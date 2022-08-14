package com.johnny.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {

    private static DruidDataSource dataSource;
    private static ThreadLocal<Connection> conns = new ThreadLocal<Connection>();

    //初始化DruidDataSource
    static {
        try {
            Properties properties = new Properties();
            // 讀取jdbc.properties屬性配置文件
            InputStream inputStream = JdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
            // 從流中加載數據
            properties.load(inputStream);
            // 創建資料庫連線池
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 取得資料庫連線池中的連接
     * @return 如果返回null,說明獲取連線失敗<br/>有值就是獲取連線成功
     */
    public static Connection getConnection(){
        Connection conn = conns.get();
        if (conn == null) {
            try {
                conn = dataSource.getConnection();//從資料庫連線池中獲取連線
                conns.set(conn); // 保存到ThreadLocal物件中，供後面的jdbc操作使用
                conn.setAutoCommit(false); // 設置為手動管理交易
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    /**
     * 提交交易，並關閉釋放連線
     */
    public static void commitAndClose(){
        Connection connection = conns.get();
        if (connection != null) { // 如果不等於null，說明之前使用過連線，操作過資料庫
            try {
                connection.commit(); // 提交交易
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close(); // 關閉連線，釋放資源
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // 一定要執行remove操作，否則就會出錯。（因為Tomcat伺服器底層使用了執行緒池技術）
        conns.remove();
    }

    /**
     * 回滾交易，並關閉釋放連線
     */
    public static void rollbackAndClose(){
        Connection connection = conns.get();
        if (connection != null) { // 如果不等於null，說明之前使用過連線，操作過資料庫
            try {
                connection.rollback();//回滾交易
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close(); // 關閉連線，釋放資源
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // 一定要執行remove操作，否則就會出錯。（因為Tomcat伺服器底層使用了執行緒池技術）
        conns.remove();
    }


    /**
     * 關閉連線，放回資料庫連線池
     * @param conn

    public static void close(Connection conn){
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    } */

}