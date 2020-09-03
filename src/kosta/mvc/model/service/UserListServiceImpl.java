package kosta.mvc.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import kosta.mvc.model.dao.UserListDAO;
import kosta.mvc.model.dao.UserListDAOImpl;
import kosta.mvc.model.dto.UserListDTO;

public class UserListServiceImpl implements UserListService {
    private UserListDAO dao = new UserListDAOImpl();
	@Override
	public List<Vector<Object>> getSelectAll() throws SQLException {
		List<Vector<Object>> list = dao.getSelectAll();
		if(list==null || list.size()==0) {
           throw new SQLException("�˻��� ���ڵ尡 �����ϴ�.");			
		}
		return list;
	}

	@Override
	public boolean getCheckById(String id) throws SQLException {
		return dao.getCheckById(id);
	}

	@Override
	public int userListInsert(UserListDTO userListDTO) throws SQLException {
		int re = dao.userListInsert(userListDTO);
		if(re==0)throw new SQLException("���Ե��� �ʾҽ��ϴ�.");
		return re;
	}

	@Override
	public int userListUpdate(UserListDTO userListDTO) throws SQLException {
		 int re = dao.userListUpdate(userListDTO);
		 if(re==0)throw new SQLException("�������� �ʾҽ��ϴ�.");
		return re;
	}

	@Override
	public int userListDelete(String id) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int userListDelete(String[] ids) throws SQLException {
		 int re = dao.userListDelete(ids);
		 if(re != ids.length) {
			 throw new SQLException("�������� �ʾҽ��ϴ�.");
		 }
		return re;
	}

	@Override
	public List<Vector<Object>> getSearchUser(String keyField, String keyWord) throws SQLException {
		List<Vector<Object>> list = dao.getSearchUser(keyField, keyWord);
		if(list==null || list.size()==0) {
			throw new SQLException(keyField+"�÷��� "+keyWord+"���� ã�� �� �����ϴ�.");
		}
		return list;
	}

}





