package com.qi.backstage.management.common.verify.util;

import com.qi.backstage.management.common.verify.model.VerifyModel;
import com.sfsctech.common.util.DateUtil;
import com.sfsctech.common.util.FileUtil;
import com.sfsctech.common.util.SpringContextUtil;
import com.sfsctech.constants.LabelConstants;
import com.sfsctech.rpc.result.ActionResult;
import com.sfsctech.spring.properties.WebsiteProperties;
import org.springframework.boot.autoconfigure.web.WebMvcProperties;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Class VerifyUtil
 *
 * @author 张麒 2018-3-26.
 * @version Description:
 */
public class VerifyUtil {

    private static WebsiteProperties properties = SpringContextUtil.getBean(WebsiteProperties.class);

    public static ActionResult<String> BackendVerify(MultipartFile mf, VerifyModel vm) throws IOException {
        ActionResult<String> result = new ActionResult<>();
        String fileName = mf.getOriginalFilename().toLowerCase();
        String suffix = FileUtil.getFileSuffixName(fileName);
        if (!".java".contains(suffix.toLowerCase())) {
            result.setSuccess(false);
            result.setMessage("上传文件格式不正确");
        } else {
            fileName = FileUtil.getFileBaseName(fileName) + LabelConstants.UNDERLINE + DateUtil.toDateTime(DateUtil.getCurrentDate()) + suffix;
            File targetFile = new File(properties.getSupport().getUploadPath().get("classFilePath"), fileName);
            if (targetFile.exists() || (!targetFile.exists() && targetFile.mkdirs())) {
                mf.transferTo(targetFile);
            }
            System.out.println(FileUtil.readFileToString(targetFile));
            FileUtil.readLines(targetFile);

        }
        return result;
    }

}
