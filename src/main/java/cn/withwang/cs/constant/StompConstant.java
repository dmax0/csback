package cn.withwang.cs.constant;

public interface StompConstant {
    /**
     * STOMP的节点
     */
    String STOMP_ENDPOINT = "/dmchat";
    /**
     * 广播式
     */
    String STOMP_TOPIC = "/topic";
    /**
     * 一对一式
     */
    String STOMP_USER = "/user";
    /**
     * 单用户消息订阅地址
     */
    String SUB_USER = "/chat";
    /**
     * 单用户消息发布地址
     */
    String PUB_USER = "/chat";

}