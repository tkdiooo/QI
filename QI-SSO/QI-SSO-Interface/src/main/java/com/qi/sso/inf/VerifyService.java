package com.qi.sso.inf;

import com.sfsctech.auth.jwt.JwtToken;
import com.sfsctech.rpc.result.ActionResult;

/**
 * Class VerifyService
 *
 * @author 张麒 2017/10/8.
 * @version Description:
 */
public interface VerifyService {

    ActionResult<JwtToken> check(JwtToken jt);
}
