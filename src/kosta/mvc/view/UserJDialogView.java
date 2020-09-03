package kosta.mvc.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import kosta.mvc.controller.UserListController;
import kosta.mvc.model.dto.UserListDTO;

public class UserJDialogView extends JDialog implements ActionListener{
	JPanel pw=new JPanel(new GridLayout(4,1));
	JPanel pc=new JPanel(new GridLayout(4,1));
	JPanel ps=new JPanel();

	JLabel lableId = new JLabel("ID");
	JLabel lableName=new JLabel("이름");
	JLabel lableAge=new JLabel("나이");
	JLabel lableAddr=new JLabel("주소");


	JTextField id=new JTextField();
	JTextField name=new JTextField();
	JTextField age=new JTextField();
	JTextField addr=new JTextField();
	

	JButton confirm;
	JButton reset=new JButton("취소");

   UserJTableView userJTableView;

   JPanel idCkP =new JPanel(new BorderLayout());
   JButton idCkBtn = new JButton("IDCheck");
   
  
   

	public UserJDialogView(UserJTableView userJTableView, String index){
		super(userJTableView,"다이어로그", true);
		
		this.userJTableView=userJTableView;
		
		if(index.equals("가입")){
			confirm=new JButton(index);
		}else{
			confirm=new JButton("수정");	
			
			//Jtable에서 선택된 레코드의 열의 값들을 text필드에 넣는다.
			int row = userJTableView.jt.getSelectedRow();
			
			id.setText((String)userJTableView.jt.getValueAt(row, 0));
			name.setText((String)userJTableView.jt.getValueAt(row, 1));
			age.setText((Integer)userJTableView.jt.getValueAt(row, 2)+"");
			addr.setText((String)userJTableView.jt.getValueAt(row, 3));
			
			//id text박스 비활성
			id.setEditable(false);
	
			//IDCheck버튼 비활성화
			idCkBtn.setEnabled(false);
		}
		
		
		//Label추가부분
		pw.add(lableId);//ID
		pw.add(lableName);//이름
		pw.add(lableAge);//나이
		pw.add(lableAddr);//주소
	
		
		idCkP.add(id,"Center");
		idCkP.add(idCkBtn,"East");
		
		//TextField 추가
		pc.add(idCkP);
		pc.add(name);
		pc.add(age);
		pc.add(addr);
		
		
		
		ps.add(confirm); 
		ps.add(reset);
	
		add(pw,"West"); 
		add(pc,"Center");
		add(ps,"South");
		
		setSize(300,250);
		setLocationRelativeTo(null);
		
       setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);//x 클릭
		
		//이벤트등록
        confirm.addActionListener(this); //가입/수정 이벤트등록
        reset.addActionListener(this); //취소 이벤트등록
        idCkBtn.addActionListener(this);// ID중복체크 이벤트 등록
        
		setVisible(true);
		
		
	}//생성자끝
    
	/**
	 * 가입/수정/취소/IDCheck 기능에 대한 부분
	 * */
	@Override
	public void actionPerformed(ActionEvent e) {
		String labelText = e.getActionCommand(); //버튼의 글씨
		if(labelText.equals("가입")) {
			//유효성  체크
			if( isValidate()) {
				UserListDTO userDTO = new UserListDTO(
					    id.getText(), 
			    		name.getText(), 
			    		Integer.parseInt(age.getText()), 
			    		addr.getText());
			
			int re = UserListController.userListInsert(userDTO );
		  
		  if(re>0) {//가입 성공.
			  dispose();//다이얼로그 창닫기
			  /*userJTableView.dt.addRow(
					  new Object[] {id.getText(),name.getText(),
							  age.getText(),addr.getText()});*/
			  
			 List<Vector<Object>> list = UserListController.getSelectAll();
			 if(list!=null && list.size() > 0) {
				 userJTableView.addRowTable(list);
				 userJTableView.jt.setRowSelectionInterval(0, 0);
			 }
		  }//
				
			}
			
		}else if(labelText.equals("수정")) {
			if(isValidate()) {
				UserListDTO dto= new UserListDTO(
						    id.getText(), 
				    		name.getText(), 
				    		Integer.parseInt(age.getText()), 
				    		addr.getText());
				
			 int re = UserListController.userListUpdate(dto);
			 
			   if(re>0) {//수정완료
				   dispose();
				   //선택된 행번호 가져와서 데이터 변경.
				   int row = userJTableView.jt.getSelectedRow();
				   //userJTableView.jt.setValueAt(id.getText(), row, 0);
				   userJTableView.jt.setValueAt(name.getText(), row, 1);
				   userJTableView.jt.setValueAt(age.getText(), row, 2);
				   userJTableView.jt.setValueAt(addr.getText(), row, 3);
			   }
			}
		}else if(labelText.equals("IDCheck")) {
			//id박스이 값을 가져온다.
			String checkId = id.getText();
			
			//text박스에 값 입력유무 체크
			if(checkId.equals("")) {
				FailView.errorMessage("ID를 반드시 입력해주세요.");
				id.requestFocus();//커서놓기
				return;//함수 빠져나가라.
			}
			
			//controller의 중복체크메소드를 호출한다.
			
			//그 결과에 따라 메시지를  출력한다.
			if( UserListController.getCheckById(checkId)) {
				FailView.errorMessage(checkId+"는 이미 사용중입니다.");
				id.setText("");
				id.requestFocus();//커서놓기
			}else {
				SuccessView.successMessage(checkId+"는 사용가능합니다.");
			}
			
		}else if(labelText.equals("취소")) {
			dispose();//다이얼로그 창 닫기
		}
		
		
	}//actionPerformed끝
	
	/**
	 * 유효성 체크 메소드
	 * @return : true 이면 성공, false이면 실패
	 * */
	public boolean isValidate(){
		//아이디체크
		if(id.getText().trim().equals("")) {
			FailView.errorMessage("ID를 입력해주세요");
			id.requestFocus();
			return false;
		}
		
		//이름 체크
		if(name.getText().trim().equals("")) {
			FailView.errorMessage("이름을 입력해주세요");
			name.requestFocus();
			return false;
		}
		
		//나이 체크
		if(age.getText().trim().equals("")) {
			FailView.errorMessage("나이를 입력해주세요");
			age.requestFocus();
			return false;
		}else {
			//숫자인지 체크
			try {
			 Integer.parseInt(age.getText());
			}catch (NumberFormatException e) {
				FailView.errorMessage("나이는 숫자만 입력해주세요.");
				age.setText("");
				age.requestFocus();
				return false;
			}
		}
		
		//주소체크
		if(addr.getText().trim().equals("")) {
			FailView.errorMessage("주소를 입력해주세요");
			addr.requestFocus();
			return false;
		}
	
		return true;
	}
	
	
}//클래스끝







