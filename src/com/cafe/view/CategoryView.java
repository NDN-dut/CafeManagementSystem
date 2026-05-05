package com.cafe.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import com.cafe.bll.CategoryBLL;
import com.cafe.model.Category;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CategoryView {
	private CategoryBLL categoryBLL = new CategoryBLL();
	
	private JFrame formDM;
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
					CategoryView window = new CategoryView();
					window.formDM.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CategoryView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		formDM = new JFrame();
		formDM.setTitle("Form Danh Mục");
		formDM.setBounds(100, 100, 450, 300);
		formDM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		formDM.getContentPane().setLayout(null);
		
		txtCategory = new JTextField();
		txtCategory.setBounds(192, 24, 135, 20);
		formDM.getContentPane().add(txtCategory);
		txtCategory.setColumns(10);
		
		//Add
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Mở Dialog với trường nhập liệu rỗng
				CategoryDialog dialog = new CategoryDialog(formDM, "Add Category", "");
		        dialog.setVisible(true);
		        
		        //Chờ người dùng bấm OK trên Dialog
		        if (dialog.isConfirmed()) {
		            boolean check = categoryBLL.add(dialog.getCategoryName());
		            if (check) {
		                JOptionPane.showMessageDialog(formDM, "Successful");
		                model.setRowCount(0);
		                refresh();
		            } else {
		                JOptionPane.showMessageDialog(formDM, "Failed");
		            }
		        }
			}
		});
		btnAdd.setBounds(102, 219, 76, 22);
		formDM.getContentPane().add(btnAdd);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Kiểm tra xem người dùng đã chọn dòng nào trên bảng chưa
		        int selectedRow = table.getSelectedRow();
		        if (selectedRow == -1) {
		            JOptionPane.showMessageDialog(formDM, "Please select a row to update!");
		            return;
		        }

		        // Lấy dữ liệu từ dòng được chọn
		        int id = (int) table.getValueAt(selectedRow, 0);
		        String currentName = (String) table.getValueAt(selectedRow, 1);

		        // Mở Dialog và điền sẵn tên hiện tại
		        CategoryDialog dialog = new CategoryDialog(formDM, "Update Category", currentName);
		        dialog.setVisible(true);

		        // Chờ người dùng sửa và bấm OK
		        if (dialog.isConfirmed()) {
		            boolean check = categoryBLL.update(id, dialog.getCategoryName());
		            if (check) {
		                JOptionPane.showMessageDialog(formDM, "Successful");
		                model.setRowCount(0);
		                refresh();
		            } else {
		                JOptionPane.showMessageDialog(formDM, "Failed");
		            }
		        }
			}
		});
		btnUpdate.setBounds(190, 219, 76, 22);
		formDM.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Kiểm tra xem người dùng đã chọn dòng nào trên bảng chưa
		        int selectedRow = table.getSelectedRow();
		        if (selectedRow == -1) {
		            JOptionPane.showMessageDialog(formDM, "Please select a row to update!");
		            return;
		        }
		        
		        // Lấy id từ dòng được chọn
		        int id = (int) table.getValueAt(selectedRow, 0);
		        int confirmResult = JOptionPane.showConfirmDialog(formDM, "Bạn có chắc chắn muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
		        
		        if (confirmResult == JOptionPane.YES_OPTION) {
		            boolean isDeleted = categoryBLL.delete(id);
		            if (isDeleted) {
		                JOptionPane.showMessageDialog(formDM, "Successful");
		                refresh();
		            } else {
		                JOptionPane.showMessageDialog(formDM, "Failed");
		            }
		        }
			}
		});
		btnDelete.setBounds(278, 219, 76, 22);
		formDM.getContentPane().add(btnDelete);
		
		JButton btnShow = new JButton("Show");
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setRowCount(0);
				for (var c : categoryBLL.searchByName(txtCategory.getText())) {
					model.addRow(new Object[] {
							c.getCategoryId(),
							c.getCategoryName()
					});
				}
				table.setModel(model);
			}
		});
		btnShow.setBounds(339, 22, 85, 22);
		formDM.getContentPane().add(btnShow);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 54, 412, 155);
		formDM.getContentPane().add(scrollPane);
		
		table = new JTable();
		
		model = new DefaultTableModel();
		model.addColumn("Category Id");
		model.addColumn("Category Name");

		for (Category c : categoryBLL.getAllCategories()) {
			model.addRow(new Object[] {
				c.getCategoryId(),
				c.getCategoryName()
			});
		}
		scrollPane.setViewportView(table);
		table.setModel(model);
		
		JLabel lblNewLabel = new JLabel("Find category name");
		lblNewLabel.setBounds(59, 27, 121, 14);
		formDM.getContentPane().add(lblNewLabel);
	}
	
	public void refresh() {
		model.setRowCount(0);
		for (Category c : categoryBLL.getAllCategories()) {
			model.addRow(new Object[] {
				c.getCategoryId(),
				c.getCategoryName()
			});
		}
		table.setModel(model);
	}

	public void setVisible(boolean visible) {
		formDM.setVisible(visible);
	}

	public void setDefaultCloseOperation(int operation) {
		formDM.setDefaultCloseOperation(operation);
	}
}
