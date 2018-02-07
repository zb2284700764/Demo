package other.test;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Test1 {


    public void test() {

        // 无符号运算符 >>> 计算正数、负数
        System.out.println("无符号运算符 >>>");
        System.out.println("17 >>> 2 = " + (17 >>> 2));
        System.out.println("-17 >>> 2 = " + (-17 >>> 2));

        // 通过循环的方式求 -17 >>> 2 的结果
        int result = 3;
        for (int i = 0; i < 27; i++) {
            int pow = (int) Math.pow(2, (i + 3));
            result += pow;
        }
        System.out.println("通过循环的方式求 -17 >>> 2 的结果：" + result);

        // 右移运算符 >> 计算正数、负数
        System.out.println("右移运算符 >>");
        System.out.println("17 >> 2 = " + (17 >> 2));
        System.out.println("-17 >> 2 = " + (-17 >> 2));

        // 左移运算符 << 计算正数、负数
        System.out.println("左移运算符 <<");
        System.out.println("17 << 2 = " + (17 << 2));
        System.out.println("-17 << 2 = " + (-17 << 2));

        // 按位 与 运算符 &  计算正数、负数
        System.out.println("按位 与 运算符 &");
        System.out.println("12 & 5 = " + (12 & 5));
        System.out.println("-12 & 5 = " + (-12 & 5));
        System.out.println("12 & -5 = " + (12 & -5));
        System.out.println("-12 & -5 = " + (-12 & -5));

        // 或 运算符 |  计算正数、负数
        System.out.println("按位 或 运算符 |");
        System.out.println("12 | 5 = " + (12 | 5));
        System.out.println("-12 | 5 = " + (-12 | 5));
        System.out.println("12 | -5 = " + (12 | -5));
        System.out.println("-12 | -5 = " + (-12 | -5));

        // 按位 异或 运算符 ^  计算正数、负数
        System.out.println("按位 异或 运算符 ^");
        System.out.println("12 ^ 5 = " + (12 ^ 5));
        System.out.println("-12 ^ 5 = " + (-12 ^ 5));
        System.out.println("12 ^ -5 = " + (12 ^ -5));
        System.out.println("-12 ^ -5 = " + (-12 ^ -5));

        // 按位 非 运算符 ~  计算正数、负数
        System.out.println("按位 非 运算符 ~");
        System.out.println("~12 = " + ~12);
        System.out.println("~-12 = " + ~-12);



    }


    public void testArrayList() {

        List<String> linkedList = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            StringBuffer sb = new StringBuffer("Hello");
            sb.append(i + 1);
            linkedList.add(sb.toString());
        }
        System.out.println(linkedList.size());

        List<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            StringBuffer sb = new StringBuffer("Hello");
            sb.append(i + 1);
            arrayList.add(sb.toString());
        }
        System.out.println(arrayList.size());


        for (int i = 0; i < 50; i++) {
            arrayList.remove(i);
        }
        System.out.println(arrayList.size());

        for (int i = 50; i < 100; i++) {
            StringBuffer sb = new StringBuffer("Hello");
            sb.append(i + 50);
            arrayList.add(sb.toString());
        }
        System.out.println(arrayList.size());
    }

    @Test
    public void testHashMap() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("key1", "value1");
        hashMap.put("key2", "value2");
        hashMap.put("key3", "value3");
        hashMap.put("key4", "value4");
        hashMap.put("key5", "value5");
        hashMap.put("key6", "value6");
        hashMap.put("key7", "value7");
        hashMap.put("key8", "value8");
        hashMap.put("key9", "value9");
        hashMap.put("key10", "value10");
        hashMap.put("key11", "value11");
        hashMap.put("key12", "value12");
        hashMap.put("key13", "value13");
        hashMap.put("key14", "value14");
        hashMap.put("key15", "value15");
        hashMap.put("key16", "value16");
        hashMap.put("key17", "value17");
        hashMap.put("key18", "value18");
        hashMap.put("key19", "value19");
        hashMap.put("key20", "value20");

        // jdk 1.7 这两个 key 的 hashcode 对应的下标会冲突
        String key9 = hashMap.get("key9");
        System.out.println(key9);
        String key20 = hashMap.get("key20");
        System.out.println(key20);



        // jdk 1.7 这两个 key 的 hashcode 对应的下标会冲突
//        Map<String, String> map = new ConcurrentHashMap();
//        map.put("key1", "value1");
//        map.put("key2", "value2");
//        map.put("key3", "value3");
//        map.put("key4", "value4");
//        map.put("key5", "value5");
//        map.put("key6", "value6");
//        map.put("key7", "value7");
//        map.put("key8", "value8");
//        map.put("key9", "value9");
//        map.put("key10", "value10");
//        map.put("key11", "value11");
//        map.put("key12", "value12");
//        map.put("key13", "value13");
//        map.put("key14", "value14");
//        map.put("key15", "value15");
//        map.put("key16", "value16");
//        map.put("key17", "value17");
//        map.put("key18", "value18");
//        map.put("key19", "value19");
//        map.put("key20", "value20");

        // jdk 1.7 这两个 key 的 hashcode 对应的下标会冲突
//        String key9_map = map.get("key9");
//        System.out.println(key9_map);
//        String key20_map = map.get("key20");
//        System.out.println(key20_map);


    }


}
