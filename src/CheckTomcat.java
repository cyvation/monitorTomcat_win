import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CheckTomcat {
    private static String tomcatroot = "";
    private static String monitorurl = "";
    private static int sleepminutes = 2;
    private static RunTomcat runt = new RunTomcat();

    private static void checkTomcatIsAlive(String myurl) throws NullPointerException {
        String s;
        boolean isTomcatAlive = false;
        java.io.BufferedReader in;
        try {
//            System.out.println(">>>>>>检测URL：" + myurl);
            System.out.print(".");
            URL url = new URL(myurl);
            URLConnection con = url.openConnection();
            in = new java.io.BufferedReader(new java.io.InputStreamReader(con.getInputStream()));
            con.setConnectTimeout(1000);
            con.setReadTimeout(4000);
            while ((s = in.readLine()) != null) {
                if (s.length() > 0) {// 如果能够读取到页面则证明可用，tomcat正常，否则继续后面的重启tomcat操作。

                    return;
                }
            }
            in.close();
        } catch (Exception ex) {
            //ex.printStackTrace();
            System.out.println("*************该URL不可访问！");
        }
  
  /*if (isTomcatAlive) {
   System.out.println("<" + new Date()+ "> Tomcat is alive but not response!");
   stopTomcat();
  }*/
        runt.startTomcat(tomcatroot);
    }
 
 /*public static void stopTomcat() {
  try {
   //java.lang.Process p = java.lang.Runtime.getRuntime().exec("net stop /"Apache Tomcat/"");
   java.lang.Process p = java.lang.Runtime.getRuntime().exec(tomcatroot+"bin//shutdown.bat");
   java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(p.getInputStream()));
   String s;
   String t = "Using JRE_HOME";
   boolean restart = false;
   while ((s = in.readLine()) != null) {
    if (s.indexOf(t) != -1) {
     restart = true;
     break;
    }
   }
   System.out.println("<" + new Date() + "> Tomcat is stop "+ (restart ? "OK" : "ERROR"));
  } catch (Exception e) {
   e.printStackTrace();
  }
 }*/

    public static void main(String[] args) throws IOException {
        System.out.println("********************************************************");
        System.out.println("====本程序自动检测Tomcat运行状况，必要时自动重启Tomcat。====");
        System.out.println("********************************************************");

        init_config();
        if (monitorurl.equals("")) monitorurl = "http://localhost:8080/cqe/account/loginDwbmTree";
        if (tomcatroot.equals("")) tomcatroot = "C://tomcat//apache-tomcat-9.0.0.M3";
        if (!tomcatroot.endsWith("/")) tomcatroot += "/";
        while (true) {
            try {
                String random = "?random=" + Math.random() * 65535;//=====处理数据缓存问题======
                CheckTomcat.checkTomcatIsAlive(monitorurl + random);
                TimeUnit.MINUTES.sleep(sleepminutes);
//                System.out.println("========================checking at <" + new Date() + ">");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    static private void init_config() {
        try {
            CheckTomcat me = new CheckTomcat();
            String maindir = me.getClass().getResource("/").toURI().getPath();
            System.out.println(">>>>>>配置文件目录：" + maindir);
            String sLine;
            String filename = maindir + "config.xml";
            BufferedReader buffReader = new BufferedReader(new FileReader(filename));
            while ((sLine = buffReader.readLine()) != null) {
                sLine = sLine.trim();
                if (sLine.trim() != "" && !sLine.equals("")) {
                    if (sLine.toLowerCase().startsWith("tomcatroot")) {
                        int npos = sLine.indexOf("tomcatroot");
                        npos += "tomcatroot".length();
                        tomcatroot = sLine.substring(npos).trim();
                        if (tomcatroot.startsWith("=")) tomcatroot = tomcatroot.substring(1);
                    } else if (sLine.toLowerCase().startsWith("monitorurl")) {
                        int npos = sLine.indexOf("monitorurl");
                        npos += "monitorurl".length();
                        monitorurl = sLine.substring(npos).trim();
                        if (monitorurl.startsWith("=")) monitorurl = monitorurl.substring(1);
                    } else if (sLine.toLowerCase().startsWith("sleepminutes")) {
                        int npos = sLine.indexOf("sleepminutes");
                        npos += "sleepminutes".length();
                        String sleepminutes_str = sLine.substring(npos).trim();
                        if (sleepminutes_str.startsWith("=")) {
                            sleepminutes_str = sleepminutes_str.substring(1);
                            sleepminutes = Integer.valueOf(sleepminutes_str);
                        }

                    }
                }
            }
            buffReader = null;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("********************************************************");
            System.out.println("====读取配置文件失败！系统无法运行，请与供应商联系。====");
            System.out.println("********************************************************");
            System.exit(0);
        }
    }
}