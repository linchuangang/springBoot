package redis;

import java.io.File;

/**
 * Created by lincg on 2017/6/28.
 */
public class ThreadTest extends Thread {

    public static void main(String[] args) {
//        ThreadTest t=new ThreadTest();
//        t.run();
//        System.out.println("22");
        String localPicPath = " E:/shopnc_recources/haha";
        File f = new File(localPicPath);
        if (!f.exists()) {
            f.mkdirs();
        }

    }
    public void run() {
        System.out.println("11");
    }
}
