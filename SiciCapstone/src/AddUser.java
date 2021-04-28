import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class AddUser extends DbConnector{

	/*
	 * Window that lets you add more users to the database
	 * JGBT
	 */
	
	private JFrame frame;
	private JTextField txtName;
	private JTextField txtLastName;
	private JTextField txtEmail;
	private JTextField txtPassword;
	private JTextField txtPhone;

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	/**
	 * Create the application.
	 */
	public AddUser() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		super.setUpDB(); //Necessary call
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 402);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Para acomodar
		frame.getContentPane().setLayout(null);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBounds(10, 81, 414, 271);
		frame.getContentPane().add(bottomPanel);
		
		JPanel topPanel = new JPanel();
		topPanel.setBounds(10, 11, 234, 59);
		frame.getContentPane().add(topPanel);
		topPanel.setLayout(new BorderLayout(0, 0)); //to center label
		
		JLabel lblTop = new JLabel("Add User");
		lblTop.setHorizontalAlignment(SwingConstants.LEFT);
		lblTop.setFont(new Font("Eras Medium ITC", Font.BOLD | Font.ITALIC, 40));
		topPanel.add(lblTop, BorderLayout.CENTER);
		
		bottomPanel.setLayout(null);
		
		JLabel lblName = new JLabel("First Name:");
		lblName.setFont(new Font("Eras Medium ITC", Font.BOLD, 20));
		lblName.setBounds(10, 11, 101, 23);
		bottomPanel.add(lblName);
		
		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setFont(new Font("Eras Medium ITC", Font.BOLD, 20));
		lblLastName.setBounds(10, 45, 101, 23);
		bottomPanel.add(lblLastName);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Eras Medium ITC", Font.BOLD, 20));
		lblEmail.setBounds(10, 79, 101, 23);
		bottomPanel.add(lblEmail);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Eras Medium ITC", Font.BOLD, 20));
		lblPassword.setBounds(10, 113, 101, 23);
		bottomPanel.add(lblPassword);
		
		JLabel lblPhone = new JLabel("Phone Number:");
		lblPhone.setFont(new Font("Eras Medium ITC", Font.BOLD, 20));
		lblPhone.setBounds(10, 147, 142, 23);
		bottomPanel.add(lblPhone);
		
		JLabel lblRole = new JLabel("Role:");
		lblRole.setFont(new Font("Eras Medium ITC", Font.BOLD, 20));
		lblRole.setBounds(10, 181, 101, 23);
		bottomPanel.add(lblRole);
		
		txtName = new JTextField();
		txtName.setFont(new Font("Eras Medium ITC", Font.PLAIN, 20));
		txtName.setBounds(184, 11, 220, 23);
		bottomPanel.add(txtName);
		txtName.setColumns(10);
		
		txtLastName = new JTextField();
		txtLastName.setFont(new Font("Eras Medium ITC", Font.PLAIN, 20));
		txtLastName.setColumns(10);
		txtLastName.setBounds(184, 45, 220, 23);
		bottomPanel.add(txtLastName);
		
		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Eras Medium ITC", Font.PLAIN, 20));
		txtEmail.setColumns(10);
		txtEmail.setBounds(184, 79, 220, 23);
		bottomPanel.add(txtEmail);
		
		txtPassword = new JTextField();
		txtPassword.setFont(new Font("Eras Medium ITC", Font.PLAIN, 20));
		txtPassword.setColumns(10);
		txtPassword.setBounds(184, 113, 220, 23);
		bottomPanel.add(txtPassword);
		
		txtPhone = new JTextField();
		txtPhone.setFont(new Font("Eras Medium ITC", Font.PLAIN, 20));
		txtPhone.setColumns(10);
		txtPhone.setBounds(184, 147, 220, 23);
		bottomPanel.add(txtPhone);
		
		JPanel rolePanel = new JPanel();
		rolePanel.setBounds(91, 181, 112, 79);
		bottomPanel.add(rolePanel);
		//rolePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		rolePanel.setLayout(null);
		
		JRadioButton rdbManager = new JRadioButton("Manager");
		rdbManager.setBounds(6, 33, 92, 23);
		rolePanel.add(rdbManager);
		
		JRadioButton rdbSales = new JRadioButton("Cashier");
		rdbSales.setBounds(6, 7, 92, 23);
		rolePanel.add(rdbSales);
		
		JButton btnAdd = new JButton("Add User");
		btnAdd.setBounds(285, 210, 89, 23);
		bottomPanel.add(btnAdd);
		
		ButtonGroup g = new ButtonGroup();
		g.add(rdbManager);
		g.add(rdbSales);
		
		JButton btnBack = new JButton("");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Hub(true).getFrame().setVisible(true); //Already manager if you are at this window
				getFrame().dispose();
				
			}
		});
		btnBack.setBounds(365, 11, 59, 59);
		frame.getContentPane().add(btnBack);
		
		BufferedImage picBack = null;
		try {
			picBack = ImageIO.read(getClass().getResourceAsStream("/Images/backIcon.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image imgBack = picBack.getScaledInstance(btnBack.getWidth(), btnBack.getHeight(), Image.SCALE_SMOOTH);
		btnBack.setIcon(new ImageIcon (imgBack));
		
		////////////////////////////////////////////////////////
		//Makes radio buttons cancel each other
//		rdbSales.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				if(rdbSales.isSelected()) {
//					rdbManager.setSelected(false);
//				}
//			}
//		});
//		
//		rdbManager.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				if(rdbManager.isSelected()) {
//					rdbSales.setSelected(false);
//				}
//			}
//		});
		//////////////////////////////////////////////////////////
		
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String role;
				
				if(rdbManager.isSelected()) {
					role = "Manager";
				} else {
					role = "Sales";
				}
				
				String password = txtPassword.getText(); 
				String name = txtName.getText();
				String lastName = txtLastName.getText();
				String email = txtEmail.getText();
				String phone = txtPhone.getText();
				
				System.out.println("role:" + role);
				System.out.println("pass: " + password);
				System.out.println("nam: " + name);
				System.out.println("last: " + lastName);
				System.out.println("email: " + email);
				System.out.println("phone: " + phone);
				
				String query = "BEGIN;\r\n" + 
						"INSERT INTO staff_tb (store_id, staff_role, staff_password, firstname, lastname, email, phone, row_status)\r\n" + 
						"VALUES (1, '"+role+"', '"+password+"', '"+name+"', '"+lastName+"', '"+email+"', '"+phone+"', 1);\r\n" +  
						"END;"; 
				
				System.out.println(query);
					
				try {

					Statement s = getConnection().createStatement();
					s.executeUpdate(query);
				
				}catch (SQLException er) {
					er.printStackTrace();
				
				}
			}
		});
		
	}
}
