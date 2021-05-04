import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProductInformation {

	private JFrame frame;
	private JTextField txtInput;
	
	private boolean readyToAdd; //flag
	
	private String name;
	private String brand;
	private String color;
	private String size;
	private String price;
	
	private int quantity;
	
	public JFrame getFrame() {
		return frame;
	}
	

	public String getName() {
		return name;
	}


	public String getPrice() {
		return price;
	}
	
	public boolean isReadyToAdd() {
		return readyToAdd;
	}


	public void setReadyToAdd(boolean readyToAdd) {
		this.readyToAdd = readyToAdd;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setPrice(String price) {
		this.price = price;
	}
	

	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * Create the application.
	 */
	public ProductInformation(JTable table, String name, String brand, String color, String size, String price, int available) {
		initialize(table, name, brand, color, size, price, available);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(JTable table, String name, String brand, String color, String size, String price, int available) {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 402);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //CLOSES THIS FORM
		
		this.name = name;
		this.brand = brand;
		this.color = color;
		this.size = size;
		this.price = price;
		//this.available = available;
		
		//this.quantity = 4;
		
		this.readyToAdd = false;
		
		//Para acomodar
		frame.getContentPane().setLayout(null);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBounds(10, 81, 414, 183);
		frame.getContentPane().add(bottomPanel);
		
		JPanel topPanel = new JPanel();
		topPanel.setBounds(10, 11, 414, 59);
		frame.getContentPane().add(topPanel);
		topPanel.setLayout(new BorderLayout(0, 0)); //to center label
		
		JLabel lblTop = new JLabel("Product Information");
		lblTop.setHorizontalAlignment(SwingConstants.CENTER);
		lblTop.setFont(new Font("Eras Medium ITC", Font.BOLD | Font.ITALIC, 40));
		topPanel.add(lblTop, BorderLayout.CENTER);
		
		bottomPanel.setLayout(null);
		
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
		
		System.out.println("the given name was:...." + this.name);
		System.out.println("the given brand was:...." + this.brand);
		System.out.println("the given color was:...." + this.color);
		System.out.println("the given size was:...." + this.size);
		System.out.println("the given price was:...." + this.price);
		
		JLabel lblNameInfo = new JLabel(name);
		lblNameInfo.setFont(new Font("Eras Medium ITC", Font.PLAIN, 20));
		lblNameInfo.setBounds(121, 11, 283, 23);
		bottomPanel.add(lblNameInfo);
		
		lblNameInfo.setOpaque(true);
		lblNameInfo.setBackground(new Color(245, 255, 250));
		
		JLabel lblBrandInfo = new JLabel(brand);
		lblBrandInfo.setOpaque(true);
		lblBrandInfo.setFont(new Font("Eras Medium ITC", Font.PLAIN, 20));
		lblBrandInfo.setBackground(new Color(245, 255, 250));
		lblBrandInfo.setBounds(121, 45, 283, 23);
		bottomPanel.add(lblBrandInfo);
		
		JLabel lblColorInfo = new JLabel(color);
		lblColorInfo.setOpaque(true);
		lblColorInfo.setFont(new Font("Eras Medium ITC", Font.PLAIN, 20));
		lblColorInfo.setBackground(new Color(245, 255, 250));
		lblColorInfo.setBounds(121, 79, 283, 23);
		bottomPanel.add(lblColorInfo);
		
		JLabel lblSizeInfo = new JLabel(size);
		lblSizeInfo.setOpaque(true);
		lblSizeInfo.setFont(new Font("Eras Medium ITC", Font.PLAIN, 20));
		lblSizeInfo.setBackground(new Color(245, 255, 250));
		lblSizeInfo.setBounds(121, 113, 283, 23);
		bottomPanel.add(lblSizeInfo);
		
		JLabel lblPriceInfo = new JLabel(price);
		lblPriceInfo.setOpaque(true);
		lblPriceInfo.setFont(new Font("Eras Medium ITC", Font.PLAIN, 20));
		lblPriceInfo.setBackground(new Color(245, 255, 250));
		lblPriceInfo.setBounds(121, 147, 283, 23);
		bottomPanel.add(lblPriceInfo);
		
		JPanel inputPanel = new JPanel();
		inputPanel.setBounds(10, 275, 414, 77);
		frame.getContentPane().add(inputPanel);
		inputPanel.setLayout(null);
		
		
		JButton btnUpdate = new JButton("Update Cart");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String input = txtInput.getText();
				
				if(!input.matches("-?\\d+")){ //regex to check if its integer
					Notification.notNumber(getFrame());
					txtInput.setText("");
					
				}else { //else its a number
					
					if(Integer.parseInt(input) <= available) { //we good
						//Save quantity
						setName(name);
						setPrice(price);
						setQuantity(Integer.parseInt(input));
						setReadyToAdd(true);						
						Notification.succesfulUpdate(getFrame());
						
						DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
						tableModel.addRow(new Object[]{getName(), getQuantity(), getPrice()});
						
						getFrame().dispose();
						
					} else {//number > available
						Notification.failedQuantity(getFrame());
						txtInput.setText("");
					}
					
				}
				
			}
		});
		btnUpdate.setBounds(243, 11, 91, 55);
		inputPanel.add(btnUpdate);
		
		JLabel lblQuantity = new JLabel("Qty:");
		lblQuantity.setHorizontalAlignment(SwingConstants.TRAILING);
		lblQuantity.setFont(new Font("Eras Medium ITC", Font.BOLD, 20));
		lblQuantity.setBounds(38, 24, 62, 23);
		inputPanel.add(lblQuantity);
		
		txtInput = new JTextField();
		txtInput.setBounds(110, 24, 86, 23);
		inputPanel.add(txtInput);
		txtInput.setColumns(10);
		
		
	}
}
