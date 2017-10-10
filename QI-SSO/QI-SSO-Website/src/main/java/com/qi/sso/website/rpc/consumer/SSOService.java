package com.qi.sso.website.rpc.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qi.sso.inf.LoginService;
import com.sfsctech.base.jwt.JwtToken;
import com.sfsctech.base.session.UserAuthData;
import com.sfsctech.rpc.result.ActionResult;
import org.springframework.stereotype.Service;

/**
 * Class LoginService
 *
 * @author 张麒 2017/8/22.
 * @version Description:
 */
@Service
public class SSOService {

    @Reference
    private LoginService loginService;


    public ActionResult<JwtToken> login(final UserAuthData authData) {
        return loginService.login(authData);
    }

    public ActionResult<JwtToken> check(final JwtToken jt) {
        return null;
    }

    public ActionResult<JwtToken> logout(final JwtToken jt) {
        return null;
    }

}
