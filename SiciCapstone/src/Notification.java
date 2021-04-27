import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public final class Notification {
	/*
	 * Class intended to collect all message dialog methods
	 * JGBT
	 */
	
    private Notification() {} //Don't let anyone instantiate this class.
    
    public static void failedLogIn(JFrame frame, JPasswordField txtPassword, JTextField txtUser) {
		JOptionPane.showMessageDialog(frame,
			    "Incorrect username or password!!!",
			    "Error 404",
			    JOptionPane.ERROR_MESSAGE);
		System.out.println("try again");
		txtUser.setText("");
		txtPassword.setText("");
    }
    
    public static void itemNotFound(JFrame frame) {
		JOptionPane.showMessageDialog(frame,
			    "Item not found",
			    "Error 404",
			    JOptionPane.ERROR_MESSAGE);
    }
    
    public static void failedQuantity(JFrame frame, JTextField txtInput) {
		JOptionPane.showMessageDialog(frame,
			    "cantidad mayor de lo que hay disponible",
			    "Error 404",
			    JOptionPane.ERROR_MESSAGE);
		txtInput.setText("");
    }
    
    public static void notNumber(JFrame frame, JTextField txtInput) {
		JOptionPane.showMessageDialog(frame,
			    "Please enter number!!!",
			    "Error 404",
			    JOptionPane.ERROR_MESSAGE);
		txtInput.setText("");
    }
    
    public static void updatedCart(JFrame frame, JTextField txtInput) {
		JOptionPane.showMessageDialog(frame,
			    "Succesful update!!!");
		txtInput.setText("");
    }
}
