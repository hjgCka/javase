package sun.applet;

public class Main {

    public Main() {
        System.out.println("constructed");
    }

    //如果不用自定义的类加载器，则会使用默认的类加载器。
    //原来的sun.applet.Main在rt.jar，这个类会由启动类加载器加载
    public static void main(String[] args) {
        ClassLoader classloader = Main.class.getClassLoader();
        System.out.println("classloader = " + classloader);
        System.out.println("sun.applet.Main is writen by hjg");
    }
}
