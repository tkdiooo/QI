package com.qi.sso.inf;

import com.sfsctech.base.jwt.JwtToken;
import com.sfsctech.rpc.result.ActionResult;

/**
 * Class HystrixTestService
 *
 * @author 张麒 2018-3-2.
 * @version Description:
 */
public interface HystrixTestService {

    ActionResult<JwtToken> test(Integer i);

    ActionResult<JwtToken> test1(Integer i);
}
