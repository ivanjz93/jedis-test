package org.dan.string;

import com.google.gson.Gson;
import redis.clients.jedis.Jedis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.Executors;

/**
 * redis保存对象
 */
public class PersonService {
    public static void main(String[] args) throws Exception {
        Person person = new Person("刘德华", 17);
        Jedis jedis = new Jedis("nn1", 6379);

        //直接保存对象的toString，这种方法无法反序列化，不推荐。
        jedis.set("user:andy:str", person.toString());
        System.out.println(jedis.get("user:andy:str"));

        //保存序列化之后的对象
        jedis.set("user:andy:obj".getBytes(), getBytesByPerson(person));
        byte[] personBytes = jedis.get("user:andy:obj".getBytes());
        Person bPerson = getPersonByBytes(personBytes);
        System.out.println(bPerson);

        //保存对象的json格式
        jedis.set("user:andy:json", new Gson().toJson(person));
        String personJson = jedis.get("user:andy:json");
        System.out.println(personJson);
        Person jPerson = new Gson().fromJson(personJson, Person.class);
        System.out.println(jPerson);
    }

    private static Person getPersonByBytes(byte[] personBytes) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(personBytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        return (Person)ois.readObject();
    }

    private static byte[] getBytesByPerson(Person person) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(person);
        oos.flush();
        return baos.toByteArray();
    }
}
