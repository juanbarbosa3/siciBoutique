import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import java.awt.SystemColor;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class OldCashRegister extends DbConnector{
	
	

	private JFrame frame;
	private JTable table;
	private JTextField txtSearch;

	public JFrame getFrame() {
		return frame;
	}

	public JTable getTable() {
		return table;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OldCashRegister window = new OldCashRegister();
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
	public OldCashRegister() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();	
		super.setUpDB();
		//attempt to resize
//		frame.setSize(1148, 768);
//		frame.getContentPane().setSize(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
//		frame.getContentPane().setPreferredSize(frame.getContentPane().getPreferredSize());
		
		
		//Resize to fullscreen JFrame & contentPane
		//lo mas cercano al goal
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.getContentPane().setSize(JFrame.MAXIMIZED_HORIZ, JFrame.MAXIMIZED_VERT);
	
	
		//Para acomodar
		frame.getContentPane().setLayout(null);
		
//		JScrollPane scrollPane = new JScrollPane();
//		scrollPane.setBounds(10, 11, 673, 737);
//		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"inventory_name", "brand", "color", "inventory_size", "list_price"
			}
		));
		table.setBackground(Color.WHITE);
		table.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		//scrollPane.setViewportView(table);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 11, 673, 737);
		frame.getContentPane().add(scrollPane);
//		table.setEnabled(false);
//		table.setFocusable(true);
		
		String query = "SELECT inventory_name, brand, color, inventory_size, list_price FROM public.inventory_tb";

		try {

			
			//Statement s = this.super.getConnection().createStatement();
			Statement s = getConnection().createStatement();
			ResultSet r = s.executeQuery(query);
			
//			DefaultTableModel dTable = (DefaultTableModel) table.getModel();
			while(r.next()) {
				String inventory_name = r.getString("inventory_name");
				String brand = r.getString("brand");
				String color = r.getString("color");
				String inventory_size = r.getString("inventory_size");
				String list_price = r.getString("list_price");

				DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
				
				//String tableData[] = {inventory_name, brand, color, inventory_size};

				tableModel.addRow(new String[]{inventory_name, brand, color, inventory_size, list_price});
				//tableModel.addRow(tableData);
			}
			
		
		}catch (SQLException e) {
			e.printStackTrace();
		}
	
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBounds(693, 358, 723, 390);
		frame.getContentPane().add(bottomPanel);
		bottomPanel.setLayout(null); //Null layout
		
		JLabel lblSubTotal = new JLabel("Sub-Total:");
		lblSubTotal.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblSubTotal.setBounds(56, 134, 145, 39);
		
		lblSubTotal.setHorizontalAlignment(lblSubTotal.RIGHT);
		
		bottomPanel.add(lblSubTotal);
		
		JLabel lblTax = new JLabel("Tax:");
		lblTax.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTax.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblTax.setBounds(50, 210, 145, 39);
		bottomPanel.add(lblTax);
		
		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblTotal.setBounds(50, 288, 145, 39);
		bottomPanel.add(lblTotal);
		
		JLabel lblSubResult = new JLabel("New label");
		lblSubResult.setHorizontalAlignment(SwingConstants.CENTER);
		lblSubResult.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblSubResult.setBounds(291, 288, 145, 39);
		lblSubResult.setOpaque(true); //let she background show, setOpaque -> setBackground
		lblSubResult.setBackground(SystemColor.activeCaption);
		lblSubResult.setVisible(false);
		bottomPanel.add(lblSubResult);
		
		JLabel lblTaxResult = new JLabel("New label");
		lblTaxResult.setHorizontalAlignment(SwingConstants.CENTER);
		lblTaxResult.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblTaxResult.setBounds(291, 210, 145, 39);
		lblTaxResult.setOpaque(true); //let she background show, setOpaque -> setBackground
		lblTaxResult.setBackground(SystemColor.activeCaption);
		bottomPanel.add(lblTaxResult);
		
		JLabel lblTotalResult = new JLabel("New label");
		lblTotalResult.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalResult.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblTotalResult.setBounds(291, 134, 145, 39);
		lblTotalResult.setOpaque(true); //let she background show, setOpaque -> setBackground
		lblTotalResult.setBackground(SystemColor.activeCaption);
		bottomPanel.add(lblTotalResult);
		
		JPanel topPanel = new JPanel();
		topPanel.setBounds(693, 11, 677, 335);
		frame.getContentPane().add(topPanel);
		topPanel.setLayout(null);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(495, 210, 145, 39);
		bottomPanel.add(btnClear);
	
		
		
//		JButton btnPay = new JButton("Procesar Pago");
//		btnPay.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				
//				
//				
//				
//			}
//		});
//		btnPay.setBounds(495, 288, 145, 39);
//		bottomPanel.add(btnPay);
		
//		BufferedImage myPicture = Images.logOut;
		BufferedImage myPicture = null;
		try {
			//myPicture = ImageIO.read(new File("/SiciCapstone/res/Images/logOut.png"));
			myPicture = ImageIO.read(getClass().getResourceAsStream("/Images/logOut.png"));
			//myPicture = ImageIO.read(new File("C:\\Users\\Josean\\Desktop\\Todo lo de gaby\\todo java\\SiciCapstone\\res\\Images\\logOut.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		JButton btnLogOut = new JButton("");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Login().getFrame().setVisible(true);
				getFrame().dispose();
			}
		});

		btnLogOut.setBounds(607, 11, 60, 58);
		//btnNewButton.setBounds(122, 18041, 42126, 104, 77);
		
		Image i = myPicture.getScaledInstance(btnLogOut.getWidth(), btnLogOut.getHeight(), Image.SCALE_SMOOTH);
		btnLogOut.setIcon(new ImageIcon (i));
		
		topPanel.add(btnLogOut);
		
		txtSearch = new JTextField();
		txtSearch.setBounds(213, 40, 193, 41);
		topPanel.add(txtSearch);
		txtSearch.setColumns(10);
		
//		txtSearch.addKeyListener(new KeyAdapter() {
//	         public void keyPressed(KeyEvent ke) {
//
//	            String value = txtSearch.getText();
//	            int l = value.length();
//	            if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
//	            	txtSearch.setEditable(true);
//	               //label.setText("");
//	            } else {
//	            	txtSearch.setEditable(false);
//	               //label.setText("* Enter only numeric digits(0-9)");
//	            }
//	         }
//	      });
		
		DefaultListModel listModel = new DefaultListModel();
		
		
		JList lstBox = new JList(listModel);
	//	lstBox.setBounds(10, 63, 182, 213);
		
		JScrollPane lstScrollPane = new JScrollPane(lstBox);
		
		
		
		
		lstScrollPane.setBounds(10, 63, 193, 242);
		topPanel.add(lstScrollPane);
		
		
		
		ArrayList<String> woo = new ArrayList<>();
		ArrayList<Integer> av = new ArrayList<>();
		
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sku = txtSearch.getText();
				
				String query = "select stock_tb.stock_id, inventory_tb.sku, inventory_tb.inventory_name, inventory_tb.brand, inventory_tb.color, inventory_tb.inventory_size, inventory_tb.list_price, stock_tb.available\r\n" + 
						"from inventory_tb, stock_tb\r\n" + 
						"where (\r\n" + 
						"	inventory_tb.sku = '" + sku + "'\r\n" + 
						"	   and stock_tb.available > 0\r\n" + 
						"	   and inventory_tb.inventory_id = stock_tb.inventory_id\r\n" + 
						"	  );";

				
				try {

					
					//Statement s = this.super.getConnection().createStatement();
					Statement s = getConnection().createStatement();
					ResultSet r = s.executeQuery(query);
					
					
					//listModel.contains(r.getString("inventory_name"))
				
					if(r.next()) {
						
						String name = r.getString("inventory_name");
						String brand = r.getString("brand");
						String color = r.getString("color");
						String size = r.getString("inventory_size");
						String price = r.getString("list_price");
						int available = r.getInt("available");
						
						ProductInformation pi = new ProductInformation(name, brand, color, size, price, available);
						pi.getFrame().setVisible(true);
						
							
//							Object tableData[] = {r.getString("inventory_name")};
//							listModel.addElement(tableData[0]);
//							woo.add(r.getString("stock_id"));
//							av.add(r.getInt("available"));
//							listModel.addElement(r.getString("inventory_name").toUpperCase());
//							
//						}

					} else {
						JOptionPane.showMessageDialog(frame,
							    "Item not found",
							    "Error 404",
							    JOptionPane.ERROR_MESSAGE);
					}
				
				}catch (SQLException sq) {
					sq.printStackTrace();
				}
			
				
				
			}
		});
		btnSearch.setBounds(213, 92, 89, 41);
		topPanel.add(btnSearch);
		
		
		
		JButton btnPay = new JButton("Procesar Pago");
		btnPay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String query = "";
				
				try {

					
					//Statement s = this.super.getConnection().createStatement();
					Statement s = getConnection().createStatement();
					//ResultSet r = s.executeQuery(query);
					
					for (int j = 0; j < woo.size(); j++) {
						int num = av.get(j) - 1;
						query = "UPDATE stock_tb SET available='" + num + "' WHERE stock_id ='" + woo.get(j) + "';";
						s.executeUpdate(query);
						System.out.println("????");
					}
					
				
				}catch (SQLException er) {
					er.printStackTrace();
				}
				
				
				
			}
		});
		btnPay.setBounds(495, 288, 145, 39);
		bottomPanel.add(btnPay);
			
		//
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

}
