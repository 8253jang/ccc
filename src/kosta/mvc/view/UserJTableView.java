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
	JMenu m = new JMenu("����");
	JMenuItem  insert=new JMenuItem("����");
	JMenuItem  update=new JMenuItem("����");
	JMenuItem  delete=new JMenuItem("����");
	JMenuItem  quit=new JMenuItem("����");
	JMenuBar mb=new JMenuBar();
		
	String [] name={"id","name","age","addr"};
	
	DefaultTableModel dt= new DefaultTableModel(name,0);
	JTable jt=new JTable(dt);
	JScrollPane jsp=new JScrollPane(jt);
	
	/*
		South ������ �߰��� Componet��
	*/
	JPanel p= new JPanel();
	String [] comboName={"  ALL  ","  ID  "," name "," addr "};
	
	JComboBox combo = new JComboBox(comboName);
	JTextField jtf = new JTextField(20);
	JButton search = new JButton("�˻�");


	
/**
	ȭ�鱸�� �� �̺�Ʈ���

*/
	public UserJTableView(){
		super("DB����");
		
		m.add(insert);
		m.add(update);
		m.add(delete);
		m.add(quit);
		mb.add(m);

		setJMenuBar(mb);
		
		//South����
		
		p.setBackground(Color.yellow);
		p.add(combo);
		p.add(jtf);
		p.add(search);

		add(jsp, "Center");
		add(p, "South");

		setSize(500,400);
		setLocationRelativeTo(null);//�����...
		setVisible(true);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//�̺�Ʈ���-> �̺�Ʈ��ü.addXxxListener(�̺�Ʈ����Ŭ����);
		
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
		
		
		//Jtable���� ���ڵ� �߰�...
		List<Vector<Object>> list = UserListController.getSelectAll();
		if(list!=null && list.size()!=0) {
		  this.addRowTable(list);
		  
		  //ù��° ���� ����!!!
		  jt.setRowSelectionInterval(0, 0);
		}
		
	}//�����ڳ�
	
	/**
	 * �˻��� ���ڵ�(List<Vector<Object>>)�� DefaultTableModel�� �߰��ϴ� �޼ҵ�
	 * */
	 public void addRowTable(List<Vector<Object>> list) {
		 //���� ���ڵ� ����!!
		/* for( ; dt.getRowCount() > 0 ; ) {
		    dt.removeRow(0);
		 }*/
		 
		 //dt�� �ִ� ������ �ѹ��� ����
		 dt.setNumRows(0);
		 
		for(Vector<Object> v : list) {
			dt.addRow(v);//���� �߰�
		}
		
	 }//addRowTable��
	
	

    /**
     * ����/����/����/�˻� ����� ����ϴ� �޼ҵ�
     * */
		
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();//�̺�Ʈ�߻���Ű�� ��ü.
		if(obj==insert) {//����
			new UserJDialogView(this, "����");
			
		}else if(obj==update) {//����
			new UserJDialogView(this, "����");
		}else if(obj==delete) {//����
			int re = JOptionPane.showConfirmDialog(this, "���� ���� �ϽǷ���?");
			//System.out.println("re = " +re);
			//if(re==0) {//yes
			if(re==JOptionPane.YES_OPTION) {
				//���õ� �� ��ȣ ��������
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
			
			
		}else if(obj==search) {//�˻�
			//combox ���õ� �� ��������
			String keyField = combo.getSelectedItem().toString();
			if(keyField.trim().equals("ALL")) {//��ü�˻�
				List<Vector<Object>> list = UserListController.getSelectAll();
				if(list!=null && list.size()>0) {
					addRowTable(list);
					jt.setRowSelectionInterval(0, 0);
				}
				
			}else {//���ǰ˻�
				//text�ڽ��� �� �Է�����üũ
				String keyWord = jtf.getText();
				if(keyWord.equals("")) {
					FailView.errorMessage("�˻��ܾ �Է����ּ���.");
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












