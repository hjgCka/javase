package com.hjg.proxy.customloader;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UnDelegationClassLoader extends ClassLoader{

    private String classpath;

    public UnDelegationClassLoader(String classpath) {
        //super();
        this.classpath = classpath;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        Class<?> clz = super.findLoadedClass(name);
        if(clz != null) {
            return clz;
        }

        //defineClass方法无法导入以java开头的类，会抛出安全异常
        if(name.startsWith("java")) {
            return ClassLoader.getSystemClassLoader().loadClass(name);
        }

        //没有使用双亲委派规则
        return this.findClass(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String classFilePath = this.classpath + name.replace(".", "/") + ".class";
        try(InputStream is = new FileInputStream(classFilePath)) {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int n = 0;
            while (-1 != (n = is.read(buffer))) {
                output.write(buffer, 0, n);
            }
            byte[] classBytes = output.toByteArray();

            return super.defineClass(name, classBytes, 0, classBytes.length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        //main1是启动类加载器加载的rt.jar中的类创建的实例对象
        sun.applet.Main main1 = new sun.applet.Main();

        //工作目录为E:\github\javase，所以需要写这个相对路径才能访问到classes目录下面
        String classpath = "proxy-classloader/target/classes/";
        UnDelegationClassLoader cl = new UnDelegationClassLoader(classpath);

        String name = "sun.applet.Main";
        Class<?> clz = cl.loadClass(name);
        Object main2 = clz.newInstance();

        //main2是由自定义的类加载器加载的类创建的实例对象
        System.out.println("main1 class: " + main1.getClass());
        System.out.println("main2 class: " + main2.getClass());
        System.out.println("main1 classloader: " + main1.getClass().getClassLoader());
        System.out.println("main2 classloader: " + main2.getClass().getClassLoader());

        //通过反射调用静态方法main
        Method method = clz.getMethod("main", String[].class);
        method.invoke(null, (Object)new String[]{});
    }
}
