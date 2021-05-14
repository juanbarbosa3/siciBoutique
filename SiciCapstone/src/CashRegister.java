import java.awt.Color;
import javax.swing.JFrame;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class CashRegister extends DbConnector {

	/*
	 * Window that searches for items to pay or leave at lay away
	 * JGBT
	 */
	
	private JFrame frame;
	private JTable table;
	private JTextField txtInput;
	
	private static double tax = 0.115;
	
	public static double getTax() {
		return tax;
	}

	private boolean ready;
	
	public JTable getTable() {
		return table;
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public boolean readyToAdd(String s, JTable table) {
		for (int i = 0; i < table.getRowCount(); i++) {
			if(table.getValueAt(i, 0).equals(s)) {
				return false;
			}
		}
		return true;
	}
	

	/**
	 * Create the application.
	 */
	public CashRegister(boolean access, int staffID) {
		initialize(access, staffID);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(boolean access, int staffID) {
		
		super.setUpDB(); //Necessary call
		
		System.out.println(LocalDate.now());
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setSize(598, 549);

		frame.getContentPane().setLayout(null);
		//frame.getContentPane().getHeight()
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Sku", "Name", "Quantity", "Price"
			}
		));
		table.setBackground(Color.WHITE);
		table.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		
		table.setDefaultEditor(Object.class, null); //non editable table
		
		JScrollPane scrollPane = new JScrollPane(table);
		
		//Adjusts itself based on frame
		scrollPane.setBounds(10, 70, frame.getWidth()/2, frame.getHeight()-120);

		frame.getContentPane().add(scrollPane);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 478, 48);
		frame.getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblCashRegister = new JLabel("Cash Register");
		lblCashRegister.setFont(new Font("Eras Medium ITC", Font.BOLD | Font.ITALIC, 40));
		panel.add(lblCashRegister, BorderLayout.CENTER);
		
		JPanel inputPanel = new JPanel();
		inputPanel.setBounds(319, 70, 253, 429);
		frame.getContentPane().add(inputPanel);
		
		inputPanel.setLayout(null);
		
		txtInput = new JTextField();
		txtInput.setFont(new Font("Eras Medium ITC", Font.PLAIN, 20));
		txtInput.setColumns(10);
		txtInput.setBounds(0, 25, 183, 23);
		inputPanel.add(txtInput);
		
		JLabel lblSearch = new JLabel("Search Item:");
		lblSearch.setBounds(0, 0, 133, 23);
		inputPanel.add(lblSearch);
		lblSearch.setFont(new Font("Eras Medium ITC", Font.BOLD, 20));
		
		JButton btnSearch = new JButton();
		
		ready = true;
		
		JLabel lblSubResult = new JLabel();
		JLabel lblTaxResult = new JLabel();
		JLabel lblTotalResult = new JLabel();


		
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sku = txtInput.getText();
				
				String query = "select inventory_name, brand, color, inventory_size, list_price, available_amount\r\n" + 
						"from public.inventory_tb\r\n" + 
						"where (\r\n" + 
						"	sku = '" + sku + "'\r\n" + 
						"	   and available_amount > 0\r\n" + 
						"	  );";
				
				try {
					Statement s = getConnection().createStatement();
					ResultSet r = s.executeQuery(query);
				
					if(r.next()) {
						
						if(readyToAdd(sku, table)) {
						
						String name = r.getString("inventory_name");
						String brand = r.getString("brand");
						String color = r.getString("color");
						String size = r.getString("inventory_size");
						String price = r.getString("list_price");
						int available = r.getInt("available_amount");
						
						ProductInformation pi = new ProductInformation(table, lblSubResult, lblTaxResult, lblTotalResult, sku, name, brand, color, size, price, available);
						pi.getFrame().setVisible(true);
						
						} else {
								Notification.alreadyOnCart(frame);
						}

					} else {
						Notification.itemNotFound(getFrame());
					}
				
				}catch (SQLException sq) {
					sq.printStackTrace();
				}
			
				
			}
		});
		btnSearch.setBounds(193, 11, 50, 37);
		inputPanel.add(btnSearch);
		
		BufferedImage picSearch = null;
		try {
			picSearch = ImageIO.read(getClass().getResourceAsStream("/Images/searchIcon.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image imgSearch = picSearch.getScaledInstance(btnSearch.getWidth(), btnSearch.getHeight(), Image.SCALE_SMOOTH);
		btnSearch.setIcon(new ImageIcon (imgSearch));
		
		JButton btnEliminate = new JButton("Eliminar artículo"); //Button to eliminate item from cart
		btnEliminate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() != -1) {
					DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
					
					double sub;
					double subOfSelectedRow;		
					
					
					subOfSelectedRow = Double.parseDouble((String.valueOf(tableModel.getValueAt(table.getSelectedRow(), 2)))) * Double.parseDouble(String.valueOf(tableModel.getValueAt(table.getSelectedRow(), 3)).substring(1));
					subOfSelectedRow = (double) Math.round(subOfSelectedRow * 100) / 100;
					
					sub = Double.parseDouble(lblSubResult.getText());
					sub = (double) Math.round(sub * 100) / 100;
					//tax = Double.parseDouble(lblTaxResult.getText());
					
					tableModel.removeRow(table.getSelectedRow());
					
					lblSubResult.setText(String.valueOf(sub - subOfSelectedRow));
					lblTaxResult.setText(String.valueOf((double) Math.round((sub - subOfSelectedRow) * CashRegister.getTax()* 100) / 100));
					lblTotalResult.setText(String.valueOf(sub - subOfSelectedRow + (sub - subOfSelectedRow) * CashRegister.getTax()));
				}
				
			}
		});
		btnEliminate.setBounds(60, 59, 133, 64);
		inputPanel.add(btnEliminate);
		
		JButton btnQuantity = new JButton("Cambiar cantidad");
		btnQuantity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() != -1) {
					
					DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
					
					Icon icon = null;
					String quantity = (String)JOptionPane.showInputDialog(
		                    frame,
		                    "Enter desired quantity:",
		                    "Edit Quantity",
		                    JOptionPane.PLAIN_MESSAGE,
		                    icon,
		                    null,
		                    "");
					
					if(quantity != null && quantity.matches("-?\\d+") && !quantity.equals("0")) {
					
						String query = "select available_amount\r\n" + 
								"from public.inventory_tb\r\n" + 
								"where (\r\n" + 
								"	sku = '" + tableModel.getValueAt(table.getSelectedRow(), 0) + "'\r\n" + 
								"	  );";
						
						try {
							Statement s = getConnection().createStatement();
							ResultSet r = s.executeQuery(query);
							r.next();
							
							int available = r.getInt("available_amount");
							
							if(Integer.parseInt(quantity) <= available) {
								

									double sub;
									double subOfSelectedRow;				
									
									subOfSelectedRow = Double.parseDouble((String.valueOf(tableModel.getValueAt(table.getSelectedRow(), 2)))) * Double.parseDouble(String.valueOf(tableModel.getValueAt(table.getSelectedRow(), 3)).substring(1));
									subOfSelectedRow = (double) Math.round(subOfSelectedRow * 100) / 100;
									
									sub = Double.parseDouble(lblSubResult.getText());
									sub = (double) Math.round(sub * 100) / 100;
									//tax = Double.parseDouble(lblTaxResult.getText());
									
									tableModel.insertRow(table.getSelectedRow(), new Object[]{tableModel.getValueAt(table.getSelectedRow(), 0), tableModel.getValueAt(table.getSelectedRow(), 1), quantity, tableModel.getValueAt(table.getSelectedRow(), 3)});
									tableModel.removeRow(table.getSelectedRow()+1);
									
									lblSubResult.setText(String.valueOf(sub - subOfSelectedRow + (Double.parseDouble(quantity) * Double.parseDouble(String.valueOf(tableModel.getValueAt(table.getSelectedRow(), 3)).substring(1)))));
									lblTaxResult.setText(String.valueOf((double) Math.round((sub - subOfSelectedRow + (Double.parseDouble(quantity) * Double.parseDouble(String.valueOf(tableModel.getValueAt(table.getSelectedRow(), 3)).substring(1)))) * CashRegister.getTax()* 100) / 100));
									lblTotalResult.setText(String.valueOf(sub - subOfSelectedRow + (Double.parseDouble(quantity) * Double.parseDouble(String.valueOf(tableModel.getValueAt(table.getSelectedRow(), 3)).substring(1))) + (sub - subOfSelectedRow + (Double.parseDouble(quantity) * Double.parseDouble(String.valueOf(tableModel.getValueAt(table.getSelectedRow(), 3)).substring(1)))) * CashRegister.getTax()));
									
							} else {
								Notification.failedQuantity(getFrame());
							}
							
						}catch (SQLException sq) {
							sq.printStackTrace();
						}
					} else {
						Notification.notNumber(getFrame());
					}
				}
			}
		});
		btnQuantity.setBounds(60, 141, 133, 64);
		inputPanel.add(btnQuantity);
		
		JButton btnPay = new JButton("Pay");
		btnPay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
				
				if(table.getRowCount() != 0) { //If table is not empty
					
					//Searches for the most recently created cart
					String query4CartID = "select cart_id\r\n" + 
							"from public.cart_tb\r\n" + 
							"order by cart_id desc\r\n" + 
							"limit 1\r\n" + 
							";";
					
					
					//Uses sku to search for first item's inventory ID
					String queryInventoryID = "select inventory_id\r\n" + 
							"from public.inventory_tb\r\n" + 
							"where (\r\n" + 
							"	sku = '" + tableModel.getValueAt(0, 0) + "'\r\n" + 
							"	  );";
					
						
					try { //Creates new cart with first item

						Statement s = getConnection().createStatement();
						ResultSet r = s.executeQuery(queryInventoryID);
						r.next();
						
						int inventoryID = r.getInt("inventory_id");
						int amount = Integer.parseInt((String.valueOf(tableModel.getValueAt(0, 2))));
						
						r = s.executeQuery(query4CartID);
						
						int cartID;
						if(r.next()) { //Searches for the most recently created cart
							cartID = r.getInt("cart_id") + 1; //If it exists, adds 1
						} else {
							cartID = 1; //Else, first cart created
						}
						///////////////////////////////////////////////////////////////////////////
						String availableInvID = "select available_amount\r\n" + 
								"from public.inventory_tb\r\n" + 
								"where (\r\n" + 
								"	inventory_id = '" + inventoryID + "'\r\n" + 
								"	  );";
						
						r = s.executeQuery(availableInvID);
						r.next();
						
						int availableAmount = r.getInt("available_amount");
						
						int resultOfSale = availableAmount - amount;
						
						String queryMinusAmount = "UPDATE public.inventory_tb \r\n"
								+ "SET available_amount='"+resultOfSale+"'\r\n" 
										+"WHERE inventory_id = '"+inventoryID+"';";
						
						s.executeUpdate(queryMinusAmount);
						//////////////////////////////////////////////////////////////////////////
						String queryFillCart = "BEGIN;\r\n" + 
								"INSERT INTO cart_tb (cart_id, inventory_id, desired_amount, row_status)\r\n" + 
								"VALUES ('"+cartID+"', '"+inventoryID+"', '"+amount+"', 1);\r\n" +  
								"END;"; 
						
						s.executeUpdate(queryFillCart);
						
						if(table.getRowCount() > 1) { //If table consist of multiple items, adds it to same cart 
							
							//Searches for the most recently created cart, (cart created above)
							
							for (int i = 1; i < table.getRowCount(); i++) { //Adds the rest of the items from the cart

								queryInventoryID = "select inventory_id\r\n" + 
									"from public.inventory_tb\r\n" + 
									"where (\r\n" + 
									"	sku = '" + tableModel.getValueAt(i, 0) + "'\r\n" + 
									"	  );";
								
								r = s.executeQuery(queryInventoryID);
								r.next();
								
								inventoryID = r.getInt("inventory_id");
								amount = Integer.parseInt((String.valueOf(tableModel.getValueAt(i, 2))));
								
								////////////////////////////////////////////////////////////////////////////
								availableInvID = "select available_amount\r\n" + 
										"from public.inventory_tb\r\n" + 
										"where (\r\n" + 
										"	inventory_id = '" + inventoryID + "'\r\n" + 
										"	  );";
								r = s.executeQuery(availableInvID);
								r.next();
								
								availableAmount = r.getInt("available_amount");
								
								resultOfSale = availableAmount - amount;
								
								queryMinusAmount =  "UPDATE public.inventory_tb \r\n"
								+ "SET available_amount='"+resultOfSale+"'\r\n" 
										+"WHERE inventory_id = '"+inventoryID+"';";
						
								s.executeUpdate(queryMinusAmount);
								///////////////////////////////////////////////////////////////////////////
								
								String query2KeepFillingCart = "BEGIN;\r\n" + 
										"INSERT INTO cart_tb (cart_id, inventory_id, desired_amount, row_status)\r\n" + 
										"VALUES ('"+cartID+"', '"+inventoryID+"', '"+amount+"', 1);\r\n" +  
										"END;"; 
								
								s.executeUpdate(query2KeepFillingCart);
								
							}	
						}
						
						double total = Double.parseDouble(lblTotalResult.getText());
						total = (double) Math.round(total * 100) / 100;
						
						
						String cart2Sale = "BEGIN;\r\n" + 
								"INSERT INTO sale_tb (cart_id, staff_id, sale_type, sale_date, total_to_pay, total_paid, discount, row_status)\r\n" + 
								"VALUES ('"+cartID+"', '"+staffID+"', 'Normal', '"+LocalDate.now()+"', '"+total+"', '"+total+"', 0, 1);\r\n" +  
								"END;"; 
						
						s.executeUpdate(cart2Sale);
						
						Notification.succesfulUpdate(getFrame());
						tableModel.setRowCount(0);
					
					}catch (SQLException er) {
						er.printStackTrace();
					
					}
								
				}
	
			}
		});
		btnPay.setBounds(60, 216, 133, 64);
		inputPanel.add(btnPay);
		
		JLabel lblSub = new JLabel("Sub-Total:");
		lblSub.setFont(new Font("Eras Medium ITC", Font.BOLD, 20));
		lblSub.setBounds(10, 291, 101, 23);
		inputPanel.add(lblSub);
		
//		JLabel lblSubResult = new JLabel();
		lblSubResult.setOpaque(true);
		lblSubResult.setFont(new Font("Eras Medium ITC", Font.PLAIN, 20));
		lblSubResult.setBackground(new Color(245, 255, 250));
		lblSubResult.setBounds(121, 291, 132, 23);
		inputPanel.add(lblSubResult);
		
		JLabel lblTax = new JLabel("Tax:");
		lblTax.setFont(new Font("Eras Medium ITC", Font.BOLD, 20));
		lblTax.setBounds(10, 325, 101, 23);
		inputPanel.add(lblTax);
		
//		JLabel lblTaxResult = new JLabel();
		lblTaxResult.setOpaque(true);
		lblTaxResult.setFont(new Font("Eras Medium ITC", Font.PLAIN, 20));
		lblTaxResult.setBackground(new Color(245, 255, 250));
		lblTaxResult.setBounds(121, 325, 132, 23);
		inputPanel.add(lblTaxResult);
		
		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setFont(new Font("Eras Medium ITC", Font.BOLD, 20));
		lblTotal.setBounds(10, 359, 101, 23);
		inputPanel.add(lblTotal);
		
//		JLabel lblTotalResult = new JLabel();
		lblTotalResult.setOpaque(true);
		lblTotalResult.setFont(new Font("Eras Medium ITC", Font.PLAIN, 20));
		lblTotalResult.setBackground(new Color(245, 255, 250));
		lblTotalResult.setBounds(121, 359, 132, 23);
		inputPanel.add(lblTotalResult);
		
		
		
		
		JButton btnBack = new JButton("");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Hub(access, staffID).getFrame().setVisible(true);
				getFrame().dispose();
				
			}
		});
		btnBack.setBounds(513, 11, 59, 59);
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
		
		
		
		
		
		
	}
}
