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
	          RoomManager rm = new RoomManager();
	        
	          
	          while(sc.hasNext()) {
	        	  String[] newInfoLine = sc.nextLine().split(" ");
	        	 
	        	  if(newInfoLine[0].equals("Hotel")){
	        		  rm.addNewRoom(newInfoLine);
	        		  
	        		  
	        		  
	        	  }
	        	  
	        	  if(newInfoLine[0].equals("Booking")) {
	        		  bm.addNewBooking(newInfoLine);  
	        	  }
	        	  
	        	  if(newInfoLine[0] == "Cancel") {
	        		  
	        	  }
	        	  
	        	  if(newInfoLine[0] == "Change") {
	        		  
	        	  }
	        	  
	        	  
	        	  
	        	  
	        	  
	        	  
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
