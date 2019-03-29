package com.Yimm;

/**
 * Author: sy
 * Create:2019-03-24
 * 17:30
 */
public interface OrderCenter {

    //添加订单
    void addOrder(Order order);


    //移除订单
    void removeOrder(Order order);


    //所有订单信息
    String orderTable();


    //指定订单信息
    String orderTable(String orderId);


    //存储订单
    void storeOrders();


    //加载订单
    void loadOrders();

}
