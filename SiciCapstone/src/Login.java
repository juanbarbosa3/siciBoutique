import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends DbConnector{
	
	/*
	 * Login window form, first window
	 * JGBT
	 */

	private JFrame frame;
	private JPasswordField txtPassword;
	private JTextField txtUser;
	

	public JFrame getFrame() {
		return frame;
	}

	public void setTxtPassword(JPasswordField txtPassword) {
		this.txtPassword = txtPassword;
	}

	public void setTxtUser(JTextField txtUser) {
		this.txtUser = txtUser;
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		
		super.setUpDB(); //Necessary call
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(245, 255, 250));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Null layout
		frame.getContentPane().setLayout(null);
		
		//Top panel w/ stuff
		JPanel topPanel = new JPanel();
		topPanel.setBounds(10, 11, 424, 72);
		frame.getContentPane().add(topPanel);
		topPanel.setLayout(null);
		
		JLabel lblTop = new JLabel("");
		lblTop.setBounds(0, 0, 424, 72);
		lblTop.setHorizontalAlignment(SwingConstants.CENTER);
		topPanel.add(lblTop, BorderLayout.CENTER);
		
		//BufferedImage myPicture = Images.logo;
		BufferedImage picLogo = null;
		try {
			picLogo = ImageIO.read(getClass().getResourceAsStream("/Images/AlhannasLogo.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Image imgLogo = picLogo.getScaledInstance(lblTop.getWidth(),lblTop.getHeight(), Image.SCALE_SMOOTH);
		
		lblTop.setIcon(new ImageIcon (imgLogo));
		topPanel.add(lblTop);
		
		//Bottom panel w/ stuff
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(new Color(255, 153, 204));
		bottomPanel.setBounds(10, 94, 424, 166);
		frame.getContentPane().add(bottomPanel);
		bottomPanel.setLayout(null); //Null layout on bottom
		
		JLabel lblUser = new JLabel("Username:");
		lblUser.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 17));
		lblUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblUser.setBounds(60, 37, 127, 27);
		lblUser.setOpaque(true);
		lblUser.setBackground(new Color(245, 255, 250));
		bottomPanel.add(lblUser);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setOpaque(true);
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 17));
		lblPassword.setBackground(new Color(245, 255, 250));
		lblPassword.setBounds(60, 75, 127, 27);
		bottomPanel.add(lblPassword);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(197, 75, 152, 27);
		bottomPanel.add(txtPassword);
		
		txtUser = new JTextField();
		txtUser.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtUser.setBounds(197, 37, 152, 27);
		bottomPanel.add(txtUser);
		txtUser.setColumns(10);
		
		JButton btnEnter = new JButton("Sign In");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				String u = txtUser.getText();
				String p = txtPassword.getText();

				if(!u.matches("-?\\d+") || p.equals("")) { //regexto check if its integer
					Notification.failedLogIn(getFrame(), txtPassword, txtUser);
				}else {
					
					
					String query = "SELECT * FROM public.staff_tb WHERE staff_id='"+u+"' AND staff_password='"+p+"'";

					
					try {

						
						//Statement s = this.super.getConnection().createStatement();
						Statement s = getConnection().createStatement();
						ResultSet r = s.executeQuery(query);
					
						if(r.next()) {
							new Hub().getFrame().setVisible(true);
							getConnection().close();
							getFrame().dispose();
						} else {
							Notification.failedLogIn(getFrame(), txtPassword, txtUser);
						}
					
					}catch (SQLException er) {
						er.printStackTrace();
					}
				
				
				}
			}
		});
		btnEnter.setBounds(166, 113, 89, 42);
		bottomPanel.add(btnEnter);
		
	}
}
