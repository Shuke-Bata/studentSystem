package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        }

        try {
            System.out.println("开始加载驱动。。。。");
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("驱动加载成功。。。。\n开始链接！");
            /*String url = "jdbc:mysql://localhost:3306/student?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=UTF-8&useSSL=false";
            String user = "root";
            String password = "zmhjrR@512";*/
            String url = "jdbc:mysql://121.36.58.0:3306/student?useSSL=false&autoReconnect=true&failOverReadOnly=false&maxReconnects=10";
            String user = "root";
            String password = "zmh_jrR@512";
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("数据库连接成功");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("加载驱动失败！");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("连接数据库失败！");
        }
        return connection;
    }

    public static Statement getStatement() throws Exception {
        try {
            Statement statement = getConnection().createStatement();
            return statement;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
