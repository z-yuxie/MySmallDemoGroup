package com.yuxie.demo;

import org.junit.Test;

import java.util.Random;

/**
 * @author by 心涯
 * @package com.yuxie.demo
 * @classname RequestLimiterTest
 * @description 请求限流器测试
 * @date 2019/12/3 11:15
 */
public class RequestLimiterTest {

    RequestLimiter requestLimiter = new RequestLimiter();

    @Test
    public void limitTest() throws InterruptedException {
        Random random = new Random(1000);
        long timestamp = System.currentTimeMillis();
        for (int i = 0; i < 15; i++) {
            int sleepTime = random.nextInt(10) + 75;
            Thread.sleep(sleepTime);
            timestamp += sleepTime;
            System.out.println("+++++++++++++++++++第" + i + "次开始++++++++++++++++++++++");
            System.out.println("线程睡眠：" + sleepTime + "毫秒后苏醒，当前时间：" + timestamp);
            System.out.println("当前链表长度：" + requestLimiter.getLength());
            System.out.println("当前最老节点时间戳：" +
                    (requestLimiter.getHead() != null ? requestLimiter.getHead().getTimestamp() : null));
            boolean canBeAccepted = requestLimiter.canBeAccepted(timestamp);
            System.out.println("是否允许改请求向下传递：" + canBeAccepted);
            System.out.println("调用后链表长度：" + requestLimiter.getLength());
            System.out.println("-------------------第" + i + "次结束----------------------");
        }
    }
}
