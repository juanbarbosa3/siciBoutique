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

public class Inventory extends DbConnector{

	/*
	 * Inventory window: add, delete, update items
	 * JGBT
	 */
	
	private JFrame frame;
	private JTable table;
	
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * Create the application.
	 */
	public Inventory() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		super.setUpDB(); //Necessary call
		
		frame = new JFrame();
		frame.setBounds(100, 100, 705, 485);
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
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Name", "Brand", "Category", "Color", "Date", "Size", "Price", "Sku", "Row Status",
			}
		));
		table.setBackground(Color.WHITE);
		table.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		
		table.setDefaultEditor(Object.class, null); //non editable table
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 81, 245, 354);
		frame.getContentPane().add(scrollPane);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(null);
		bottomPanel.setBounds(265, 81, 414, 322);
		frame.getContentPane().add(bottomPanel);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Eras Medium ITC", Font.BOLD, 20));
		lblName.setBounds(10, 11, 101, 23);
		bottomPanel.add(lblName);
		
		JLabel lblBrand = new JLabel("Brand:");
		lblBrand.setFont(new Font("Eras Medium ITC", Font.BOLD, 20));
		lblBrand.setBounds(10, 45, 101, 23);
		bottomPanel.add(lblBrand);
		
		JLabel lblColor = new JLabel("Color:");
		lblColor.setFont(new Font("Eras Medium ITC", Font.BOLD, 20));
		lblColor.setBounds(10, 79, 101, 23);
		bottomPanel.add(lblColor);
		
		JLabel lblSize = new JLabel("Size:");
		lblSize.setFont(new Font("Eras Medium ITC", Font.BOLD, 20));
		lblSize.setBounds(10, 113, 101, 23);
		bottomPanel.add(lblSize);
		
		JLabel lblPrice = new JLabel("Price:");
		lblPrice.setFont(new Font("Eras Medium ITC", Font.BOLD, 20));
		lblPrice.setBounds(10, 147, 101, 23);
		bottomPanel.add(lblPrice);
		
		JLabel lblNameInfo = new JLabel("<dynamic>");
		lblNameInfo.setOpaque(true);
		lblNameInfo.setFont(new Font("Eras Medium ITC", Font.PLAIN, 20));
		lblNameInfo.setBackground(new Color(245, 255, 250));
		lblNameInfo.setBounds(121, 11, 283, 23);
		bottomPanel.add(lblNameInfo);
		
		JLabel lblBrandInfo = new JLabel("<dynamic>");
		lblBrandInfo.setOpaque(true);
		lblBrandInfo.setFont(new Font("Eras Medium ITC", Font.PLAIN, 20));
		lblBrandInfo.setBackground(new Color(245, 255, 250));
		lblBrandInfo.setBounds(121, 45, 283, 23);
		bottomPanel.add(lblBrandInfo);
		
		JLabel lblColorInfo = new JLabel("<dynamic>");
		lblColorInfo.setOpaque(true);
		lblColorInfo.setFont(new Font("Eras Medium ITC", Font.PLAIN, 20));
		lblColorInfo.setBackground(new Color(245, 255, 250));
		lblColorInfo.setBounds(121, 79, 283, 23);
		bottomPanel.add(lblColorInfo);
		
		JLabel lblSizeInfo = new JLabel("<dynamic>");
		lblSizeInfo.setOpaque(true);
		lblSizeInfo.setFont(new Font("Eras Medium ITC", Font.PLAIN, 20));
		lblSizeInfo.setBackground(new Color(245, 255, 250));
		lblSizeInfo.setBounds(121, 113, 283, 23);
		bottomPanel.add(lblSizeInfo);
		
		JLabel lblPriceInfo = new JLabel("<dynamic>");
		lblPriceInfo.setOpaque(true);
		lblPriceInfo.setFont(new Font("Eras Medium ITC", Font.PLAIN, 20));
		lblPriceInfo.setBackground(new Color(245, 255, 250));
		lblPriceInfo.setBounds(121, 147, 283, 23);
		bottomPanel.add(lblPriceInfo);
		
		JLabel lblBrand_1 = new JLabel("Brand:");
		lblBrand_1.setFont(new Font("Eras Medium ITC", Font.BOLD, 20));
		lblBrand_1.setBounds(10, 181, 101, 23);
		bottomPanel.add(lblBrand_1);
		
		JLabel lblBrandInfo_1 = new JLabel("<dynamic>");
		lblBrandInfo_1.setOpaque(true);
		lblBrandInfo_1.setFont(new Font("Eras Medium ITC", Font.PLAIN, 20));
		lblBrandInfo_1.setBackground(new Color(245, 255, 250));
		lblBrandInfo_1.setBounds(121, 181, 283, 23);
		bottomPanel.add(lblBrandInfo_1);
		
		JLabel lblColor_1 = new JLabel("Color:");
		lblColor_1.setFont(new Font("Eras Medium ITC", Font.BOLD, 20));
		lblColor_1.setBounds(10, 215, 101, 23);
		bottomPanel.add(lblColor_1);
		
		JLabel lblColorInfo_1 = new JLabel("<dynamic>");
		lblColorInfo_1.setOpaque(true);
		lblColorInfo_1.setFont(new Font("Eras Medium ITC", Font.PLAIN, 20));
		lblColorInfo_1.setBackground(new Color(245, 255, 250));
		lblColorInfo_1.setBounds(121, 215, 283, 23);
		bottomPanel.add(lblColorInfo_1);
		
		JLabel lblSize_1 = new JLabel("Size:");
		lblSize_1.setFont(new Font("Eras Medium ITC", Font.BOLD, 20));
		lblSize_1.setBounds(10, 249, 101, 23);
		bottomPanel.add(lblSize_1);
		
		JLabel lblSizeInfo_1 = new JLabel("<dynamic>");
		lblSizeInfo_1.setOpaque(true);
		lblSizeInfo_1.setFont(new Font("Eras Medium ITC", Font.PLAIN, 20));
		lblSizeInfo_1.setBackground(new Color(245, 255, 250));
		lblSizeInfo_1.setBounds(121, 249, 283, 23);
		bottomPanel.add(lblSizeInfo_1);
		
		JLabel lblPrice_1 = new JLabel("Price:");
		lblPrice_1.setFont(new Font("Eras Medium ITC", Font.BOLD, 20));
		lblPrice_1.setBounds(10, 283, 101, 23);
		bottomPanel.add(lblPrice_1);
		
		JLabel lblPriceInfo_1 = new JLabel("<dynamic>");
		lblPriceInfo_1.setOpaque(true);
		lblPriceInfo_1.setFont(new Font("Eras Medium ITC", Font.PLAIN, 20));
		lblPriceInfo_1.setBackground(new Color(245, 255, 250));
		lblPriceInfo_1.setBounds(121, 283, 283, 23);
		bottomPanel.add(lblPriceInfo_1);
		
		JButton btnBack = new JButton("");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Hub().getFrame().setVisible(true);
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
		
		String query = "SELECT * FROM public.inventory_tb";
		
		try {
			Statement s = getConnection().createStatement();
			ResultSet r = s.executeQuery(query);
			
//			DefaultTableModel dTable = (DefaultTableModel) table.getModel();
			while(r.next()) {
				String name = r.getString("inventory_name");
				String brand = r.getString("brand");
				String category = r.getString("category");
				String color = r.getString("color");
				String date = r.getString("date_added");
				String size = r.getString("inventory_size");
				String price = r.getString("list_price");
				String sku = r.getString("sku");
				String rowStatus = r.getString("row_status");
				

				DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
				
				//String tableData[] = {inventory_name, brand, color, inventory_size};

				tableModel.addRow(new String[]{name, brand, category, color, date, size, price, sku, rowStatus});
				//tableModel.addRow(tableData);
			}
			
		
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
