package com.demo.reflect;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.google.common.base.Stopwatch;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 3 种反射方法效率对比
 */
public class ReflectASMTest {

    private long times = 100_000_000L;
    private ReflectASM asmBean;
    private String formatter = "%s %d times using %d ms";

    @Before
    public void setUp() throws Exception {
        asmBean = new ReflectASM();
        asmBean.setUsername("ReflectASM 测试效率");
    }

    @Test
    public void javaGet(){
        Stopwatch watch = Stopwatch.createStarted();
        for (int i = 0; i < times; i++) {
            asmBean.getUsername();
        }
        watch.stop();
        String result = String.format(formatter, "javaGet", times, watch.elapsed(TimeUnit.MILLISECONDS));
        System.out.println(result);
    }

    @Test
    public void reflectASMGet(){
        MethodAccess methodAccess = MethodAccess.get(ReflectASM.class);
        Stopwatch watch = Stopwatch.createStarted();
        for (int i = 0; i < times; i++) {
            methodAccess.invoke(asmBean, "getUsername");
        }
        watch.stop();
        String result = String.format(formatter, "reflectASMGet", times, watch.elapsed(TimeUnit.MILLISECONDS));
        System.out.println(result);
    }

    @Test
    public void javaReflectGet() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = ReflectASM.class.getMethod("getUsername");
        Stopwatch watch = Stopwatch.createStarted();
        for (long i = 0; i < times; i++) {
            method.invoke(asmBean);
        }
        watch.stop();
        String result = String.format(formatter, "javaReflectGet", times, watch.elapsed(TimeUnit.MILLISECONDS));
        System.out.println(result);
    }

}
