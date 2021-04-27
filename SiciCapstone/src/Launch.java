import java.awt.EventQueue;

public class Launch {
	
	/**
	 * Launch the application.
	 * JGBT
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Login().getFrame().setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	
	}
}
