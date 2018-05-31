package com.qi.sso.website.rpc.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sfsctech.core.auth.sso.inf.LoginService;
import com.sfsctech.core.auth.sso.inf.VerifyService;
import com.sfsctech.core.base.jwt.JwtToken;
import com.sfsctech.core.base.session.UserAuthData;
import com.sfsctech.core.rpc.result.ActionResult;
import org.springframework.scheduling.annotation.Async;
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

    @Reference
    private VerifyService verifyService;

    public ActionResult<JwtToken> login(final UserAuthData authData) {
        return loginService.login(authData);
    }

    public ActionResult<JwtToken> check(final JwtToken jt) {
        return verifyService.complexVerify(jt);
    }

    @Async
    public void logout(final JwtToken jt) {
        loginService.logout(jt);
    }

}
