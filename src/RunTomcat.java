import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class RunTomcat extends Thread {


    private static String tomcatroot = "";

    public void startTomcat(String root) {
        this.tomcatroot = root;

        System.out.println(">>>>>>Tomcat即将启动。。。");
        System.out.println(">>>>>>Tomcat根目录：" + tomcatroot);
        try {
            //java.lang.Process p = java.lang.Runtime.getRuntime().exec("net stop /"Apache Tomcat/"");
            String cmdPath = tomcatroot + "bin/shutdown.bat";
            System.out.println("先关闭tomcat，命令路径：\n" + cmdPath);
            java.lang.Process p_shutdown = java.lang.Runtime.getRuntime().exec(cmdPath);

            int exitValue = -1;
            for (int i = 1; i < 10; i++) {
                try {
                    TimeUnit.SECONDS.sleep(20);//等待shutdown结束
                    exitValue = p_shutdown.exitValue();
                } catch (Exception stex) {
                    System.err.println("未完成关闭，继续等待");
                }
                if (exitValue != -1) {
                    System.out.println("成功关闭进程exitValue:" + exitValue);
                    break;
                }
            }
            System.out.println((new Date())+"启动tomcat......");
            //RunTomcat me=new RunTomcat();
            //String maindir=me.getClass().getResource("/").toURI().getPath();
            //java.lang.Process p = java.lang.Runtime.getRuntime().exec(maindir+"checkTomcat.bat");
            java.lang.Process p_start = java.lang.Runtime.getRuntime().exec(tomcatroot + "bin/startup.bat");

//            int stExt = -1;
//            for (int i = 1; i < 10; i++) {
//                try {
                    TimeUnit.SECONDS.sleep(60);//等待startn结束
//                    stExt = p_start.exitValue();
//                } catch (Exception stex) {
//                    System.err.println("未完成启动，继续等待");
//                }
//                if (stExt != -1) {
//                    System.out.println("成功启动exitValue:" + stExt);
//                    CheckTomcat.runThdLoacl.set(Boolean.FALSE);
//                    break;
//                }
//            }
//            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(p.getInputStream()));
   
   /*String s;
   boolean restart = false;
   String t = "Server startup in";
   while ((s = in.readLine()) != null) {
    System.out.println(s);
    if (s.indexOf(t) != -1) {
     restart = true;
     break;
    }
   }*/
            System.out.println(">>>>>>Tomcat start at <" + new Date() + ">");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}