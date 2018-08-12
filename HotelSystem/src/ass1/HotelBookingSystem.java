package ass1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HotelBookingSystem {
	
	public static void main(String[] args) {
		Scanner sc = null;
	      try
	      {
	          sc = new Scanner(new File(args[0]));    // args[0] is the first command line argument
	          // Read input from the scanner here
	          BookingManager bm = new BookingManager();
 
	          while(sc.hasNext()) {
	        	  String[] newInfoLine = sc.nextLine().split(" ");
	        	  String infoDetail="";
	        	  if(newInfoLine[0].equals("Hotel")){
	        		  bm.addNewRoom(newInfoLine);
	        		  continue;
	        	  }
	        	  
	        	  if(newInfoLine[0].equals("Booking")) {
	        		  infoDetail = bm.addNewBooking(newInfoLine);  
	        	  }
	        	  
	        	  if(newInfoLine[0].equals("Cancel")) {
	        		  infoDetail = bm.cancelBooking(newInfoLine);
	        	  }
	        	  
	        	  if(newInfoLine[0].equals("Change")) {
	        		  infoDetail = bm.changeBooking(newInfoLine);		  
	        	  }
	        	  
	        	  if(newInfoLine[0].equals("Print")) {
	        		  infoDetail = bm.allHotelInfo(newInfoLine[1]);
	        	  }
	        
	        	  System.out.println(infoDetail);
	          }
	          
	      }
	      catch (FileNotFoundException e)
	      {
	          System.out.println(e.getMessage());
	      }
	      finally
	      {
	          if (sc != null) sc.close();
	      }
	}
	
	
}
