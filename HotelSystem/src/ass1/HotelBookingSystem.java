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
	        	 
	        	  if(newInfoLine[0].equals("Hotel")){
	        		  bm.addNewRoom(newInfoLine);	  
	        	  }
	        	  
	        	  if(newInfoLine[0].equals("Booking")) {
	        		  Boolean successBooking = bm.addNewBooking(newInfoLine);  
	        		  if(successBooking == true) {
	        			  
	        		  }else{
	        			  
	        		  }
	        		  
	        		  
	        	  }
	        	  
	        	  if(newInfoLine[0] == "Cancel") {
	        		  bm.cancelBooking(newInfoLine);
	        	  }
	        	  
	        	  if(newInfoLine[0] == "Change") {
	        		  Boolean successChanging = bm.changeBooking(newInfoLine);
	        		  if(successChanging == true) {
	        			  
	        		  }else {
	        			  
	        		  }
	        		  
	        	  }
	        	  
	        	  if(newInfoLine[0] == "Print") {
	        		  
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
