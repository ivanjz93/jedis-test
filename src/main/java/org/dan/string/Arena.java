package org.dan.string;

import redis.clients.jedis.Jedis;

import java.util.Random;

public class Arena implements Runnable{

    private Random random = new Random();
    private String redisKey;
    private String arenaName;
    private Jedis jedis;

    public Arena(String redisKey, String arenaName) {
        this.redisKey = redisKey;
        this.arenaName = arenaName;
    }
    @Override
    public void run() {
        jedis = new Jedis("nn1", 6379);
        String[] swordsmen = {
                "郭靖",  "黄蓉", "令狐冲", "杨过", "林冲", "鲁智深", "小龙女", "虚竹", "独孤求败",
                "张三丰", "王重阳", "张无忌", "东方不败", "逍遥子", "乔峰",
                "虚竹", "段誉", "韦小宝", "王语嫣", "周芷若", "峨眉师太", "慕容复",
        };
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int p1 = random.nextInt(swordsmen.length);
            int p2 = random.nextInt(swordsmen.length);
            while (p1 == p2) {
                p2 = random.nextInt(swordsmen.length);
            }

            System.out.println("在擂台" + arenaName + "上 大侠 " + swordsmen[p1] + " VS " + swordsmen[p2]);
            jedis.incr(redisKey);
        }

    }
}
