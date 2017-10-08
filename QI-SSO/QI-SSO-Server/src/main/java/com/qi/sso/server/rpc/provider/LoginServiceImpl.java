package com.qi.sso.server.rpc.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.qi.sso.inf.LoginService;
import com.sfsctech.auth.jwt.JwtToken;
import com.sfsctech.base.session.UserAuthData;
import com.sfsctech.rpc.result.ActionResult;

/**
 * Class LoginServiceImpl
 *
 * @author 张麒 2017/10/8.
 * @version Description:
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Override
    public ActionResult<JwtToken> login(UserAuthData authData) {
        System.out.println(authData.getAccount());
        System.out.println(authData.getPassword());
        return new ActionResult<>();
    }

}
