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
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Name", "Brand", "Category", "Color", "Size", "Price", "Sku", "Available",
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
		
		JLabel lblNameInfo = new JLabel("<dynamic>");
		lblNameInfo.setOpaque(true);
		lblNameInfo.setFont(new Font("Eras Medium ITC", Font.PLAIN, 20));
		lblNameInfo.setBackground(new Color(245, 255, 250));
		lblNameInfo.setBounds(121, 11, 142, 23);
		bottomPanel.add(lblNameInfo);
		
		JLabel lblBrandInfo = new JLabel("<dynamic>");
		lblBrandInfo.setOpaque(true);
		lblBrandInfo.setFont(new Font("Eras Medium ITC", Font.PLAIN, 20));
		lblBrandInfo.setBackground(new Color(245, 255, 250));
		lblBrandInfo.setBounds(121, 45, 142, 23);
		bottomPanel.add(lblBrandInfo);
		
		JLabel lblCategoryInfo = new JLabel("<dynamic>");
		lblCategoryInfo.setOpaque(true);
		lblCategoryInfo.setFont(new Font("Eras Medium ITC", Font.PLAIN, 20));
		lblCategoryInfo.setBackground(new Color(245, 255, 250));
		lblCategoryInfo.setBounds(121, 79, 142, 23);
		bottomPanel.add(lblCategoryInfo);
		
		JLabel lblColorInfo = new JLabel("<dynamic>");
		lblColorInfo.setOpaque(true);
		lblColorInfo.setFont(new Font("Eras Medium ITC", Font.PLAIN, 20));
		lblColorInfo.setBackground(new Color(245, 255, 250));
		lblColorInfo.setBounds(121, 113, 142, 23);
		bottomPanel.add(lblColorInfo);
		
		JLabel lblSizeInfo = new JLabel("<dynamic>");
		lblSizeInfo.setOpaque(true);
		lblSizeInfo.setFont(new Font("Eras Medium ITC", Font.PLAIN, 20));
		lblSizeInfo.setBackground(new Color(245, 255, 250));
		lblSizeInfo.setBounds(121, 147, 142, 23);
		bottomPanel.add(lblSizeInfo);
		
		JLabel lblPrice = new JLabel("Price:");
		lblPrice.setFont(new Font("Eras Medium ITC", Font.BOLD, 20));
		lblPrice.setBounds(10, 181, 101, 23);
		bottomPanel.add(lblPrice);
		
		JLabel lblPriceInfo = new JLabel("<dynamic>");
		lblPriceInfo.setOpaque(true);
		lblPriceInfo.setFont(new Font("Eras Medium ITC", Font.PLAIN, 20));
		lblPriceInfo.setBackground(new Color(245, 255, 250));
		lblPriceInfo.setBounds(121, 181, 142, 23);
		bottomPanel.add(lblPriceInfo);
		
		JLabel lblSku = new JLabel("Sku:");
		lblSku.setFont(new Font("Eras Medium ITC", Font.BOLD, 20));
		lblSku.setBounds(10, 215, 101, 23);
		bottomPanel.add(lblSku);
		
		JLabel lblSkuInfo = new JLabel("<dynamic>");
		lblSkuInfo.setOpaque(true);
		lblSkuInfo.setFont(new Font("Eras Medium ITC", Font.PLAIN, 20));
		lblSkuInfo.setBackground(new Color(245, 255, 250));
		lblSkuInfo.setBounds(121, 215, 142, 23);
		bottomPanel.add(lblSkuInfo);
		
		JLabel lblAvailable = new JLabel("Available Amount:");
		lblAvailable.setFont(new Font("Eras Medium ITC", Font.BOLD, 20));
		lblAvailable.setBounds(10, 249, 101, 23);
		bottomPanel.add(lblAvailable);
		
		JLabel lblAvailableInfo = new JLabel("<dynamic>");
		lblAvailableInfo.setOpaque(true);
		lblAvailableInfo.setFont(new Font("Eras Medium ITC", Font.PLAIN, 20));
		lblAvailableInfo.setBackground(new Color(245, 255, 250));
		lblAvailableInfo.setBounds(121, 249, 142, 23);
		bottomPanel.add(lblAvailableInfo);
		
		JLabel lblRow = new JLabel("Row Status:");
		lblRow.setFont(new Font("Eras Medium ITC", Font.BOLD, 20));
		lblRow.setBounds(10, 283, 101, 23);
		bottomPanel.add(lblRow);
		
		JLabel lblRowInfo = new JLabel("<dynamic>");
		lblRowInfo.setOpaque(true);
		lblRowInfo.setFont(new Font("Eras Medium ITC", Font.PLAIN, 20));
		lblRowInfo.setBackground(new Color(245, 255, 250));
		lblRowInfo.setBounds(121, 283, 142, 23);
		bottomPanel.add(lblRowInfo);
		
		JButton btnBack = new JButton("");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Hub(true).getFrame().setVisible(true); //Already manager if you are at this window
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
			
			while(r.next()) {
				String name = r.getString("inventory_name");
				String brand = r.getString("brand");
				String category = r.getString("inventory_category");
				String color = r.getString("color");
				String size = r.getString("inventory_size");
				String price = r.getString("list_price");
				String sku = r.getString("sku");
				String available = r.getString("available_amount");			

			
				DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
				tableModel.addRow(new String[]{name, brand, category, color, size, price, sku, available});
			}
			
		
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
