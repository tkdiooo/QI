package com.qi.sso.website.rpc.consumer;

import com.sfsctech.cloud.base.inf.LoginService;
import com.sfsctech.cloud.base.inf.VerifyService;
import com.sfsctech.core.base.domain.result.RpcResult;
import com.sfsctech.core.base.jwt.JwtToken;
import com.sfsctech.core.base.session.UserAuthData;
import com.sfsctech.core.web.domain.result.ActionResult;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private LoginService loginService;

    @Autowired
    private VerifyService verifyService;

    public ActionResult<JwtToken> login(final UserAuthData authData) {
        RpcResult<JwtToken> result = loginService.login(authData);
        return ActionResult.forSuccess(result.getResult());
    }

    public ActionResult<JwtToken> check(final JwtToken jt) {
        RpcResult<JwtToken> result = verifyService.complexVerify(jt);
        return ActionResult.forSuccess(result.getResult());
    }

    @Async
    public void logout(final JwtToken jt) {
        loginService.logout(jt);
    }

}
