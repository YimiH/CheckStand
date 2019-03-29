package com.Yimm;

/**
 * Author: sy
 * Create:2019-03-24
 * 17:24
 */
public interface ProductCenter {

    //添加商品
    void addProduct(Product product);

    //修改商品
    void removeProduct(String productId);

    //更新商品
    void updateProduct(Product product);

    //判断商品是否存在
    boolean isProductExist(String productId);


    //获取商品
    Product getInstance(String productId);


    //列举所有商品
    String listProduct();


    //存储商品信息
    void store();


    //加载商品信息
    void load();
}
