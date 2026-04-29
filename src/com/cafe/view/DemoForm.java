package com.cafe.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Label;
import java.awt.Font;
import javax.swing.JTabbedPane;

public class DemoForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DemoForm frame = new DemoForm();
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
	public DemoForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(60, 11, 309, 101);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblUsername = new JLabel("username");
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblUsername.setBounds(23, 11, 56, 14);
		panel.add(lblUsername);
		
		textField = new JTextField();
		textField.setBounds(110, 8, 116, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblPassword = new JLabel("password");
		lblPassword.setBounds(23, 38, 56, 14);
		panel.add(lblPassword);
		
		textField_1 = new JTextField();
		textField_1.setBounds(110, 39, 116, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.setBounds(65, 68, 98, 22);
		panel.add(btnNewButton);
		
		Label label = new Label("haha");
		label.setBounds(38, 151, 60, 20);
		contentPane.add(label);
		
		Label label_1 = new Label("Hhihi");
		label_1.setBounds(38, 186, 60, 20);
		contentPane.add(label_1);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(158, 172, 56, 14);
		contentPane.add(lblNewLabel);

	}
}
