package org.dan.map;

import com.google.gson.Gson;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Cart {

    private Jedis jedis = new Jedis("nn1", 6379);

    public void updateProduct2Cart(String username, String productID, int num) {
        jedis.hincrBy("shop:cart:" + username, productID, num);
    }

    public static void main(String[] args) {
        initData();

        Cart cart = new Cart();

        String username = "andy";
        //往购物车中添加商品
        cart.updateProduct2Cart(username, "1645139266", 10);
        cart.updateProduct2Cart(username, "1788744384", 1000);
        cart.updateProduct2Cart(username, "1645080454", -1000);

        System.out.println("=================用户购物车信息如下===================");
        List<Product> products = cart.getProductByUsername(username);
        for (Product product : products) {
            System.out.println(product);
        }
    }

    private  List<Product> getProductByUsername(String username) {
        List<Product> products = new ArrayList<>();
        Map<String, String> productMap = jedis.hgetAll("shop:cart:" + username);
        if(productMap == null || productMap.size() == 0)
            return products;
        for(Map.Entry entry : productMap.entrySet()) {
            Product product = new Product();
            product.setProductID((String)entry.getKey());
            long num = Long.parseLong((String)entry.getValue());
            product.setNum(num > 0 ? num : 0);
            completeOtherField(product);
            products.add(product);
        }
        return  products;
    }

    private void completeOtherField(Product product) {
        String productId = product.getProductID();
        String productJson = jedis.get("shop:product:" + productId);
        Product jProduct = new Gson().fromJson(productJson, Product.class);
        if(jProduct != null) {
            product.setName(jProduct.getName());
            product.setPrice(jProduct.getPrice());
        }
    }

    public static void initData() {
        System.out.println("=================初始化商品信息===================");

        Jedis jedis = new Jedis("nn1", 6379);

        //准备数据
        Product product1 = new Product("1645139266", "man waistcoat", new BigDecimal("168"));
        Product product2 = new Product("1788744384", "older waistcoat", new BigDecimal("168"));
        Product product3 = new Product("1645080454", "women waistcoat", new BigDecimal("168"));

        //将商品数据写入redis
        jedis.set("shop:product:" + product1.getProductID(), new Gson().toJson(product1));
        jedis.set("shop:product:" + product2.getProductID(), new Gson().toJson(product2));
        jedis.set("shop:product:" + product3.getProductID(), new Gson().toJson(product3));

        //打印所有商品信息
        Set<String> allProductKeys = jedis.keys("shop:product:*");
        for(String productKey : allProductKeys) {
            String json = jedis.get(productKey);
            Product product = new Gson().fromJson(json, Product.class);
            System.out.println(product);
        }

    }
}
