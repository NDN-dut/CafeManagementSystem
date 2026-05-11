package com.cafe.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.cafe.bll.AccountBLL;
import com.cafe.model.Account;
import com.cafe.model.Role;
import com.cafe.view.utils.ComboBoxItem;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;

public class AccountDialog extends JDialog implements ActionListener {
	private AccountBLL accountBLL = new AccountBLL();
	private AccountView accountView;
	
	private static final long serialVersionUID = 1L;
	private final JPanel detailForm = new JPanel();
	private JTextField txtUsername;
	private JTextField txtPassword;
	private int id;
	private JTextField txtId;
	private JLabel lblUsername;
	private JLabel lblPassword;
	JComboBox<String> cbbRole;
	JButton btnOk, btnCancel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AccountDialog dialog = new AccountDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AccountDialog() {}
	public AccountDialog(AccountView accountView, int id) {
		setTitle("Detail Form");
		this.id = id;
		this.accountView = accountView;
		setBounds(100, 100, 277, 260);
		getContentPane().setLayout(null);
		detailForm.setBounds(0, 0, 436, 223);
		detailForm.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(detailForm);
		{
			txtUsername = new JTextField();
			txtUsername.setBounds(109, 53, 116, 20);
			txtUsername.setColumns(10);
		}
		{
			lblUsername = new JLabel("Username");
			lblUsername.setBounds(31, 60, 78, 14);
		}
		{
			txtPassword = new JTextField();
			txtPassword.setBounds(109, 84, 116, 20);
			txtPassword.setColumns(10);
		}
		{
			lblPassword = new JLabel("Password");
			lblPassword.setBounds(31, 91, 78, 14);
		}
		
		txtId = new JTextField();
		txtId.setBounds(109, 22, 116, 20);
		txtId.setColumns(10);
		detailForm.setLayout(null);
		
		JLabel lblId = new JLabel("Id");
		lblId.setBounds(31, 29, 78, 14);
		detailForm.add(lblId);
		detailForm.add(txtId);
		detailForm.add(lblUsername);
		detailForm.add(txtUsername);
		detailForm.add(lblPassword);
		detailForm.add(txtPassword);
		
		ButtonGroup btnGroup = new ButtonGroup();
		
		cbbRole = new JComboBox();
		cbbRole.setBounds(109, 115, 118, 22);
		detailForm.add(cbbRole);
		
		JLabel lblRole = new JLabel("Role");
		lblRole.setBounds(31, 123, 78, 14);
		detailForm.add(lblRole);
		{
			btnOk = new JButton("OK");
			btnOk.setBounds(40, 168, 69, 23);
			detailForm.add(btnOk);
			btnOk.setActionCommand("OK");
			getRootPane().setDefaultButton(btnOk);
			btnOk.addActionListener(this);
		}
		{
			btnCancel = new JButton("Cancel");
			btnCancel.setBounds(145, 168, 78, 23);
			detailForm.add(btnCancel);
			btnCancel.setActionCommand("Cancel");
		}
		
		setCBB();
		init();
	}
	
	private void setCBB() {
		for (var i : Role.values()) {
			cbbRole.addItem(i.name());
		}
	}
	
	private void init() {
		if (id == -1) {
			txtId.setEditable(true);
		} else {
			txtId.setEditable(false);
			//update
			Account ac = accountBLL.findById(id);
			txtId.setText(Integer.toString(ac.getAccountId()));;
			txtUsername.setText(ac.getUsername());
			txtPassword.setText(ac.getPassword());
			
			for (int i = 0; i < cbbRole.getItemCount(); i++) {
				if (ac.getRole().name().equals(cbbRole.getItemAt(i))) {
					cbbRole.setSelectedItem(ac.getRole().name());
					break;
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnOk) {
			int id = Integer.parseInt(txtId.getText());
			String username = txtUsername.getText();
			String password = txtPassword.getText();
			Role role = Role.valueOf(cbbRole.getSelectedItem().toString());
			
			Account ac = new Account(id, username, password, role);
			
			boolean kq = accountBLL.addUpdate(ac);
			if (kq) {
				JOptionPane.showMessageDialog(this, "Thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				accountView.loadData();
				this.dispose();
			}
			else {
				JOptionPane.showMessageDialog(this, "Thất bại", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (e.getSource() == btnCancel) {
			this.dispose();
		}
	}
}
