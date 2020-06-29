package com.hjg.proxy.loader;

import javax.swing.*;
import java.awt.*;

public class CustomClassLoaderApp {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new ClassLoaderFrame();
            frame.setTitle("ClassLoaderTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
