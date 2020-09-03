package kosta.mvc.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import kosta.mvc.model.dto.UserListDTO;
import kosta.mvc.model.service.UserListService;
import kosta.mvc.model.service.UserListServiceImpl;
import kosta.mvc.view.FailView;
import kosta.mvc.view.SuccessView;

public class UserListController {
	private static UserListService service = new UserListServiceImpl();
    /**
     * 전체레코드 검색하기
     * */
	public static List<Vector<Object>> getSelectAll() {
		//서비스 호출
		List<Vector<Object>> list=null;
		try {
		  list = service.getSelectAll();
		 
		}catch (Exception e) {
			e.printStackTrace();
			FailView.errorMessage(e.getMessage());
		}
		return list;
	}
	
	/**
	 * 검색단어에 해당하는 레코드 검색하기
	 * */
	public static List<Vector<Object>> getSearchUser(String keyField, String keyWord) {
      List<Vector<Object>> list =null;
	  try {
    	   list=service.getSearchUser(keyField, keyWord);
    	   
       }catch (Exception e) {
    	 e.printStackTrace();
		 FailView.errorMessage(e.getMessage());
	   }
		return list;
	}

	public static boolean getCheckById(String id) {
		boolean result = true;
		try {
			 result = service.getCheckById(id);
		}catch(SQLException e) {
			FailView.errorMessage(e.getMessage());
		}
		return result;
	}

	/**
	 * 가입하기
	 * */
	public static int userListInsert(UserListDTO userListDTO) {
		int result=0;
		try {
			result = service.userListInsert(userListDTO);
			SuccessView.successMessage(userListDTO.getName()+"님 가입 되었습니다.");
		}catch(Exception e) {
			FailView.errorMessage(e.getMessage());
		}
		return result;
	}

	/**
	 * 정보 수정하기
	 * */
	public static int userListUpdate(UserListDTO userListDTO) {
		int result=0;
		try {
			result = service.userListUpdate(userListDTO);
			SuccessView.successMessage("정보가 수정되었습니다.");
		}catch (Exception e) {
			FailView.errorMessage(e.getMessage());
		}
		return result;
	}

	/**
	 * id에 해당하는 레코드 삭제하기
	 * */
	public static int userListDelete(String[] ids) {
		int result=0;
		try {
			result = service.userListDelete(ids);
			SuccessView.successMessage("삭제되었습니다.");
		}catch (Exception e) {
			FailView.errorMessage(e.getMessage());
		}
		return result;
	}

}








