package com.test.dao.jdbcimpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.test.entity.User;

public class TestDBIDU {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// 0. 加载驱动jar包
		// 0. 注册驱动程序
		Class.forName("com.mysql.jdbc.Driver");
		// 1. 创建连接
		String url= "jdbc:mysql://127.0.0.1:3306/mydb";
		String usn = "root";
		String pwd = "root";
		Connection conn = DriverManager.getConnection(url, usn, pwd);		
		//String sql = "insert into Users values(null, ?, ?)";
		//String sql = "update Users set name = ? where id = ?";
		String sql = "delete from Users where id = ?";
		// 2. 创建数据库操作命令承载的容器
		PreparedStatement pstmt = conn.prepareStatement(sql);
		// 参数设置
//		User user = new User("wangwu", "wangwu");
//		pstmt.setString(1, user.getName());
//		pstmt.setString(2, user.getPassword());
//		pstmt.setString(1, "ww");
//		pstmt.setInt(2, 3);
		pstmt.setInt(1, 3);
		// 3. 执行
		int result = pstmt.executeUpdate();
		System.out.println(result);
		
		conn.close();

	}

}
