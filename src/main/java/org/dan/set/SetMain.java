package org.dan.set;

import redis.clients.jedis.Jedis;

import java.util.Set;

public class SetMain {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("nn1", 6379);

        String[] swordsmen = {
                "郭靖",  "黄蓉", "令狐冲", "杨过", "林冲", "鲁智深", "小龙女", "虚竹", "独孤求败",
                "张三丰", "王重阳", "张无忌", "王重阳", "东方不败", "逍遥子", "乔峰", "虚竹", "段誉",
                "韦小宝", "王语嫣", "周芷若", "峨眉师太", "慕容复", "乔峰", "郭靖", "王重阳"
        };

        jedis.sadd("contest:registry", swordsmen);

        //获取set中的所有值，set特点无序、无重复元素。
        Set<String> swordsmenSet = jedis.smembers("contest:registry");
        for(String name : swordsmenSet) {
            System.out.print(name + " ");
        }
        System.out.println();

        //判断成员是否数据set
        boolean isComing = jedis.sismember("contest:registry", "井中月");
        if(!isComing) {
            System.out.println("大侠井中月尚未登记。");
        }

        //计算一个set中有多少元素
        long totalNum = jedis.scard("contest:registry");
        System.out.println("有" + totalNum + "位大戏已经登记了。");
        System.out.println();

        String[] otherSwordsmen = {
                "王语嫣", "周芷若", "峨眉师太", "慕容复", "乔峰", "郭靖", "井中月"
        };
        jedis.sadd("inter-contest:registry", otherSwordsmen);
        Set<String> interSwordsmenSet = jedis.smembers("inter-contest:registry");
        for (String name : interSwordsmenSet) {
            System.out.print(name + " ");
        }
        System.out.println();

        //计算两个集合交集
        Set<String> ands = jedis.sinter("contest:registry", "inter-contest:registry");
        for(String name : ands) {
            System.out.print(name + " ");
        }
        System.out.println();

        //计算两个集合并集
        Set<String> ors = jedis.sunion("contest:registry", "inter-contest:registry");
        for(String name : ors) {
            System.out.print(name + " ");
        }
        System.out.println();

        //计算两个集合差集
        Set<String> diffs = jedis.sdiff("contest:registry", "inter-contest:registry");
        for(String name: diffs) {
            System.out.print(name + " ");
        }
        System.out.println();

        //将两个集合的差保存起来
        jedis.sdiffstore("vip-swordsmen", "contest:registry", "inter-contest:registry");
        for (String name : jedis.smembers("vip-swordsmen")) {
            System.out.print(name + " ");
        }
        System.out.println();
    }
}
