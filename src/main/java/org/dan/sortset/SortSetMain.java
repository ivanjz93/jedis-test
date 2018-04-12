package org.dan.sortset;

import redis.clients.jedis.Jedis;

import java.util.Set;

public class SortSetMain {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("nn1", 6379);

        jedis.zadd("contest-score", 10, "乔峰");
        jedis.zadd("contest-score", 5, "王重阳");
        jedis.zadd("contest-score", 7, "虚竹");
        jedis.zadd("contest-score", 2, "王语嫣");
        jedis.zadd("contest-score", 5, "段誉");
        jedis.zadd("contest-score", 4, "峨眉师太");
        jedis.zadd("contest-score", 20, "张三丰");

        //获取sortSet中的所有元素
        Set<String> names = jedis.zrange("contest-score", 0, -1);
        for(String name : names) {
            System.out.println(name
                    + "\t排名" + jedis.zrank("contest-score", name)
                    + "\t获胜场次" + jedis.zscore("contest-score", name));
        }
        System.out.println("========================================");

        names = jedis.zrevrange("contest-score", 0, -1);
        for(String name : names) {
            System.out.println(name
                    + "\t排名" + jedis.zrevrank("contest-score", name)
                    + "\t获胜场次" + jedis.zscore("contest-score", name));
        }
        System.out.println("========================================");

        //修改分数
        jedis.zincrby("contest-score", 100,"王语嫣");
        names = jedis.zrevrange("contest-score", 0, -1);
        for(String name : names) {
            System.out.println(name
                    + "\t排名" + jedis.zrevrank("contest-score", name)
                    + "\t获胜场次" + jedis.zscore("contest-score", name));
        }
        System.out.println("========================================");

    }
}
