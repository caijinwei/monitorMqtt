package com.wecon.box.test;

import java.security.KeyStore;
import java.sql.*;

/**
 * Created by cai95 on 2018/4/8.
 */
public class JdbcConnectTest
{
    public static void main(String[] args){

        //申明Connection对象
        Connection con;
        //驱动程序名
        String driver = "com.mysql.jdbc.Driver";

        //URL指向要访问的数据库
        String url ="jdbc:mysql://caijinwei.win:3306/wecon_pibox_db?useUnicode=true&characterEncoding=utf-8&autoReconnect=true";
        //MYSQL配置用户名
        String user = "root";

        //Mysql密码
        String password = "admin123";

        try{
            //加载驱动程序
            Class.forName(driver);

            //连接mysql数据库
            con = DriverManager.getConnection(url,user,password);

            //创建statement 类对象用于执行sql语句
            Statement state = con.createStatement();

            //sql查询语句
            String sql = "select * from account where 1=1";

            ResultSet rs = state.executeQuery(sql);


            int count = rs.getMetaData().getColumnCount();
            while(rs.next()) {//遍历行，next()方法返回值为Boolean，当存在数据行返回true,反之false
                for(int i = 1; i <= count; i++) {//遍历列
                    System.out.print(rs.getString(i));
                    if(i < count) {
                        System.out.print("---");//为数据隔开美观，没实际意义
                    }
                }
                System.out.println();
            }
            rs.close();

            if(con!=null){
                System.out.println("成功连接数据库");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
