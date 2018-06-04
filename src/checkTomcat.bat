@echo off

rem=========第一步：配置下面的JAVA_HOME为JDK目录==========#
@set JAVA_HOME=D:/Program Files/Java/jdk1.8.0_101

rem=========第二步：配置下面的CATALINA_HOME为Tomcat目录==========#
@set CATALINA_HOME=C:/tomcat/apache-tomcat-9.0.0.M3

@set PATH=%JAVA_HOME%/bin/;
@set CLASSPATH=%JAVA_HOME%/lib/rt.jar;%JAVA_HOME%/lib/tools.jar;%CATALINA_HOME%/lib/servlet-api.jar;%CATALINA_HOME%/lib/jsp-api.jar;

java -Dfile.encoding=UTF-8 -classpath "." CheckTomcat

