package com.cafe.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.cafe.bll.OrderBLL;
import com.cafe.bll.TableBLL;
import com.cafe.model.CafeTable;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;

public class TableManagerView extends JFrame {
    TableBLL tableBLL = new TableBLL();
    OrderBLL orderBLL = new OrderBLL();

    private JTable tableGrid;
    private DefaultTableModel tableModel;
    private JButton btnOrder;


    public TableManagerView() {
        setTitle("Quản lý Bàn - Coffee Shop");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // 1. Khởi tạo Bảng (Table)
        String[] columns = {"ID Bàn", "Tên Bàn", "Trạng Thái"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép sửa trực tiếp trên bảng
            }
        };
        tableGrid = new JTable(tableModel);
        
        // Đổ dữ liệu từ BLL
        loadTableData();

        JScrollPane scrollPane = new JScrollPane(tableGrid);
        add(scrollPane, BorderLayout.CENTER);

        // 2. Khu vực Nút bấm (Bottom)
        JPanel panelBottom = new JPanel();
        btnOrder = new JButton("Mở Menu Đặt Món (Bàn đang chọn)");
        btnOrder.setFont(new Font("Arial", Font.BOLD, 14));
        
        panelBottom.add(btnOrder);
        add(panelBottom, BorderLayout.SOUTH);

        // 3. Xử lý sự kiện (Wiring)
        btnOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableGrid.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(TableManagerView.this, "Vui lòng chọn một bàn trước!");
                    return;
                }
                
                // Lấy ID bàn từ cột 0 của dòng được chọn
                int tableId = (int) tableGrid.getValueAt(selectedRow, 0);
                String tableName = (String) tableGrid.getValueAt(selectedRow, 1);
                
                // Mở giao diện Đặt món và truyền ID bàn qua
                OrderView orderView = new OrderView(tableId, tableName);
                orderView.setVisible(true);

                // KHI DIALOG ĐÓNG, DÒNG NÀY SẼ CHẠY:
                loadTableData(); // Tự động cập nhật lại trạng thái các bàn (màu sắc/text)
            }
        });
    }

    public void loadTableData() {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ
        List<CafeTable> list = tableBLL.getAllTables();
        
        for (CafeTable t : list) {
            tableModel.addRow(new Object[]{
                t.getTableId(), 
                t.getTableName(), 
                t.isOccupied() ? "Có khách" : "Trống"
            });
        }
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> new TableManagerView().setVisible(true));
    }
}