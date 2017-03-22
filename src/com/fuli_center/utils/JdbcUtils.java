package com.fuli_center.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * 数据库连接、释放连接的工具库
 * @author yao
 *
 */
public class JdbcUtils {

	static{
		try {
			//使用反射加载sqlite驱动
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection(){
		// 创建数据库连接
		try { 
			Connection connection=DriverManager.getConnection(
					"jdbc:sqlite://E:/EclipseWorkSpace/FuliCenterSqlite/fuli_center.db");
			///Users/clawpo/work/ucai/work/projects/FuLiCenter/source/FuLiCenterServerExt/src/fuli_center.db
			//"/Users/clawpo/work/ucai/work/projects/FuLiCenter/20160317/FuLiCenterServerExt/src/fuli_center.db");
			//"jdbc:sqlite://D:/chensaitao/fulishe/FuLiCenterServerExt/src/fuli_center.db");
//				"jdbc:sqlite::resource:fuli_center.db");
			return connection;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 释放与数据库的连接和相关资源
	 * @param resultSet
	 * @param statement
	 * @param connection
	 */
	public static void closeAll(ResultSet resultSet,PreparedStatement statement,Connection connection){
		try {
			//释放结果集资源
			if(resultSet!=null){
				resultSet.close();
			}
			//释放预处理sql命令的资源
			if(statement!=null){
				statement.close();
			}
			//释放与数据库连接的资源
			if(connection!=null){
				connection.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
