import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Images {
	
	
	  public static BufferedImage logOut;
	  public static BufferedImage logo;
	  
	  public Images() {
		  //
		  //myPicture = ImageIO.read(new File("/SiciCapstone/res/Images/logOut.png"));
		  //
		  
		  try {
			  System.out.println("whaaat");
			  logOut = ImageIO.read(getClass().getResourceAsStream("/Images/logOut.png"));
			  //logOut = ImageIO.read(new File("C:\\Users\\Josean\\Desktop\\Todo lo de gaby\\todo java\\SiciCapstone\\res\\Images\\logOut.png"));
			 // logOut = ImageIO.read(getClass().getResourceAsStream("/Images/logOut.png"));
			  
			 // logo = ImageIO.read(new File("C:\\Users\\Josean\\Desktop\\Todo lo de gaby\\todo java\\SiciCapstone\\res\\Images\\AlhannasLogo.jpg"));
			  
			  
		  }catch (IOException e) {
			  System.out.println("wtff");
			  e.printStackTrace();
		  }

	  }
	    public static BufferedImage loadImage(String path) {
	        try {
	            return ImageIO.read(Images.class.getResourceAsStream(path));
	        } catch (IOException e) {
	            e.printStackTrace();
	            System.exit(1);
	        }
	        return null;
	    }
}
