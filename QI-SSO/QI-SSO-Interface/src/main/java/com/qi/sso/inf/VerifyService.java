package com.qi.sso.inf;

import com.sfsctech.base.jwt.JwtToken;
import com.sfsctech.base.session.SessionInfo;
import com.sfsctech.rpc.result.ActionResult;

/**
 * Class VerifyService
 *
 * @author 张麒 2017/10/13.
 * @version Description:
 */
public interface VerifyService {

    /**
     * 简单Session校验：只校验token是否存在
     *
     * @param jt JwtToken
     * @return ActionResult<JwtToken>
     */
    ActionResult<JwtToken> simpleVerify(JwtToken jt);

    /**
     * 复杂Session检验：校验token包含的数据，以及更新数据版本
     *
     * @param jt JwtToken
     * @return ActionResult<JwtToken>
     */
    ActionResult<JwtToken> complexVerify(JwtToken jt);

}
