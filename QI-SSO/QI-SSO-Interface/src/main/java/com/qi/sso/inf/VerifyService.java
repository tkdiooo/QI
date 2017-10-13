package com.qi.sso.inf;

import com.sfsctech.base.jwt.JwtToken;
import com.sfsctech.rpc.result.ActionResult;

/**
 * Class VerifyService
 *
 * @author 张麒 2017/10/13.
 * @version Description:
 */
public interface VerifyService {

    ActionResult<JwtToken> check(JwtToken jt);
}
