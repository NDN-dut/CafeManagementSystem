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
	private JFrame frame;
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
					window.frame.setVisible(true);
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
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtCategory = new JTextField();
		txtCategory.setBounds(289, 23, 135, 20);
		frame.getContentPane().add(txtCategory);
		txtCategory.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String txt = txtCategory.getText();
				boolean check = CategoryBLL.getInstance().add(txt);
				if (check)
					JOptionPane.showMessageDialog(frame, "Successful");
				else 
					JOptionPane.showMessageDialog(frame, "Failed");
				table.setModel(model);
				model.setRowCount(0);
				refresh();
			}
		});
		btnAdd.setBounds(47, 230, 76, 22);
		frame.getContentPane().add(btnAdd);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(135, 230, 76, 22);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(223, 230, 76, 22);
		frame.getContentPane().add(btnDelete);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setBounds(311, 230, 85, 22);
		frame.getContentPane().add(btnRefresh);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 54, 412, 155);
		frame.getContentPane().add(scrollPane);
		
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
		lblNewLabel.setBounds(185, 26, 92, 14);
		frame.getContentPane().add(lblNewLabel);
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
