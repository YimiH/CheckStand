package com.Yimm;

import java.util.Scanner;

/**
 * Author: sy
 * Create:2019-03-24
 * 17:06
 */
public class CheckStand{
    private static Scanner scanner=new Scanner(System.in);

    //商品中心管理
    private static ProductCenter productCenter=new RealProductCenter();

    //订单中心管理
    private static OrderCenter orderCenter=new RealOrderCenter(productCenter);

    private static Integer orderId=0;

    //帮助信息
    static void helpInfo(){
        System.out.println("**************欢迎使用简易收银台 **************");
        System.out.println("        U 使用    S 设置    P 保存            ");
        System.out.println("             A 关于     Q 退出               ");
        System.out.println("        请输入对应的符号进行您的操作：");
        System.out.println("*******************************************");


    }


    //退出逻辑
    static void quit(){
        System.out.println("*******************************************");
        System.out.println("            欢迎使用    下次再见");
        System.out.println("*******************************************");
        System.exit(0);
    }


    //关于信息
    static void about(){
        System.out.println("*******************************************");
        System.out.println("        产品名称：基于字符界面的简易收银态");
        System.out.println("        功能   ：提供字符界面的简单收银操作");
        System.out.println("        作者   ：Yimm");
        System.out.println("        版本   ：v1.0.0");
        System.out.println("*******************************************");


    }


    //使用信息
    static void useMessage(){
        System.out.println("***************  用户买单  ******************");
        System.out.println("    A 添加商品      D 取消商品    L 浏览商品");
        System.out.println("           S 查看订单      R 返回上级");
        System.out.println("*******************************************");
    }


    //设置信息
    static void settingInfo(){
        System.out.println("***************  商品管理  ******************");
        System.out.println("    A 商品上架      D 商品下架    U 商品修改");
        System.out.println("           S 查看商品      R 返回上级");
        System.out.println("*******************************************");
    }


    //使用逻辑
    static void usage(){
        //创建订单同时添加到订单中心
        Order order=new Order(String.valueOf(++orderId));
        orderCenter.addOrder(order);
        useMessage();
        System.out.println(orderCenter.orderTable(order.getOrderId()));
        while(true){
            String line=scanner.nextLine();
            switch (line.trim().toUpperCase()){
                case "S":{
                    System.out.println(productCenter.listProduct());
                    break;
                }
                case "A":{
                    System.out.println("请输入下单信息，格式如：商品编号 所需数量");
                    String value=scanner.nextLine();
                    String[] messArray=value.split(" ");
                    if(messArray.length==2){
                        Product product=productCenter.getInstance(messArray[0]);
                        if(product!=null){
                            order.add(messArray[0],Integer.parseInt(messArray[0]));
                            System.out.println(orderCenter.orderTable(order.getOrderId()));
                            break;
                        }
                    }
                    System.out.println("请按照格式输入订单信息");
                }
                case "D":{
                    System.out.println("请输入取消信息，格式如：商品编号  操作数量");
                    String value=scanner.nextLine();
                    String[] messArray=value.split(" ");
                    if(messArray.length==2){
                        Product product=productCenter.getInstance(messArray[0]);
                        if(product!=null){
                            order.cancel(messArray[0],Integer.parseInt(messArray[0]));
                            System.out.println(orderCenter.orderTable(order.getOrderId()));
                            break;
                        }
                    }
                    System.out.println("请按照格式输入订单信息");
                    break;
                }
                case "L":{
                    System.out.println(productCenter.listProduct());
                    break;
                }
                case "R":{
                    return;
                }
                default:{
                    useMessage();
                }
            }
        }

    }


    //恢复逻辑
    static void setting(){
        settingInfo();
        while(true){
            String line=scanner.nextLine();
            switch (line.toUpperCase()){
                case "A":{
                    System.out.println("请录入上架的商品信息，格式如：1 华为 2999");
                    Product product=readProduct();
                    if(product==null){
                        System.out.println("请按照规定输入操作");
                        break;
                    }
                    if(productCenter.isProductExist(product.getId())){
                        System.out.println("您输入的商品已经上架，不可重复操作");
                    }else{
                        productCenter.addProduct(product);
                    }
                    System.out.println(productCenter.listProduct());
                    break;
                }
                case "D":{
                    System.out.println("请录入下架的商品信息，格式如：商品编号");
                    Product product=readProduct();
                    if(product==null){
                        System.out.println("请按照规定输入操作");
                        break;
                    }
                    if(productCenter.isProductExist(product.getId())){
                       productCenter.removeProduct(product.getId());
                    }else{
                        System.out.println("输入错误，请输入当前存在的商品");
                    }
                    System.out.println(productCenter.listProduct());
                    break;
                }
                case "U":{
                    System.out.println("请录入更新的商品信息，格式如：1 华为 2699");
                    Product product=readProduct();
                    if(product==null){
                        System.out.println("请按照规定输入操作");
                        break;
                    }
                    if(productCenter.isProductExist(product.getId())){
                        productCenter.updateProduct(product);
                    }else{
                        System.out.println("输入错误，请输入当前存在的商品");
                    }
                    System.out.println(productCenter.listProduct());
                    break;
                }
                case "S":{
                    System.out.println(productCenter.listProduct());
                    break;
                }
                case "R":{
                    return;
                }
                default:{
                    settingInfo();
                }
            }
        }

    }


    //读取商品
    static Product readProduct(){
        String value=scanner.nextLine();
        String[] messInfo=value.split(" ");
        if(messInfo.length==3 || messInfo.length==1){
            if(messInfo.length==3){
                Product product=new Product(messInfo[0],messInfo[1],Double.parseDouble(messInfo[2]));
                return product;
            }
            Product product=new Product(messInfo[0],"",0.0D);
            return product;
        }
        return null;
    }


    //主方法

    public static void main(String[] args){
        helpInfo();
        //初始化加载文件信息
        productCenter.load();
        orderCenter.loadOrders();
        while(true){
            String line=scanner.nextLine();
            switch (line.trim().toUpperCase()){
                case "U":
                    usage();
                    helpInfo();
                    break;
                case "S":
                    setting();
                    helpInfo();
                    break;
                case "A":
                    about();
                    break;
                case "P":
                    //保存商品信息和订单信息
                    productCenter.store();
                    orderCenter.loadOrders();
                    break;
                case "Q": {
                    quit();
                    break;
                }
                default: {
                    helpInfo();
                    break;
                }
            }
        }


    }
}
