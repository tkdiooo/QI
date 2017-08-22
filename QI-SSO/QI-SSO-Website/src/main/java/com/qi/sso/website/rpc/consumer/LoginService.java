package com.qi.sso.website.rpc.consumer;

import com.qi.sso.common.token.UserToken;
import org.springframework.stereotype.Service;

/**
 * Class LoginService
 *
 * @author 张麒 2017/8/22.
 * @version Description:
 */
@Service
public class LoginService {

    public UserToken ssoLogin(UserToken ut) {
        return ut;
    }
}
