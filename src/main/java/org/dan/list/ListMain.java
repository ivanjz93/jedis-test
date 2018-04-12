package org.dan.list;

import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;

import java.util.List;

public class ListMain {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("nn1", 6379);
        jedis.del("counter1");

        jedis.lpush("counter1", "1", "2", "3", "4", "5");
        for(String member :jedis.lrange("counter1", 0, -1)) {
            System.out.print(member + " ");
        }
        System.out.println();

        jedis.linsert("counter1", BinaryClient.LIST_POSITION.AFTER, "4", "6");
        for(String member :jedis.lrange("counter1", 0, -1)) {
            System.out.print(member + " ");
        }
        System.out.println();

        jedis.linsert("counter1", BinaryClient.LIST_POSITION.BEFORE, "4", "7");
        for(String member :jedis.lrange("counter1", 0, -1)) {
            System.out.print(member + " ");
        }
        System.out.println();

        jedis.lpop("counter1");
        for(String member :jedis.lrange("counter1", 0, -1)) {
            System.out.print(member + " ");
        }
        System.out.println();

        jedis.rpop("counter1");
        for(String member :jedis.lrange("counter1", 0, -1)) {
            System.out.print(member + " ");
        }
        System.out.println();

        jedis.rpush("counter1", "8");
        for(String member :jedis.lrange("counter1", 0, -1)) {
            System.out.print(member + " ");
        }
        System.out.println();

        jedis.ltrim("counter1", 2, 5);
        for(String member :jedis.lrange("counter1", 0, -1)) {
            System.out.print(member + " ");
        }
        System.out.println();


    }
}
