package com.cafe.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import com.cafe.bll.CategoryBLL;
import com.cafe.context.DbContext;
import com.cafe.model.Category;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CategoryManagement {
	private CategoryBLL categoryBLL = new CategoryBLL();
	
	private JFrame frmFormDanhMc;
	private JTextField txtCategory;
	private JTable table;
	private DefaultTableModel model;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CategoryManagement window = new CategoryManagement();
					window.frmFormDanhMc.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CategoryManagement() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmFormDanhMc = new JFrame();
		frmFormDanhMc.setTitle("Form Danh Mục");
		frmFormDanhMc.setBounds(100, 100, 450, 300);
		frmFormDanhMc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFormDanhMc.getContentPane().setLayout(null);
		
		txtCategory = new JTextField();
		txtCategory.setBounds(192, 24, 135, 20);
		frmFormDanhMc.getContentPane().add(txtCategory);
		txtCategory.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String txt = txtCategory.getText();
				boolean check = categoryBLL.add(txt);
				if (check)
					JOptionPane.showMessageDialog(frmFormDanhMc, "Successful");
				else 
					JOptionPane.showMessageDialog(frmFormDanhMc, "Failed");
				table.setModel(model);
				model.setRowCount(0);
				refresh();
			}
		});
		btnAdd.setBounds(102, 219, 76, 22);
		frmFormDanhMc.getContentPane().add(btnAdd);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(190, 219, 76, 22);
		frmFormDanhMc.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(278, 219, 76, 22);
		frmFormDanhMc.getContentPane().add(btnDelete);
		
		JButton btnRefresh = new JButton("Find");
		btnRefresh.setBounds(339, 22, 85, 22);
		frmFormDanhMc.getContentPane().add(btnRefresh);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 54, 412, 155);
		frmFormDanhMc.getContentPane().add(scrollPane);
		
		table = new JTable();
		
		model = new DefaultTableModel();
		model.addColumn("Category Id");
		model.addColumn("Category Name");

		for (Category c : DbContext.getInstance().categories) {
			model.addRow(new Object[] {
				c.getCategoryId(),
				c.getCategoryName()
			});
		}
		scrollPane.setViewportView(table);
		table.setModel(model);
		
		JLabel lblNewLabel = new JLabel("Category Name");
		lblNewLabel.setBounds(88, 27, 92, 14);
		frmFormDanhMc.getContentPane().add(lblNewLabel);
	}
	
	public void refresh() {
		for (Category c : DbContext.getInstance().categories) {
			model.addRow(new Object[] {
				c.getCategoryId(),
				c.getCategoryName()
			});
		}
		table.setModel(model);
	}
}
