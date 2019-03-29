package com.Yimm;

/**
 * Author: sy
 * Create:2019-03-24
 * 17:05
 */
class Product{
    //商品编号
    private String id;
    //商品名称
    private String name;
    //商品价格
    private double price;

    //private int store;
    public String getId(){
        return this.id;
    }
    public void setId(String id){
        this.id=id;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name=name;
    }
    public double getPrice(){
        return this.price;
    }
    public void setPrice(double price){
        this.price=price;
    }
    /*public int getStore(){
        return this.store;
    }
    public void setStore(int store){
        this.store=store;
    }*/
    public Product(){
        super();
    }
    public Product(String id,String name,double price){
        this.id=id;
        this.name=name;
        this.price=price;
    }

    public String toString(){
        //%.2f:表示保留两位小数
        return String.format("[%2s] %s %.2f",this.getId(),this.getName(),this.getPrice());
    }
}