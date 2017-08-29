package com.qi.sso.website.rpc.consumer;

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
public class SSOService {


    public ActionResult<JwtToken> login(final UserAuthData userAuthData) {
        return null;
    }

    public ActionResult<JwtToken> check(final JwtToken jt) {
        return null;
    }

    public ActionResult<JwtToken> logout(final JwtToken jt) {
        return null;
    }

}
