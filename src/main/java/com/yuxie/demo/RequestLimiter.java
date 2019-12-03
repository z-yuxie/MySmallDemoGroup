package com.yuxie.demo;

import lombok.Data;

/**
 * @author by 心涯
 * @package com.yuxie.demo
 * @classname RequestLimiter
 * @description 请求限流器，限制某个时间区间内允许的最大请求数
 * @date 2019/12/3 10:33
 */
@Data
public class RequestLimiter {

    /**
     * 节点 用于维护链表
     */
    @Data
    static class Node {
        /**
         * 时间戳
         */
        private long timestamp;
        /**
         * 下一个节点
         */
        private Node next;

        public Node(long timestamp, Node next) {
            this.timestamp = timestamp;
            this.next = next;
        }
    }

    /**
     * 限流器限流阈值
     * 假定时间区间内最多允许10个请求
     */
    public static final int N = 10;
    /**
     * 时间区间长度
     * 单位：毫秒
     */
    public static final long MS = 1000;
    /**
     * 头节点
     * 存储最老的节点
     */
    private Node head;
    /**
     * 尾节点
     * 存储最新的节点
     */
    private Node last;
    /**
     * 链表长度 初始为0
     */
    private volatile int length = 0;

    /**
     * 新传入的请求是否允许向下传递
     * @param timestamp 最新一次请求的的时间戳（这个请求参数是否是必须要调用方传入的？）
     * @return 该请求是否允许向下传递
     */
    public synchronized boolean canBeAccepted (long timestamp) {
        // 第一次调用时，链表为空
        if (this.length == 0) {
            Node newNode = new Node(timestamp , null);
            this.head = newNode;
            this.last = newNode;
            this.length++;
            return true;
        }
        // 链表已满且最老的元素与最新的请求之间的时间还未超出时间区间限制，不接受请求
        // 除链表未满时的几个请求外，其余请求均必定会判断最老节点是否过期
        // 故优先判断是否过期能更为有效的利用熔断机制减少总体所需进行的判断数量
        if ((timestamp - this.head.timestamp) < MS && this.length == N) {
            return false;
        }
        // 1、先添加新节点
        Node newNode = new Node(timestamp , null);
        this.last.next = newNode;
        this.last = newNode;
        // 2、然后再清除过期元素，避免链表长度为1时而刚好唯一的旧节点过期了的情况
        if ((timestamp - this.head.timestamp) >= MS) {
            this.head = this.head.next;
        } else {
            this.length++;
        }
        return true;
    }
}
