import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Inventory extends DbConnector{

	/*
	 * Inventory window: add, delete, update items
	 * JGBT
	 */
	
	private JFrame frame;
	private JTable table;
	private JTextField txtNameInfo;
	private JTextField txtBrandInfo;
	private JTextField txtCategoryInfo;
	private JTextField txtColorInfo;
	private JTextField txtSizeInfo;
	private JTextField txtPriceInfo;
	private JTextField txtSkuInfo;
	private JTextField txtAvailableInfo;
	private JTextField txtRowStatusInfo;
	private JTextField txtIdInfo;
	
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * Create the application.
	 */
	public Inventory(int staffID) {
		initialize(staffID);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(int staffID) {
		
		super.setUpDB(); //Necessary call
		
		frame = new JFrame();
		frame.setBounds(100, 100, 1009, 502);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Para acomodar
		frame.getContentPane().setLayout(null);
		
		JPanel topPanel = new JPanel();
		topPanel.setBounds(10, 11, 414, 59);
		frame.getContentPane().add(topPanel);
		topPanel.setLayout(new BorderLayout(0, 0)); //to center label
		
		JLabel lblTop = new JLabel("Inventory");
		lblTop.setHorizontalAlignment(SwingConstants.LEFT);
		lblTop.setFont(new Font("Eras Medium ITC", Font.BOLD | Font.ITALIC, 40));
		topPanel.add(lblTop, BorderLayout.CENTER);
		
		
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			
			
				public void mouseClicked(java.awt.event.MouseEvent e) {
						DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
						int SelectedRows = table.getSelectedRow();
						
						txtNameInfo.setText(tableModel.getValueAt(SelectedRows, 0).toString());
						txtBrandInfo.setText(tableModel.getValueAt(SelectedRows, 1).toString());
						txtCategoryInfo.setText(tableModel.getValueAt(SelectedRows, 2).toString());
						txtColorInfo.setText(tableModel.getValueAt(SelectedRows, 3).toString());
						txtSizeInfo.setText(tableModel.getValueAt(SelectedRows, 4).toString());
						txtPriceInfo.setText(tableModel.getValueAt(SelectedRows, 5).toString());
						txtSkuInfo.setText(tableModel.getValueAt(SelectedRows, 6).toString());
						txtAvailableInfo.setText(tableModel.getValueAt(SelectedRows, 7).toString());
						txtIdInfo.setText(tableModel.getValueAt(SelectedRows, 8).toString());	
					}
				
				});
		
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Name", "Brand", "Category", "Color", "Size", "Price", "Sku", "Available","ID",
			}
		));
		table.setBackground(Color.WHITE);
		table.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		
		table.setDefaultEditor(Object.class, null); //non editable table
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 81, 681, 354);
		frame.getContentPane().add(scrollPane);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(null);
		bottomPanel.setBounds(701, 81, 282, 322);
		frame.getContentPane().add(bottomPanel);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Eras Medium ITC", Font.BOLD, 20));
		lblName.setBounds(10, 11, 101, 23);
		bottomPanel.add(lblName);
		
		JLabel lblBrand = new JLabel("Brand:");
		lblBrand.setFont(new Font("Eras Medium ITC", Font.BOLD, 20));
		lblBrand.setBounds(10, 45, 101, 23);
		bottomPanel.add(lblBrand);
		
		JLabel lblCategory = new JLabel("Category:");
		lblCategory.setFont(new Font("Eras Medium ITC", Font.BOLD, 20));
		lblCategory.setBounds(10, 79, 101, 23);
		bottomPanel.add(lblCategory);
		
		JLabel lblColor = new JLabel("Color:");
		lblColor.setFont(new Font("Eras Medium ITC", Font.BOLD, 20));
		lblColor.setBounds(10, 113, 101, 23);
		bottomPanel.add(lblColor);
		
		JLabel lblSize = new JLabel("Size:");
		lblSize.setFont(new Font("Eras Medium ITC", Font.BOLD, 20));
		lblSize.setBounds(10, 147, 101, 23);
		bottomPanel.add(lblSize);
		
		JLabel lblPrice = new JLabel("Price:");
		lblPrice.setFont(new Font("Eras Medium ITC", Font.BOLD, 20));
		lblPrice.setBounds(10, 181, 101, 23);
		bottomPanel.add(lblPrice);
		
		JLabel lblSku = new JLabel("Sku:");
		lblSku.setFont(new Font("Eras Medium ITC", Font.BOLD, 20));
		lblSku.setBounds(10, 215, 101, 23);
		bottomPanel.add(lblSku);
		
		JLabel lblAvailable = new JLabel("Available Amount:");
		lblAvailable.setFont(new Font("Eras Medium ITC", Font.BOLD, 20));
		lblAvailable.setBounds(10, 249, 101, 23);
		bottomPanel.add(lblAvailable);
		
		JLabel lblRow = new JLabel("Row Status:");
		lblRow.setFont(new Font("Eras Medium ITC", Font.BOLD, 20));
		lblRow.setBounds(10, 283, 101, 23);
		bottomPanel.add(lblRow);
		
		txtNameInfo = new JTextField();
		txtNameInfo.setBounds(121, 16, 151, 19);
		bottomPanel.add(txtNameInfo);
		txtNameInfo.setColumns(10);
		
		txtBrandInfo = new JTextField();
		txtBrandInfo.setColumns(10);
		txtBrandInfo.setBounds(121, 50, 151, 19);
		bottomPanel.add(txtBrandInfo);
		
		txtCategoryInfo = new JTextField();
		txtCategoryInfo.setColumns(10);
		txtCategoryInfo.setBounds(121, 84, 151, 19);
		bottomPanel.add(txtCategoryInfo);
		
		txtColorInfo = new JTextField();
		txtColorInfo.setColumns(10);
		txtColorInfo.setBounds(121, 118, 151, 19);
		bottomPanel.add(txtColorInfo);
		
		txtSizeInfo = new JTextField();
		txtSizeInfo.setColumns(10);
		txtSizeInfo.setBounds(121, 152, 151, 19);
		bottomPanel.add(txtSizeInfo);
		
		txtPriceInfo = new JTextField();
		txtPriceInfo.setColumns(10);
		txtPriceInfo.setBounds(121, 186, 151, 19);
		bottomPanel.add(txtPriceInfo);
		
		txtSkuInfo = new JTextField();
		txtSkuInfo.setColumns(10);
		txtSkuInfo.setBounds(121, 220, 151, 19);
		bottomPanel.add(txtSkuInfo);
		
		txtAvailableInfo = new JTextField();
		txtAvailableInfo.setColumns(10);
		txtAvailableInfo.setBounds(121, 254, 151, 19);
		bottomPanel.add(txtAvailableInfo);
		
		txtRowStatusInfo = new JTextField();
		txtRowStatusInfo.setColumns(10);
		txtRowStatusInfo.setBounds(121, 288, 151, 19);
		bottomPanel.add(txtRowStatusInfo);
		
		JButton btnBack = new JButton("");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Hub(true, staffID).getFrame().setVisible(true); //Already manager if you are at this window
				getFrame().dispose();
				
			}
		});
		btnBack.setBounds(620, 11, 59, 59);
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
		
		///..........................................................................................................
		JButton btnAdd = new JButton("Add to Inventory");
		btnAdd.setBounds(701, 414, 137, 21);
		frame.getContentPane().add(btnAdd);
		
	
		
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				String name = txtNameInfo.getText(); 
				String brand = txtBrandInfo.getText();
				String category = txtCategoryInfo.getText();
				String color = txtColorInfo.getText();
				String size = txtSizeInfo.getText();
				String price = txtPriceInfo.getText();
				String sku = txtSkuInfo.getText();
				String available = txtAvailableInfo.getText();
				String row = txtRowStatusInfo.getText();
				
				String query = "BEGIN;\r\n" + 
						"INSERT INTO inventory_tb (inventory_name, brand, inventory_category, color, inventory_size, list_price, sku, available_amount, row_status)\r\n" + 
						"VALUES ('"+name+"', '"+brand+"', '"+category+ "' , '" +color+"', '"+size+"','"+price+"','"+sku+"','"+available+"','"+row+"');\r\n" +  
						"END;"; 
					
				try {

					Statement s = getConnection().createStatement();
					s.executeUpdate(query);
					Notification.succesfulUpdate(frame);
				
				}catch (SQLException er) {
					er.printStackTrace();
				}
			}
		});
		
		///.....................................................................................................

		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setBounds(473, 35, 85, 21);
		frame.getContentPane().add(btnRefresh);
		
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				txtNameInfo.setText("");
				txtBrandInfo.setText("");
				txtCategoryInfo.setText("");
				txtColorInfo.setText("");
				txtSizeInfo.setText("");
				txtPriceInfo.setText("");
				txtSkuInfo.setText("");
				txtAvailableInfo.setText("");
				txtIdInfo.setText("");
				txtRowStatusInfo.setText("");
				
				String query = "SELECT * FROM public.inventory_tb";
				
				DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
				tableModel.setRowCount(0);
				
				try {
					Statement s = getConnection().createStatement();
					ResultSet r = s.executeQuery(query);
					
					while(r.next()) {
						String name = r.getString("inventory_name");
						String brand = r.getString("brand");
						String category = r.getString("inventory_category");
						String color = r.getString("color");
						String size = r.getString("inventory_size");
						String price = r.getString("list_price");
						String sku = r.getString("sku");
						String available = r.getString("available_amount");	
						String id = r.getString("inventory_id");

					
						//DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
						tableModel.addRow(new String[]{name, brand, category, color, size, price, sku, available, id});
					}
					
				
				}catch (SQLException ex) {
					ex.printStackTrace();
				}	
			}
		});
		
		JButton btnEdit = new JButton("Edit Selected");
		btnEdit.setBounds(846, 413, 137, 21);
		frame.getContentPane().add(btnEdit);
		
		txtIdInfo = new JTextField();
		txtIdInfo.setEditable(false);
		txtIdInfo.setBounds(846, 51, 96, 19);
		frame.getContentPane().add(txtIdInfo);
		txtIdInfo.setColumns(10);
		
		JLabel lblId = new JLabel("Id:");
		lblId.setFont(new Font("Eras Medium ITC", Font.BOLD, 20));
		lblId.setBounds(794, 47, 44, 23);
		frame.getContentPane().add(lblId);
		
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String id = txtIdInfo.getText();
				String name = txtNameInfo.getText(); 
				String brand = txtBrandInfo.getText();
				String category = txtCategoryInfo.getText();
				String color = txtColorInfo.getText();
				String size = txtSizeInfo.getText();
				String price = txtPriceInfo.getText();
				String sku = txtSkuInfo.getText();
				String available = txtAvailableInfo.getText();
				//String row = txtRowStatusInfo.getText();
				
				String query = "UPDATE public.inventory_tb \r\n"
						+ "SET inventory_name= '"+name+"', brand= '"+brand+"', inventory_category='"+category+ "', color='" +color+"', inventory_size='"+size+"', list_price='"+price+"', sku='"+sku+"', available_amount='"+available+"'\r\n" 
								+"WHERE inventory_id = '"+id+"';";
						
				try {

					Statement s = getConnection().createStatement();
					s.executeUpdate(query);
					Notification.succesfulUpdate(frame);
				
				}catch (SQLException er) {
					er.printStackTrace();
				}
			}
		});
		///.....................................................................................................
		String query = "SELECT * FROM public.inventory_tb";
		
		try {
			Statement s = getConnection().createStatement();
			ResultSet r = s.executeQuery(query);
			
			while(r.next()) {
				
				
				String name = r.getString("inventory_name");
				String brand = r.getString("brand");
				String category = r.getString("inventory_category");
				String color = r.getString("color");
				String size = r.getString("inventory_size");
				String price = r.getString("list_price");
				String sku = r.getString("sku");
				String available = r.getString("available_amount");			
				String id = r.getString("inventory_id");
			
				DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
				tableModel.addRow(new String[]{name, brand, category, color, size, price, sku, available,id});
			}
			
		
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}