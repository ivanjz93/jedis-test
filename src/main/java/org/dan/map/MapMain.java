package org.dan.map;

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class MapMain {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("nn1", 6379);
        jedis.del("swordsman:andy");

        jedis.hset("swordsman:andy", "name", "unknown");
        jedis.hset("swordsman:andy", "age", "18");
        jedis.hset("swordsman:andy", "skill", "kill");

        //获取所有信息
        Map<String, String> andy = jedis.hgetAll("swordsman:andy");
        System.out.println("The info of swordsman: ");
        for(Map.Entry<String, String> entry : andy.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        System.out.println();

        //获取所有字段信息
        Set<String> fields = jedis.hkeys("swordsman:andy");
        System.out.println("hkeys:");
        for(String field : fields) {
            System.out.print(field + " ");
        }
        System.out.println();

        //获取所有值的信息
        List<String> values = jedis.hvals("swordsman:andy");
        System.out.println("hvals:");
        for(String value : values) {
            System.out.print(value + " ");
        }
        System.out.println();

        //字段的值自增
        String age = jedis.hget("swordsman:andy", "age");
        System.out.println("The wrong age of andy is " + age);
        jedis.hincrBy("swordsman:andy", "age", 10);
        System.out.println("The correct age of andy is " + age);
        System.out.println();

        //删除字段
        jedis.hdel("swordsman:andy", "name");
        for (Map.Entry entry : jedis.hgetAll("swordsman:andy").entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

    }
}
