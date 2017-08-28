package com.qi.sso.website.rpc.consumer;

import com.qi.sso.common.token.UserToken;
import com.sfsctech.rpc.result.ActionResult;
import com.sfsctech.security.jwt.JwtToken;
import com.sfsctech.security.session.UserAuthData;
import org.springframework.stereotype.Service;

/**
 * Class LoginService
 *
 * @author 张麒 2017/8/22.
 * @version Description:
 */
@Service
public class LoginService {

    public ActionResult<JwtToken> ssoLogin(final UserAuthData userAuthData) {
        return null;
    }

    public UserToken ssoCheck(final UserToken userToken) {
        return null;
    }

    public UserToken ssoLogout(final UserToken userToken) {
        return null;
    }

}
