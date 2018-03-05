package com.qi.sso.server.rpc.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.qi.sso.inf.HystrixTestService;
import com.sfsctech.base.jwt.JwtToken;
import com.sfsctech.rpc.result.ActionResult;

/**
 * Class HystrixTestServiceImpl
 *
 * @author 张麒 2018-3-2.
 * @version Description:
 */
@Service(retries = -1)
public class HystrixTestServiceImpl implements HystrixTestService {

    private static Integer count = 0;

    @Override
    public ActionResult<JwtToken> test(Integer i) {
        System.out.println("请求索引test：" + i);
        return new ActionResult<>();
    }

    @Override
    public ActionResult<JwtToken> test1(Integer i) {
        System.out.println("请求索引test1：" + i);
//        if (count < 200) {
            try {
//                count++;
                System.out.println("count计数" + count);
                System.out.println("线程休眠100000");
                Thread.sleep(100000);
                return new ActionResult<>();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//        }
        return new ActionResult<>();
    }
}
