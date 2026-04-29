package com.cafe.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.cafe.bll.ProductBLL;
import com.cafe.model.Category;
import com.cafe.model.Product;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProductView extends JFrame {
	private ProductBLL productBLL = new ProductBLL();

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtSp;
	private JTable table;
	private DefaultTableModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductView frame = new ProductView();
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
	public ProductView() {
		setTitle("Form Sản Phẩm");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtSp = new JTextField();
		txtSp.setBounds(204, 12, 116, 20);
		contentPane.add(txtSp);
		txtSp.setColumns(10);
		
		JLabel lblName = new JLabel("Find product name");
		lblName.setBounds(84, 15, 116, 14);
		contentPane.add(lblName);
		
		JButton btnShow = new JButton("Show");
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setRowCount(0);
				for (var p : productBLL.searchByName(txtSp.getText())) {
					model.addRow(new Object[] {
							p.getProductId(), p.getProductName(),
							p.getPrice(),
							p.getCategory().getCategoryName()
					});
				}
				table.setModel(model);
			}
		});
		btnShow.setBounds(332, 11, 81, 22);
		contentPane.add(btnShow);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 43, 391, 169);
		contentPane.add(scrollPane);
		
		table = new JTable();
		model = new DefaultTableModel();
		model.addColumn("Product Id");
		model.addColumn("Product Name");
		model.addColumn("Price");
		model.addColumn("Category Name");
		
		table.setModel(model);
		scrollPane.setViewportView(table);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Mở form nhưng truyền null vì là thêm mới
				ProductDialog dialog = new ProductDialog(ProductView.this, "Thêm Sản Phẩm", null);
				dialog.setVisible(true);

				if (dialog.isConfirmed()) {
					boolean check = productBLL.add(dialog.getResultProduct());
					if (check) {
						JOptionPane.showMessageDialog(ProductView.this, "Successful");
						// Reload lại bảng theo text field đang tìm kiếm (như bạn muốn)
//						loadTableData(productBLL.searchByName(txtSp.getText())); 
						refresh();
					} else {
						JOptionPane.showMessageDialog(ProductView.this, "Failed");
					}
				}
			}
		});
		btnAdd.setBounds(42, 223, 93, 22);
		contentPane.add(btnAdd);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(ProductView.this, "Vui lòng chọn dòng cần sửa!");
					return;
				}

				// Lấy ID từ cột đầu tiên của dòng được chọn
				int id = (int) table.getValueAt(selectedRow, 0);
				// Gọi BLL lấy object gốc lên
				Product currentProduct = productBLL.getProductById(id);

				// Mở form và truyền object vào để nó tự fill dữ liệu
				ProductDialog dialog = new ProductDialog(ProductView.this, "Cập nhật Sản Phẩm", currentProduct);
				dialog.setVisible(true);

				if (dialog.isConfirmed()) {
					boolean check = productBLL.update(dialog.getResultProduct());
					if (check) {
						JOptionPane.showMessageDialog(ProductView.this, "Successful");
						refresh();
					} else {
						JOptionPane.showMessageDialog(ProductView.this, "Failed");
					}
				}
			}
		});
		btnUpdate.setBounds(170, 223, 93, 22);
		contentPane.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        int selectedRow = table.getSelectedRow();
		        if (selectedRow == -1) {
		            JOptionPane.showMessageDialog(ProductView.this, "Vui lòng chọn dòng cần xóa!");
		            return;
		        }

		        int id = (int) table.getValueAt(selectedRow, 0);
		        int confirmResult = JOptionPane.showConfirmDialog(ProductView.this, "Bạn có chắc chắn muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
		        
		        if (confirmResult == JOptionPane.YES_OPTION) {
		            boolean isDeleted = productBLL.delete(id);
		            if (isDeleted) {
		                JOptionPane.showMessageDialog(ProductView.this, "Successful");
		                refresh();
		            } else {
		                JOptionPane.showMessageDialog(ProductView.this, "Failed");
		            }
		        }
		    }
		});
		btnDelete.setBounds(296, 223, 93, 22);
		contentPane.add(btnDelete);

	}
	public void refresh() {
		model.setRowCount(0);
		for (var p : productBLL.getAllProducts()) {
			model.addRow(new Object[] {
					p.getProductId(), p.getProductName(),
					p.getPrice(), p.getCategory().getCategoryName()
			});
		}
		table.setModel(model);
	}
}
