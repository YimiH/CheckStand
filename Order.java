package com.Yimm;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: sy
 * Create:2019-03-24
 * 17:17
 */
public class Order {

    //表示订单对象创建完成后，订单编号不可再修改
    private final String orderId;


    //表示订单对象创建完成后，ProductInfo属性实例化HashMap
    private final Map<String,Integer> productInfo=new HashMap<String,Integer>();

    public Order(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public Map<String,Integer> getProductInfo() {
        return productInfo;
    }

    //添加订单
    public void add(String productId,Integer num){
        int sum=0;
        if(productInfo.containsKey(productId)){
            sum=productInfo.get(productId)+num;
        }else{
            sum=num;
        }
        this.productInfo.put(productId,sum);
    }

    //取消订单
    public void cancel(String productId,Integer num){
        if(productInfo.containsKey(productId)){
            int sum=this.productInfo.get(productId);
            sum-=num;
            sum=sum<0?0:sum;
            if(sum==0){
                this.productInfo.remove(productId);
            }else{
                //更新商品信息
                this.productInfo.put(productId,sum);
            }
        }

    }

    //清空订单
    public void clear(){
        this.productInfo.clear();
    }

}
