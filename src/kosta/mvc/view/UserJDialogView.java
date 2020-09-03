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
	JLabel lableName=new JLabel("�̸�");
	JLabel lableAge=new JLabel("����");
	JLabel lableAddr=new JLabel("�ּ�");


	JTextField id=new JTextField();
	JTextField name=new JTextField();
	JTextField age=new JTextField();
	JTextField addr=new JTextField();
	

	JButton confirm;
	JButton reset=new JButton("���");

   UserJTableView userJTableView;

   JPanel idCkP =new JPanel(new BorderLayout());
   JButton idCkBtn = new JButton("IDCheck");
   
  
   

	public UserJDialogView(UserJTableView userJTableView, String index){
		super(userJTableView,"���̾�α�", true);
		
		this.userJTableView=userJTableView;
		
		if(index.equals("����")){
			confirm=new JButton(index);
		}else{
			confirm=new JButton("����");	
			
			//Jtable���� ���õ� ���ڵ��� ���� ������ text�ʵ忡 �ִ´�.
			int row = userJTableView.jt.getSelectedRow();
			
			id.setText((String)userJTableView.jt.getValueAt(row, 0));
			name.setText((String)userJTableView.jt.getValueAt(row, 1));
			age.setText((Integer)userJTableView.jt.getValueAt(row, 2)+"");
			addr.setText((String)userJTableView.jt.getValueAt(row, 3));
			
			//id text�ڽ� ��Ȱ��
			id.setEditable(false);
	
			//IDCheck��ư ��Ȱ��ȭ
			idCkBtn.setEnabled(false);
		}
		
		
		//Label�߰��κ�
		pw.add(lableId);//ID
		pw.add(lableName);//�̸�
		pw.add(lableAge);//����
		pw.add(lableAddr);//�ּ�
	
		
		idCkP.add(id,"Center");
		idCkP.add(idCkBtn,"East");
		
		//TextField �߰�
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
		
       setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);//x Ŭ��
		
		//�̺�Ʈ���
        confirm.addActionListener(this); //����/���� �̺�Ʈ���
        reset.addActionListener(this); //��� �̺�Ʈ���
        idCkBtn.addActionListener(this);// ID�ߺ�üũ �̺�Ʈ ���
        
		setVisible(true);
		
		
	}//�����ڳ�
    
	/**
	 * ����/����/���/IDCheck ��ɿ� ���� �κ�
	 * */
	@Override
	public void actionPerformed(ActionEvent e) {
		String labelText = e.getActionCommand(); //��ư�� �۾�
		if(labelText.equals("����")) {
			//��ȿ��  üũ
			if( isValidate()) {
				UserListDTO userDTO = new UserListDTO(
					    id.getText(), 
			    		name.getText(), 
			    		Integer.parseInt(age.getText()), 
			    		addr.getText());
			
			int re = UserListController.userListInsert(userDTO );
		  
		  if(re>0) {//���� ����.
			  dispose();//���̾�α� â�ݱ�
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
			
		}else if(labelText.equals("����")) {
			if(isValidate()) {
				UserListDTO dto= new UserListDTO(
						    id.getText(), 
				    		name.getText(), 
				    		Integer.parseInt(age.getText()), 
				    		addr.getText());
				
			 int re = UserListController.userListUpdate(dto);
			 
			   if(re>0) {//�����Ϸ�
				   dispose();
				   //���õ� ���ȣ �����ͼ� ������ ����.
				   int row = userJTableView.jt.getSelectedRow();
				   //userJTableView.jt.setValueAt(id.getText(), row, 0);
				   userJTableView.jt.setValueAt(name.getText(), row, 1);
				   userJTableView.jt.setValueAt(age.getText(), row, 2);
				   userJTableView.jt.setValueAt(addr.getText(), row, 3);
			   }
			}
		}else if(labelText.equals("IDCheck")) {
			//id�ڽ��� ���� �����´�.
			String checkId = id.getText();
			
			//text�ڽ��� �� �Է����� üũ
			if(checkId.equals("")) {
				FailView.errorMessage("ID�� �ݵ�� �Է����ּ���.");
				id.requestFocus();//Ŀ������
				return;//�Լ� ����������.
			}
			
			//controller�� �ߺ�üũ�޼ҵ带 ȣ���Ѵ�.
			
			//�� ����� ���� �޽�����  ����Ѵ�.
			if( UserListController.getCheckById(checkId)) {
				FailView.errorMessage(checkId+"�� �̹� ������Դϴ�.");
				id.setText("");
				id.requestFocus();//Ŀ������
			}else {
				SuccessView.successMessage(checkId+"�� ��밡���մϴ�.");
			}
			
		}else if(labelText.equals("���")) {
			dispose();//���̾�α� â �ݱ�
		}
		
		
	}//actionPerformed��
	
	/**
	 * ��ȿ�� üũ �޼ҵ�
	 * @return : true �̸� ����, false�̸� ����
	 * */
	public boolean isValidate(){
		//���̵�üũ
		if(id.getText().trim().equals("")) {
			FailView.errorMessage("ID�� �Է����ּ���");
			id.requestFocus();
			return false;
		}
		
		//�̸� üũ
		if(name.getText().trim().equals("")) {
			FailView.errorMessage("�̸��� �Է����ּ���");
			name.requestFocus();
			return false;
		}
		
		//���� üũ
		if(age.getText().trim().equals("")) {
			FailView.errorMessage("���̸� �Է����ּ���");
			age.requestFocus();
			return false;
		}else {
			//�������� üũ
			try {
			 Integer.parseInt(age.getText());
			}catch (NumberFormatException e) {
				FailView.errorMessage("���̴� ���ڸ� �Է����ּ���.");
				age.setText("");
				age.requestFocus();
				return false;
			}
		}
		
		//�ּ�üũ
		if(addr.getText().trim().equals("")) {
			FailView.errorMessage("�ּҸ� �Է����ּ���");
			addr.requestFocus();
			return false;
		}
	
		return true;
	}
	
	
}//Ŭ������







