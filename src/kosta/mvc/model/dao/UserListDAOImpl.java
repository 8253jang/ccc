package kosta.mvc.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import kosta.mvc.model.dto.UserListDTO;
import kosta.mvc.model.util.DbUtil;

public class UserListDAOImpl implements UserListDAO {

	@Override
	public List<Vector<Object>> getSelectAll() throws SQLException {
		Connection con = null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<Vector<Object>> list = new ArrayList<>();
	  try {
		  con=DbUtil.getConnection();
		  ps=con.prepareStatement("SELECT ID,NAME,AGE,ADDR FROM USERLIST");
	      rs=ps.executeQuery();
	      while(rs.next()) {
	    	  Vector<Object> v =new Vector<>();
	    	  v.add(rs.getString("ID"));
	    	  v.add(rs.getString("name"));
	    	  v.add(rs.getInt("age"));
	    	  v.add(rs.getString("addr"));
	    	  list.add(v);
	      }
	  }finally {
		  DbUtil.dbClose(con, ps, rs);
	  }
		return list;
	}

	@Override
	public boolean getCheckById(String id) throws SQLException {
		Connection con = null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		boolean result = false;
	  try {
		  con=DbUtil.getConnection();
		  ps=con.prepareStatement("SELECT ID,NAME,AGE,ADDR "
		  		+ "FROM USERLIST where lower(id) =lower(?)");
	      ps.setString(1, id);
		  
		  rs=ps.executeQuery();
	      if(rs.next()) {
	    	  result = true;
	      }
	  }finally {
		  DbUtil.dbClose(con, ps, rs);
	  }
		return result;
	}

	@Override
	public int userListInsert(UserListDTO userListDTO) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		int result=0;
		try {
			con=DbUtil.getConnection();
			ps=con.prepareStatement("INSERT INTO USERLIST"
					+ "(ID,NAME,AGE,ADDR) VALUES(?,?,?,?)");
			ps.setString(1, userListDTO.getId());
			ps.setString(2, userListDTO.getName());
			ps.setInt(3, userListDTO.getAge());
			ps.setString(4, userListDTO.getAddr());
			
			result = ps.executeUpdate();
			
		}finally {
			DbUtil.dbClose(con, ps);
		}
		return result;
	}

	@Override
	public int userListUpdate(UserListDTO userListDTO) throws SQLException {
		Connection con=null;
		PreparedStatement ps= null;
		int result=0;
		try {
			 con = DbUtil.getConnection();
			 ps= con.prepareStatement("UPDATE USERLIST "
			 		+ "SET NAME=? , AGE=?, ADDR=? WHERE ID=?");
			 ps.setString(1, userListDTO.getName());
			 ps.setInt(2, userListDTO.getAge());
			 ps.setString(3, userListDTO.getAddr());
			 ps.setString(4, userListDTO.getId());
			 result = ps.executeUpdate();
		 }finally {
			 DbUtil.dbClose(con, ps);
		 }
		return result;
	}

	@Override
	public int userListDelete(String id) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int userListDelete(String[] ids) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		int result=0;
		String sql="DELETE FROM USERLIST WHERE ID IN (";
		try {
			for(int i=0; i<ids.length ; i++) {
				if(i==(ids.length-1))
				  sql+="?)";
				else 
					sql+="?,";
			}
			//System.out.println(sql);
			con = DbUtil.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql);
			for(int i=0; i< ids.length ; i++) {
				ps.setString((i+1), ids[i]);
			}
			result=ps.executeUpdate();
		}finally {
			if(result==ids.length) con.commit();
			else con.rollback();
			
			DbUtil.dbClose(con, ps);
		}
		return result;
	}

	@Override
	public List<Vector<Object>> getSearchUser(String keyField, String keyWord) throws SQLException {
		Connection con = null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<Vector<Object>> list = new ArrayList<>();
		String sql=
				"SELECT ID,NAME,AGE,ADDR FROM USERLIST "
				+ "WHERE lower("+keyField + ") like lower(?)";
	  try {
		  
		  con=DbUtil.getConnection();
		  ps=con.prepareStatement(sql);
		  ps.setString(1, "%"+keyWord+"%");
		  
	      rs=ps.executeQuery();
	      while(rs.next()) {
	    	  Vector<Object> v =new Vector<>();
	    	  v.add(rs.getString("ID"));
	    	  v.add(rs.getString("name"));
	    	  v.add(rs.getInt("age"));
	    	  v.add(rs.getString("addr"));
	    	  list.add(v);
	      }
	  }finally {
		  DbUtil.dbClose(con, ps, rs);
	  }
		return list;
	
	}

}
