package com.qi.sso.inf;

import com.sfsctech.base.jwt.JwtToken;
import com.sfsctech.base.session.UserAuthData;
import com.sfsctech.rpc.result.ActionResult;

/**
 * Class LoginService
 *
 * @author 张麒 2017/10/8.
 * @version Description:
 */
public interface LoginService {

    ActionResult<JwtToken> login(UserAuthData authData);
}
