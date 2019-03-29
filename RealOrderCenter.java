package com.Yimm;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: sy
 * Create:2019-03-24
 * 17:35
 */
public class RealOrderCenter implements OrderCenter {

    private Map<String,Order> orderMap=new HashMap<>();
    private ProductCenter productCenter;

    private String OrderFilePath=System.getProperty("user.dir")+ File.separator+"order.txt";

    public RealOrderCenter(ProductCenter productCenter){
        this.productCenter=productCenter;
    }

    public void addOrder(Order order) {
        this.orderMap.put(order.getOrderId(),order);

    }

    public void removeOrder(Order order) {
        this.orderMap.remove(order.getOrderId(),order);

    }

    public String orderTable() {
        //只放订单编号和总价，此时需要取得相应的商品计算总价
        StringBuilder sb=new StringBuilder();
        double totalPrice=0.0;
        sb.append("================================");
        sb.append("\n");
        sb.append("订单编号     总价");
        sb.append("\n");
        for(Order order:this.orderMap.values()) {
            //订单中心依赖商品中心存在
            Map<String,Integer> productMap=order.getProductInfo();
            for (Map.Entry<String, Integer> entry : productMap.entrySet()) {
                String productId=entry.getKey();
                Integer productNum=entry.getValue();
                Product product=this.productCenter.getInstance(productId);
                totalPrice+=product.getPrice()*productNum;
            }
            sb.append(String.format("%2s\t\t%.2f",order.getOrderId(),totalPrice));
            sb.append("\n");
        }
        sb.append("================================");
        sb.append("\n");
        return sb.toString();
    }

    public String orderTable(String orderId) {
        StringBuilder sb=new StringBuilder();
        Order order=this.orderMap.get(orderId);
        double total=0.0D;
        sb.append("================================");
        sb.append("\n");
        sb.append("编号："+order.getOrderId());
        sb.append("\n");
        sb.append("打印时间："+ LocalDate.now().toString());
        sb.append("\n");
        sb.append("================================");
        sb.append("\n");
        sb.append("编号\t\t名称\t\t数量\t\t单价\n");
        for(Map.Entry<String,Integer> entry:order.getProductInfo().entrySet()){
            Product product =this.productCenter.getInstance(entry.getKey());
            sb.append(String.format("%2s\t\t%s\t\t%d\t\t%.2f",
                    entry.getKey(),
                    product.getName(),
                    entry.getValue(),
                    product.getPrice()));
                    total+=product.getPrice()*entry.getValue();
                sb.append("\n");
        }
        sb.append("================================");
        sb.append("\n");
        sb.append(String.format("消费总额：%.2f",total));
        sb.append("\n");
        sb.append("================================");
        sb.append("\n");
        return sb.toString();
    }

    public void storeOrders() {
        File file=new File(OrderFilePath);
        //采用此种类型不用关闭流
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(file))){
            for(Map.Entry<String,Order> entry:this.orderMap.entrySet()){
                Order order=entry.getValue();
                writer.write(String.format("%2s\t\t%.2f\n",
                        order.getOrderId(),
                        order.getProductInfo().size()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadOrders() {
        File file=new File(OrderFilePath);
        if(file.exists()&&file.isFile()){
            try(BufferedReader reader=new BufferedReader(new FileReader(file))) {
                String line;
                while((line=reader.readLine())!=null){
                    String[] values=line.split(":");
                    if(values.length==2){
                        //产生订单
                        Order order=new Order(values[0]);
                        this.addOrder(order);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
