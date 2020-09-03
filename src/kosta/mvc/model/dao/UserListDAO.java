package kosta.mvc.model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import kosta.mvc.model.dto.UserListDTO;

public interface UserListDAO {
	/**
	 * 전체레코드 검색하기
	 * */
	List<Vector<Object>> getSelectAll()throws SQLException;
	
	/**
	 *  Id 중복체크 하기
	 *   @return : true이면 중복, false이면 중복아님.
	 * */
	boolean getCheckById(String id) throws SQLException;
	
	/**
	 * 가입하기
	 * */
	int userListInsert(UserListDTO userListDTO)throws SQLException;
	
	/**
	 * 수정하기
	 * (ID에 해당하는 이름,나이, 주소 수정) 
	 * */
	int userListUpdate(UserListDTO userListDTO)throws SQLException;
	
	/**
	 * 삭제하기
	 * (ID에 해당하는 레코드 삭제)
	 * */
	int userListDelete(String id) throws SQLException;
	
	/**
	 * 삭제하기
	 * (한번에 여러개 레코드 삭제하기)
	 * */
	int userListDelete(String [] ids)throws SQLException;
	
	/**
	 * keyField에 keyWord가 포함된 레코드 검색하기
	 * */
	List<Vector<Object>> getSearchUser(String keyField, String keyWord)throws SQLException;

}














