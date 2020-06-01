package com.hjg.proxy.loader;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

public class ClassLoaderFrame extends JFrame {

    private JTextField keyField = new JTextField("3", 4);
    private JTextField nameField = new JTextField("Calculator", 30);
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    public ClassLoaderFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLayout(new GridBagLayout());
        add(new JLabel("Class"), new GBC(0, 0).setAnchor(GBC.EAST));
        add(nameField, new GBC(1, 0).setWeight(100, 0).setAnchor(GBC.WEST));
        add(new JLabel("Key"), new GBC(0, 1).setAnchor(GBC.EAST));
        add(keyField, new GBC(1, 1).setWeight(100, 0).setAnchor(GBC.WEST));
        JButton loadButton = new JButton("Load");
        add(loadButton, new GBC(0, 2, 2, 1));
        loadButton.addActionListener(event -> runClass(nameField.getText(), keyField.getText()));
        pack();
    }

    /**
     *
     * @param name 输入框的类名
     * @param key  输入框的key名称
     */
    public void runClass(String name, String key) {
        try {
            ClassLoader loader = new CryptoClassLoader(Integer.parseInt(key));
            Class<?> cl = loader.loadClass(name);
            Method method = cl.getMethod("main", String[].class);
            method.invoke(null, (Object)new String[]{});
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }
}

class CryptoClassLoader extends ClassLoader {
    private int key;

    public CryptoClassLoader(int k) {
        key = k;
    }

    //这个方法发不止导入了Calculator类，还导入了Calculator.InsertAction类等其它类
    //因为Calculator类还包含了Calculator.InsertAction类
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] classBytes = null;
            //找到一个类的字节码
            classBytes = loadClassBytes(name);
            //调用defineClass方法将字节码发送给虚拟机
            Class<?> cl = defineClass(name, classBytes, 0, classBytes.length);
            if (cl == null) throw new ClassNotFoundException(name);
            return cl;
        } catch (Throwable e) {
            throw new ClassNotFoundException(name);
        }
    }

    private byte[] loadClassBytes(String name) throws IOException {
        String cname = name.replace('.', '/') + ".caesar";
        //使用Class对象获取资源时，需要加上"/"，表示在classpath的根路径
        //否则会在该类的路径下搜寻资源
        cname = "/" + cname;
        System.out.println("cname = " + cname);

        try (
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                InputStream inputStream = this.getClass().getResourceAsStream(cname)
                ) {
            byte[] buffer = new byte[1024];
            int size = 0;
            while((size = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, size);
            }

            byte[] bytes = outputStream.toByteArray();
            System.out.println("bytes.length = " + bytes.length);
            //byte[] bytes = Files.readAllBytes(Paths.get(cname));

            for (int i = 0; i < bytes.length; i++)
                bytes[i] = (byte) (bytes[i] - key);
            return bytes;
        }



    }
}
