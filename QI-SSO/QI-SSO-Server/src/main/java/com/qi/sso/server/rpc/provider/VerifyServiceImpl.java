package com.qi.sso.server.rpc.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.sfsctech.base.jwt.JwtToken;
import com.sfsctech.dubbox.inf.VerifyService;
import com.sfsctech.rpc.result.ActionResult;

/**
 * Class VerifyServiceImpl
 *
 * @author 张麒 2017/10/12.
 * @version Description:
 */
@Service(retries = -1)
public class VerifyServiceImpl implements VerifyService {

    @Override
    public ActionResult<JwtToken> check(JwtToken jwtToken) {
        ActionResult<JwtToken> result = new ActionResult<>();
        System.out.println(jwtToken);
        return result;
    }
}
