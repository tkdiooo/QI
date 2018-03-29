package com.qi.backstage.management.common.verify.util;

import com.qi.backstage.management.common.verify.model.ConditionModel;
import com.sfsctech.common.util.*;
import com.sfsctech.constants.LabelConstants;
import com.sfsctech.constants.PatternConstants;
import com.sfsctech.constants.RpcConstants;
import com.sfsctech.database.model.TableModel;
import com.sfsctech.rpc.result.ActionResult;
import com.sfsctech.spring.properties.WebsiteProperties;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class VerifyUtil
 *
 * @author 张麒 2018-3-26.
 * @version Description:
 */
public class VerifyUtil {

    private static WebsiteProperties properties = SpringContextUtil.getBean(WebsiteProperties.class);

    public static ActionResult<String> BackendVerify(MultipartFile mf, List<ConditionModel> condition, Map<String, TableModel> tableModels) {
        ActionResult<String> result = new ActionResult<>();
        String fileName = mf.getOriginalFilename().toLowerCase();
        String suffix = FileUtil.getFileSuffixName(fileName);
        if (!".java".contains(suffix.toLowerCase())) {
            result.setSuccess(false);
            result.setStatus(RpcConstants.Status.Failure);
            result.setMessage("上传文件格式不正确");
            return result;
        }
        try {
            File targetFile = new File(properties.getSupport().getUploadPath().get("VerifyFilePath"));
            if (targetFile.exists() || (!targetFile.exists() && targetFile.mkdirs())) {
                targetFile = new File(targetFile.getPath() + LabelConstants.SLASH + fileName);
                mf.transferTo(targetFile);
            }
            String classContent = FileUtil.readFileToString(targetFile);
            Set<String> im = new HashSet<>();
            for (ConditionModel c : condition) {
                TableModel tm = tableModels.get(c.getName());
                StringBuilder sb = new StringBuilder();
                // 自定义
                if ("custom".equals(c.getType())) {
                    // 非空
                    if (c.isNotNull()) {
                        if (tm.getType().contains("char")) {
                            im.add("import org.hibernate.validator.constraints.NotEmpty;");
                            sb.append(LabelConstants.FOUR_SPACES).append("@NotEmpty").append(LabelConstants.RETURN_NEW_LINE);
                        } else {
                            im.add("import javax.validation.constraints.NotNull;");
                            sb.append(LabelConstants.FOUR_SPACES).append("@NotNull").append(LabelConstants.RETURN_NEW_LINE);
                        }
                    }
                    // 数字长度
                    if (null != c.getDigits()) {
                        im.add("import javax.validation.constraints.Digits;");
                        sb.append(LabelConstants.FOUR_SPACES).append("@Digits(integer = ").append(c.getDigits().getInteger()).append(", fraction = ").append(c.getDigits().getFraction()).append(")").append(LabelConstants.RETURN_NEW_LINE);
                    }
                    // 字符串长度
                    if (null != c.getLength()) {
                        im.add("import org.hibernate.validator.constraints.Length;");
                        sb.append(LabelConstants.FOUR_SPACES).append("@Length(");
                        if (null != c.getLength() && c.getLength().getMin() > 0) {
                            sb.append("min = ").append(c.getLength().getMin()).append(", ");
                        }
                        sb.append("max = ").append(c.getLength().getMax()).append(")").append(LabelConstants.RETURN_NEW_LINE);
                    }
                    if (StringUtil.isNotBlank(c.getMax())) {
                        im.add("import javax.validation.constraints.Max;");
                        sb.append(LabelConstants.FOUR_SPACES).append("@Max(").append(c.getMax()).append(")").append(LabelConstants.RETURN_NEW_LINE);
                    }
                    if (StringUtil.isNotBlank(c.getMin())) {
                        im.add("import javax.validation.constraints.Min;");
                        sb.append(LabelConstants.FOUR_SPACES).append("@Min(").append(c.getMin()).append(")").append(LabelConstants.RETURN_NEW_LINE);
                    }
                    if (null != c.getRange()) {
                        im.add("import org.hibernate.validator.constraints.Range;");
                        sb.append(LabelConstants.FOUR_SPACES).append("@Range(min = ").append(c.getRange().getMin()).append(", max = ").append(c.getRange().getMax()).append(")").append(LabelConstants.RETURN_NEW_LINE);
                    }
                    if (StringUtil.isNotBlank(c.getPattern())) {
                        im.add("import javax.validation.constraints.Pattern;");
                        PatternConstants.Pattern pattern = PatternConstants.Pattern.getPatternByKey(c.getPattern());
                        sb.append(LabelConstants.FOUR_SPACES).append("@Pattern(regexp = \"").append(null != pattern ? pattern.getPattern() : c.getPattern()).append("\", message = \"数值必须是").append(null != pattern ? pattern.getContent() : c.getPattern()).append("\"")
                                .append(")").append(LabelConstants.RETURN_NEW_LINE);
                    }
                    if (StringUtil.isNotBlank(c.getConstraint())) {
                        im.add("import javax.validation.constraints." + c.getConstraint() + ";");
                        sb.append(LabelConstants.FOUR_SPACES).append("@").append(c.getConstraint()).append(")").append(LabelConstants.RETURN_NEW_LINE);
                    }
                }
                // 默认
                else if ("default".equals(c.getType())) {

                }
                int end = classContent.indexOf(c.getName().toLowerCase() + ";");
                int start = classContent.lastIndexOf("private", end);
                String field = LabelConstants.FOUR_SPACES + classContent.substring(start, end) + c.getName().toLowerCase() + LabelConstants.SEMICOLON;
                classContent = classContent.replace(field, sb + field);

            }
            int start = classContent.indexOf("import");
            int end = classContent.indexOf(";", start) + 1;
            String importStr = classContent.substring(start, end);
            classContent = classContent.replace(importStr, importStr + LabelConstants.RETURN_NEW_LINE + ListUtil.toString(im, LabelConstants.RETURN_NEW_LINE));
            FileUtil.writeStringToFile(targetFile, classContent);
            result.setResult(fileName);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setStatus(RpcConstants.Status.ServerError);
            result.setMessage(ThrowableUtil.getRootMessage(e));
        }
        return result;
    }

    public static ActionResult<String> FrontendVerify(List<ConditionModel> condition, Map<String, TableModel> tableModels) {
        ActionResult<String> result = new ActionResult<>();
        try {
            String fileName = "test.js";
            File targetFile = new File(properties.getSupport().getUploadPath().get("VerifyFilePath"));
            if (targetFile.exists() || (!targetFile.exists() && targetFile.mkdirs())) {
                targetFile = new File(targetFile.getPath() + LabelConstants.SLASH + fileName);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("$('.form-horizontal').bootstrapValidator({").append(LabelConstants.RETURN_NEW_LINE)
                    .append("message: 'This value is not valid',").append(LabelConstants.RETURN_NEW_LINE)
                    .append("feedbackIcons: {").append(LabelConstants.RETURN_NEW_LINE)
                    .append("valid: 'glyphicon glyphicon-ok',").append(LabelConstants.RETURN_NEW_LINE)
                    .append("invalid: 'glyphicon glyphicon-remove',").append(LabelConstants.RETURN_NEW_LINE)
                    .append("validating: 'glyphicon glyphicon-refresh'").append(LabelConstants.RETURN_NEW_LINE)
                    .append("},").append(LabelConstants.RETURN_NEW_LINE)
                    .append("fields: {").append(LabelConstants.RETURN_NEW_LINE);
            for (ConditionModel c : condition) {
//                TableModel tm = tableModels.get(c.getName());
                // 自定义
                if ("custom".equals(c.getType())) {
                    sb.append(c.getName().toLowerCase()).append(": {").append(LabelConstants.RETURN_NEW_LINE).append("validators: {").append(LabelConstants.RETURN_NEW_LINE);
                    // 非空
                    if (c.isNotNull()) {
                        sb.append("notEmpty: {").append(LabelConstants.RETURN_NEW_LINE).append("},").append(LabelConstants.RETURN_NEW_LINE);
                    }
                    // 数字长度
                    if (null != c.getDigits()) {
                        if (null != c.getDigits().getFraction() && c.getDigits().getFraction() > 0) {
                            sb.append("digits: {").append(LabelConstants.RETURN_NEW_LINE).append("},").append(LabelConstants.RETURN_NEW_LINE);
                        } else {
                            sb.append("numeric: {").append(LabelConstants.RETURN_NEW_LINE).append("},").append(LabelConstants.RETURN_NEW_LINE);
                        }
                    }
                    // 字符串长度
                    if (null != c.getLength()) {
                        sb.append("stringLength: {").append(LabelConstants.RETURN_NEW_LINE)
                                .append("min: ").append(c.getLength().getMin()).append(",").append(LabelConstants.RETURN_NEW_LINE)
                                .append("max: ").append(c.getLength().getMax()).append(LabelConstants.RETURN_NEW_LINE)
                                .append("},").append(LabelConstants.RETURN_NEW_LINE);
                    }
                    if (StringUtil.isNotBlank(c.getMax())) {
                        sb.append("lessThan: {").append(LabelConstants.RETURN_NEW_LINE)
                                .append("value: ").append(c.getMax()).append(",").append(LabelConstants.RETURN_NEW_LINE)
                                .append("},").append(LabelConstants.RETURN_NEW_LINE);
                    }
                    if (StringUtil.isNotBlank(c.getMin())) {
                        sb.append("greaterThan: {").append(LabelConstants.RETURN_NEW_LINE)
                                .append("value: ").append(c.getMax()).append(",").append(LabelConstants.RETURN_NEW_LINE)
                                .append("},").append(LabelConstants.RETURN_NEW_LINE);
                    }
                    if (null != c.getRange()) {
                        sb.append("between: {").append(LabelConstants.RETURN_NEW_LINE)
                                .append("min: ").append(c.getRange().getMin()).append(",").append(LabelConstants.RETURN_NEW_LINE)
                                .append("max: ").append(c.getRange().getMax()).append(LabelConstants.RETURN_NEW_LINE)
                                .append("},").append(LabelConstants.RETURN_NEW_LINE);
                    }
                    if (StringUtil.isNotBlank(c.getPattern())) {
                        PatternConstants.Pattern pattern = PatternConstants.Pattern.getPatternByKey(c.getPattern());
                        sb.append("regexp: {").append(LabelConstants.RETURN_NEW_LINE)
                                .append("regexp: /").append(null != pattern ? pattern.getPattern() : c.getPattern()).append("/,").append(LabelConstants.RETURN_NEW_LINE)
                                .append("message: '数值必须是").append(null != pattern ? pattern.getPattern() : c.getPattern()).append("'").append(LabelConstants.RETURN_NEW_LINE)
                                .append("},").append(LabelConstants.RETURN_NEW_LINE);
                    }
                    int index = sb.lastIndexOf(",");
                    sb.replace(index, index + 1, "").append("}").append(LabelConstants.RETURN_NEW_LINE);
                }
                // 默认
                else if ("default".equals(c.getType())) {

                }
                sb.append("}").append(",").append(LabelConstants.RETURN_NEW_LINE);
            }
            int index = sb.lastIndexOf(",");
            sb.replace(index, index + 1, "");
            sb.append("}").append(LabelConstants.RETURN_NEW_LINE).append("});");
            System.out.println(sb);
            FileUtil.writeStringToFile(targetFile, sb.toString());
            result.setResult(fileName);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setStatus(RpcConstants.Status.ServerError);
            result.setMessage(ThrowableUtil.getRootMessage(e));
        }
        return result;
    }
}
