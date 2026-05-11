package com.cafe.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.cafe.bll.AccountBLL;
import com.cafe.model.Account;

public class AccountView extends JFrame implements ActionListener {
	private AccountBLL accountBLL = new AccountBLL();

	private static final long serialVersionUID = 1L;
	private JPanel FormAc;
	private JTextField txtUsername;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnAdd;
	private JButton btnUpdate;
	private JButton btnLock;
	private JButton btnDelete;
	private JButton btnShow;
	private DefaultTableModel tableModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AccountView frame = new AccountView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AccountView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 309);
		FormAc = new JPanel();
		FormAc.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(FormAc);
		FormAc.setLayout(null);
		
		JLabel lblFindUsn = new JLabel("Find By Username");
		lblFindUsn.setBounds(70, 21, 116, 14);
		FormAc.add(lblFindUsn);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(198, 18, 116, 20);
		FormAc.add(txtUsername);
		txtUsername.setColumns(10);
		
		btnShow = new JButton("Show");
		btnShow.setBounds(329, 17, 84, 22);
		FormAc.add(btnShow);
		btnShow.addActionListener(this);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 49, 390, 170);
		FormAc.add(scrollPane);
		
		table = new JTable();
		tableModel = new DefaultTableModel();
		tableModel.addColumn("Id");
		tableModel.addColumn("Username");
		tableModel.addColumn("Password");
		tableModel.addColumn("Role");
		tableModel.addColumn("Status");
		
		loadData();
		
		table.setModel(tableModel);
		scrollPane.setViewportView(table);
		
		btnAdd = new JButton("Add");
		btnAdd.setBounds(47, 230, 78, 22);
		FormAc.add(btnAdd);
		btnAdd.addActionListener(this);
		
		btnUpdate = new JButton("Update");
		btnUpdate.setBounds(137, 230, 78, 22);
		FormAc.add(btnUpdate);
		btnUpdate.addActionListener(this);
		
		btnLock = new JButton("Lock");
		btnLock.setBounds(227, 230, 78, 22);
		FormAc.add(btnLock);
		btnLock.addActionListener(this);
		
		btnDelete = new JButton("Delete");
		btnDelete.setBounds(317, 230, 78, 22);
		FormAc.add(btnDelete);
		btnDelete.addActionListener(this);

	}
	
	public void loadData() {
		tableModel.setRowCount(0);
		for (var i : accountBLL.getAllAccounts()) {
			tableModel.addRow(new Object[] {
					i.getAccountId(), i.getUsername(), i.getPassword(), i.getRole(), i.getStatus() ? "Bình thường" : "Đã bị khoá"
			});
		}
		table.setModel(tableModel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnShow) {
			if (txtUsername.getText().equals("")) {
				loadData();
			} else {
				tableModel.setRowCount(0);
				Account ac = accountBLL.findByUsername(txtUsername.getText());
				tableModel.addRow(new Object[] {
						ac.getAccountId(), ac.getUsername(), ac.getPassword(), ac.getRole(), ac.getStatus() ? "Bình thường" : "Đã bị khoá"
				});
			}
		} else if (e.getSource() == btnLock) {
			if (table.getSelectedRowCount() == 1) {
				int index = table.getSelectedRow(); 
				int id = Integer.parseInt(table.getValueAt(index, 0).toString());
				Account ac = accountBLL.findById(id);
				ac.setStatus(!ac.getStatus());
				
				boolean kq = accountBLL.update(ac);
				if (kq)
					JOptionPane.showMessageDialog(FormAc, "Thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				else {
					JOptionPane.showMessageDialog(FormAc, "Thất bại", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				}
				loadData();
			}
		} else if (e.getSource() == btnDelete) {
			if (table.getSelectedRowCount() > 0) {
				List<Integer> li = new ArrayList<Integer>(); 
				for (int i : table.getSelectedRows()) {
					li.add((int)table.getValueAt(i, 0));
				}
				
				boolean kq = accountBLL.delete(li);
				if (kq)
					JOptionPane.showMessageDialog(FormAc, "Thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				else {
					JOptionPane.showMessageDialog(FormAc, "Thất bại", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				}
				loadData();
			}
		} else if (e.getSource() == btnAdd) {
			AccountDialog dialog = new AccountDialog(this, -1);
			dialog.setVisible(true);
		} else if (e.getSource() == btnUpdate) {
			if (table.getSelectedRowCount() == 1) {
				int id = (int)table.getValueAt(table.getSelectedRow(), 0);
				AccountDialog dialog = new AccountDialog(this, id);
				dialog.setVisible(true);
			}
		}
	}
}
