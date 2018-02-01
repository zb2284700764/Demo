package other.test;

import org.junit.Test;

import java.util.Scanner;

public class Test1 {


    @Test
    public void test() {

        int b = -17 >>> 2;
        System.out.println("-17 >>> 2 = " + b);

        // 通过循环的方式求 -17 >>> 2 的结果
        int result = 3;
        for (int i = 0; i < 27; i++) {
            int pow = (int) Math.pow(2, (i + 3));
            result += pow;
        }
        System.out.println("通过循环的方式求 -17 >>> 2 的结果：" + result);

        int c = 17 >>> 2;
        System.out.println("17 >>> 2 = " + c);

        int d = 17 >> 2;
        System.out.println("17 >> 2 = " + d);

        int e = -17 >> 2;
        System.out.println("-17 >> 2 = " + e);

        String str = "hello";
        System.out.println("codePointCount：" + str.codePointCount(0, str.length()));

        // 字符串对应的码点数组
        int[] pointArr = "asdfwerqd".codePoints().toArray();
        // 将码点数组转换成字符串
        String s = new String(pointArr, 0, pointArr.length);
        System.out.println(s);


    }
}
