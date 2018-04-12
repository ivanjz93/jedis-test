package org.dan.string;

import redis.clients.jedis.Jedis;

public class Announcer implements Runnable {

    private String redisKey;
    private Jedis jedis;

    public Announcer(String redisKey) {
        this.redisKey = redisKey;
    }

    @Override
    public void run() {
        jedis = new Jedis("nn1", 6379);
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("===============当前总共比武次数为：" + jedis.get(redisKey));
        }
    }
}
