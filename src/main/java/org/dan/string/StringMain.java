package org.dan.string;

import redis.clients.jedis.Jedis;

import java.util.List;

public class StringMain {
    public static void main(String[] args) throws Exception {
        Jedis jedis = new Jedis("nn1", 6379);

        jedis.set("name", "andy liu");
        System.out.println(jedis.get("name"));

        //对字符串进行增减，前提是其可转换为数字
        jedis.set("age", "18");
        jedis.incr("age");
        System.out.println(jedis.get("age"));

        jedis.decr("age");
        System.out.println(jedis.get("age"));

        //多条插入和查询
        jedis.mset("AAA", "Mysql operate",
                    "BBB", "Linux operate",
                    "CCC", "SSH operate",
                    "DDD", "SSM operate");
        List<String> results = jedis.mget("AAA", "BBB", "CCC", "DDD");
        for (String result: results) {
            System.out.println(result);
        }

        //设置字段自动过期
        jedis.setex("frog", 10, "fairyland");
        while(jedis.exists("frog")){
            System.out.println("living in fairyland");
            Thread.sleep(1000);
        }

        System.out.println();

        //对已存在的字段设置过期时间
        jedis.set("frog", "fairyland");
        jedis.expire("frog", 10);
        while(jedis.exists("frog")) {
            System.out.println("living in fairyland");
            Thread.sleep(1000);
        }

    }
}
