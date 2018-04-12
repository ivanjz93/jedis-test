package org.dan;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;

public class PingPong {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("nn1", 6379);
        String response = jedis.ping();
        System.out.println(response);
    }
}
