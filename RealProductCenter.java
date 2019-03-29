package com.Yimm;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: sy
 * Create:2019-03-24
 * 17:35
 */
public class RealProductCenter implements ProductCenter {
    private final Map<String,Product> productMap=new HashMap<String,Product>();
    //当前存储在文件中,写在当前工作目录
    private String productFilePath=System.getProperty("user.dir")+ File.separator+"product.txt";

    public void addProduct(Product product) {
        this.productMap.put(product.getId(),product);
    }

    public void removeProduct(String productId) {
        this.productMap.remove(productId);
    }

    public void updateProduct(Product product) {
        if(productMap.containsKey(product.getId())){
            this.productMap.put(product.getId(),product);
        }

    }

    public boolean isProductExist(String productId) {
        return this.productMap.containsKey(productId);
    }

    public Product getInstance(String productId) {
        return this.productMap.get(productId);
    }

    //打印信息,列举商品
    public String listProduct() {
        StringBuilder sb=new StringBuilder();
        sb.append("*****************   商品信息  ***************\n");
        sb.append("\t\t编号\t商品名称\t单价\n");
        for(Map.Entry<String,Product> entry:this.productMap.entrySet()){
            Product product=entry.getValue();
            sb.append(String.format("\t\t%s\t%s\t%.2f\n",
                    product.getId(),
                    product.getName(),
                    product.getPrice()));
        }
        sb.append("*******************************************\n");
        return sb.toString();
    }

    //用到文件I/O 格式：   编号： 名称： 单价
    public void store() {
        //自处自动关闭
        File file=new File(productFilePath);
        try (BufferedWriter writer=new BufferedWriter(new FileWriter(file))){
            for(Map.Entry<String,Product> entry:this.productMap.entrySet()){
                Product product=entry.getValue();
                writer.write(String.format("%s:%s:%.2f\n",
                        product.getId(),
                        product.getName(),
                        product.getPrice()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void load() {
        File file=new File(productFilePath);
        if(file.exists()&&file.isFile()){
            try(BufferedReader reader=new BufferedReader(new FileReader(file))) {
                    String line;
                    while((line=reader.readLine())!=null){
                        String[] values=line.split(":");
                        if(values.length==3){
                            //产生商品
                            Product product=new Product(
                                    values[0],
                                    values[1],
                                    Double.parseDouble(values[2])
                            );
                            this.addProduct(product);
                        }
                    }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }



    public static void main(String[] args) {
        ProductCenter pc=new RealProductCenter();
        /*Product product=new Product("1","华为手机",1999D);
        pc.addProduct(product);
        System.out.println(pc.listProduct());
        pc.store();*/
        pc.load();;
        System.out.println(pc.listProduct());


    }
}
