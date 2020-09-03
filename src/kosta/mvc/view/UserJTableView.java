package kosta.mvc.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import kosta.mvc.controller.UserListController;

public class UserJTableView  extends JFrame implements ActionListener{
	JMenu m = new JMenu("관리");
	JMenuItem  insert=new JMenuItem("가입");
	JMenuItem  update=new JMenuItem("수정");
	JMenuItem  delete=new JMenuItem("삭제");
	JMenuItem  quit=new JMenuItem("종료");
	JMenuBar mb=new JMenuBar();
		
	String [] name={"id","name","age","addr"};
	
	DefaultTableModel dt= new DefaultTableModel(name,0);
	JTable jt=new JTable(dt);
	JScrollPane jsp=new JScrollPane(jt);
	
	/*
		South 영역에 추가할 Componet들
	*/
	JPanel p= new JPanel();
	String [] comboName={"  ALL  ","  ID  "," name "," addr "};
	
	JComboBox combo = new JComboBox(comboName);
	JTextField jtf = new JTextField(20);
	JButton search = new JButton("검색");


	
/**
	화면구성 및 이벤트등록

*/
	public UserJTableView(){
		super("DB연동");
		
		m.add(insert);
		m.add(update);
		m.add(delete);
		m.add(quit);
		mb.add(m);

		setJMenuBar(mb);
		
		//South영역
		
		p.setBackground(Color.yellow);
		p.add(combo);
		p.add(jtf);
		p.add(search);

		add(jsp, "Center");
		add(p, "South");

		setSize(500,400);
		setLocationRelativeTo(null);//정가운데...
		setVisible(true);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//이벤트등록-> 이벤트주체.addXxxListener(이벤트구현클래스);
		
		insert.addActionListener(this);
		update.addActionListener(this);
		delete.addActionListener(this);
		search.addActionListener(this);
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		
		//Jtable위에 레코드 추가...
		List<Vector<Object>> list = UserListController.getSelectAll();
		if(list!=null && list.size()!=0) {
		  this.addRowTable(list);
		  
		  //첫번째 행을 선택!!!
		  jt.setRowSelectionInterval(0, 0);
		}
		
	}//생성자끝
	
	/**
	 * 검색된 레코드(List<Vector<Object>>)를 DefaultTableModel에 추가하는 메소드
	 * */
	 public void addRowTable(List<Vector<Object>> list) {
		 //기존 레코드 삭제!!
		/* for( ; dt.getRowCount() > 0 ; ) {
		    dt.removeRow(0);
		 }*/
		 
		 //dt에 있는 데이터 한번에 삭제
		 dt.setNumRows(0);
		 
		for(Vector<Object> v : list) {
			dt.addRow(v);//끝에 추가
		}
		
	 }//addRowTable끝
	
	

    /**
     * 가입/수정/삭제/검색 기능을 담당하는 메소드
     * */
		
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();//이벤트발생시키는 주체.
		if(obj==insert) {//가입
			new UserJDialogView(this, "가입");
			
		}else if(obj==update) {//수정
			new UserJDialogView(this, "수정");
		}else if(obj==delete) {//삭제
			int re = JOptionPane.showConfirmDialog(this, "정말 삭제 하실래요?");
			//System.out.println("re = " +re);
			//if(re==0) {//yes
			if(re==JOptionPane.YES_OPTION) {
				//선택된 행 번호 가져오기
				int rows [] = jt.getSelectedRows();
				//for(int r:rows)System.out.println(r);
				String ids [] = new String [rows.length];
				for(int i=0; i< rows.length ; i++) {
				   ids[i] = jt.getValueAt(rows[i], 0).toString();
				}
				
				//for(String id:ids)System.out.println(id);
				
				re = UserListController.userListDelete(ids);
				if(re>0) {
				  List<Vector<Object>> list = UserListController.getSelectAll();
				  if(list!=null && list.size()>0) {
						addRowTable(list);
						jt.setRowSelectionInterval(0, 0);
				  }
				}
			}
			
			
		}else if(obj==search) {//검색
			//combox 선택된 값 가져오기
			String keyField = combo.getSelectedItem().toString();
			if(keyField.trim().equals("ALL")) {//전체검색
				List<Vector<Object>> list = UserListController.getSelectAll();
				if(list!=null && list.size()>0) {
					addRowTable(list);
					jt.setRowSelectionInterval(0, 0);
				}
				
			}else {//조건검색
				//text박스의 값 입력유무체크
				String keyWord = jtf.getText();
				if(keyWord.equals("")) {
					FailView.errorMessage("검색단어를 입력해주세요.");
					jtf.requestFocus();
					return;
				}
				
				List<Vector<Object>>  list =
						UserListController.getSearchUser(keyField, keyWord);
				if(list!=null && list.size()>0) {
					addRowTable(list);
					jt.setRowSelectionInterval(0, 0);
				}
				
			}
			
			
			
			
		}
		
	}

}












