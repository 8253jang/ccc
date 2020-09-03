package kosta.mvc.model.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DB���� �ε�, ����, �ݱ⸦ ���� Ŭ����
 * */
public class DbUtil {
  /**
   * �ε�
   * */
	static {
		try {
		 Class.forName(DbProperty.DRIVER_NAME);
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
  /**
   * ����
   * */
	public static Connection getConnection()throws SQLException{
		return DriverManager.getConnection(
				 DbProperty.URL,DbProperty.USER_NAME, DbProperty.USER_PASS);
		 
	}
	
  /**
   * �ݱ�
   * insert, update, delete�ΰ��
   * */
	public static void dbClose(Connection con , Statement st){
		try {
		 if(st!=null) st.close();
		 if(con!=null)con.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * select�ΰ�� �ݱ�
	 * */
	public static void dbClose(Connection con , Statement st , ResultSet rs) {
		try {
		 if(rs!=null)rs.close();
		 dbClose(con, st);
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
}











