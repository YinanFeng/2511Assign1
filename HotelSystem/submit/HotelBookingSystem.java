package ass1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * @author YinanFeng
 *
 */
public class HotelBookingSystem {

    private ArrayList<Hotel> hotelList = new ArrayList<>();
    private ArrayList<Booking> bookingList = new ArrayList<>();

    /**
     * @param args
     */
    public static void main(String[] args) {
		Scanner sc = null;
	      try
	      {
	    	// args[0] is the first command line argument
	          sc = new Scanner(new File(args[0]));    
	          // Read input from the scanner here
              HotelBookingSystem hbs = new HotelBookingSystem();
	          while(sc.hasNext()) {
	        	  String[] newInfoLine = sc.nextLine().split(" ");
	        	  String infoDetail="";
	        	  if(newInfoLine[0].equals("Hotel")){
	        		  hbs.addNewRoom(newInfoLine);
	        		  continue;
	        	  }
	        	  //process a booking request
	        	  if(newInfoLine[0].equals("Booking")) {
	        		  infoDetail = hbs.addNewBooking(newInfoLine);
	        	  }
	        	  //process a cancel request
	        	  if(newInfoLine[0].equals("Cancel")) {
	        		  infoDetail = hbs.cancelBooking(newInfoLine);
	        	  }
	        	  //process a change request
	        	  if(newInfoLine[0].equals("Change")) {
	        		  infoDetail = hbs.changeBooking(newInfoLine);
	        	  }
	        	  //process a print request
	        	  if(newInfoLine[0].equals("Print")) {
	        		  infoDetail = hbs.allHotelInfo(newInfoLine[1]);
	        	  }
	        	  //print that information
	        	  System.out.print(infoDetail);
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


    /**
     * @param roomInfo
     */
    //add the new room to the hotel it belongs. If the hotel have not be created before, create one.
    public void addNewRoom(String[] roomInfo) {
		Hotel findHotel = null;
		//create the hotel if there is no hotel exist
		if(hotelList.size() == 0) {
			findHotel = new Hotel(roomInfo[1]);
			this.hotelList.add(findHotel);
		//find the hotel if there are some hotel exist
		}else{
			boolean same=false;
			for(Hotel hotel:this.hotelList) {
				if(hotel.getName().equals(roomInfo[1])){
					same = true;
					findHotel = hotel;
					break;
				}
			}
			//create hotel if it is a new hotel
			if(same == false) {
				findHotel = new Hotel(roomInfo[1]);
				this.hotelList.add(findHotel);
			}
		}
		//add the room in the hotel
		findHotel.addNewRoom(roomInfo);
	}


    
    /**
     * @param bookingInfo
     * @return String(sb)
     */
    //process the booking request
    //return "Booking rejected\n" if the booking is not successful
    //return the string contains the detail of booking if it is success
    private String addNewBooking(String[] bookingInfo) {
        Calendar startDate = this.convertStartDate(bookingInfo);
        int duration = Integer.parseInt(bookingInfo[4]);
        String[] orders = Arrays.copyOfRange(bookingInfo, 5, bookingInfo.length);
        Booking newBooking = new Booking(bookingInfo[0],bookingInfo[1],startDate,duration,orders);
        Hotel availableHotel = this.checkRoomAvailable(newBooking);
        //reject the booking if there is no available room.
        if(availableHotel == null) {
            newBooking.setBookingStatus("Booking rejected");
            bookingList.add(newBooking);
            return "Booking rejected\n";
        }
        //set hotel name for order.
        newBooking.setHotelName(availableHotel.getName());
        //booking is success, them booking the room and record the room number booked.
        ArrayList<Integer> roomList = availableHotel.bookAllRoom(newBooking);
        newBooking.setRoomOrdered(roomList);
        bookingList.add(newBooking);
        //create the string
        StringBuilder sb = new StringBuilder();
        sb.append("Booking ");
        sb.append(newBooking.getName()).append(" ");
        sb.append(newBooking.getHotelName());
        for(int i:roomList) {
            sb.append(" ");
            sb.append(i);
        }
        sb.append("\n");

        return new String(sb);
    }

    /**
     * @param newBooking
     * @return availableHotel
     */
    //check if there is a hotel available for the booking
    //if so, return the hotel instance
    //if not return null(hotel=null)
    private Hotel checkRoomAvailable(Booking newBooking) {
        Hotel availableHotel = null;
        //loop the hotel list
        for(Hotel hotel:this.hotelList) {
            int isAvailableHotel = 1;
            for(int i=0;i<newBooking.totalOrderNumber();i++) {
                int availableNum = hotel.availableSpecTypeRoom(newBooking.getStartDate(),newBooking.getDuration(),newBooking.getOrderList().get(i).getRoomType());
                //if the hotel have less available room then required, set it as not available hotel
                if(availableNum < newBooking.getSpecOrderNor(i)) {
                	isAvailableHotel =0;
                }
            }
            //if find one, break the loop
            if(isAvailableHotel == 1) {
                availableHotel = hotel;
                break;
            }
        }
        return availableHotel;
    }

    
    /**
     * @param cancelInfo
     * @return new String(sb)
     */
    //process the cancel request
    // return "Cancel rejected\n" if the cancel is not successful
    //return the string contains the customer of cancel if it is success
    private String cancelBooking(String[] cancelInfo) {
        Booking booking= findBookingByBooker(cancelInfo[1]);
        //if the customer have no booking recorded
        if(booking == null) {
            return "Cancel rejected\n";
        }
        Hotel hotel = findHotelByName(booking.getHotelName());
        //set the status of room, change it to available.(can be booked by other customer)
        hotel.cancelRoom(booking.getRoomOrdered(),booking.getStartDate(),booking.getDuration());
        booking.setBookingStatus("Cancel");
        //build the string to return
        StringBuilder sb = new StringBuilder();
        sb.append("Cancel ");
        sb.append(booking.getName()).append("\n");

        return new String(sb);
    }

    /**
     * @param bookerName
     * @return booking
     */
    //loop the list
    //getting the booking instance by giving the name of the customer
    public Booking findBookingByBooker(String bookerName) {
        for(Booking booking:bookingList) {
            if(booking.getName().equals(bookerName) && (booking.getBookingStatus().equals("Booking")||booking.getBookingStatus().equals("Change"))) {
                return booking;
            }
        }
        return null;
    }

    /**

     * @param hotelName
     * @return hotel
     */
    //loop the list
    //getting the hotel instance by giving the name of hotel
    private Hotel findHotelByName(String hotelName) {
        for(Hotel hotel:this.hotelList) {
            if(hotel.getName().equals(hotelName)) {
                return hotel;
            }
        }
        return null;
    }


    /**
     * @param changeInfo
     * @return changeResult
     */
    //process the change request
    //return "Change rejected\n" if the change is not successful
    //return the string contains the change details if it is success
    private String changeBooking(String[] changeInfo) {
        Booking booking = findBookingByBooker(changeInfo[1]);
        //if the customer has no booking record, the change should be rejected
        if(booking == null) {
            return "Change rejected\n";
        }
        Hotel hotel = findHotelByName(booking.getHotelName());
        hotel.cancelRoom(booking.getRoomOrdered(),booking.getStartDate(),booking.getDuration());
        booking.setBookingStatus("CancelByChange");
        String changeResult = this.addNewBooking(changeInfo);
        //If new request can not be fulfilled
        if(changeResult.equals("Booking rejected\n")) {
            //remain the original booking if change rejected.
            booking.setBookingStatus("Booking");
            hotel.rebookRoom(booking.getRoomOrdered(),booking.getStartDate(),booking.getDuration());
            return "Change rejected\n";
        }
        changeResult = changeResult.replace("Booking","Change");
        return changeResult;
    }
    
    
    /**
     * @param hotelName
     * @return new String(sb)
     */
    //build the string which contains all the booking information of a hotel
    public String allHotelInfo(String hotelName) {
    	Hotel hotel=findHotelByName(hotelName);
        StringBuilder sb = new StringBuilder();
        hotel.allRoomInfoWithBooking();
        sb.append(hotel.allRoomInfoWithBooking());
        return new String(sb);
    }

    private Calendar convertStartDate(String[] bookingInfo) {
		int dayN = Integer.parseInt(bookingInfo[3]);
		int monthN = this.switchMonth(bookingInfo[2]);
		Calendar startDate = new GregorianCalendar(2018,(monthN-1),dayN);
		return startDate;	
	}
	
	private int switchMonth(String monthS) {
		int monthN = 0;
		switch(monthS) {
			case "Jan":
				monthN = 1;break;
			case "Feb":
				monthN = 2;break;
			case "Mar":
				monthN = 3;break;
			case "Apr":
				monthN = 4;break;
			case "May":
				monthN = 5;break;
			case "Jun":
				monthN = 6;break;
			case "Jul":
				monthN = 7;break;
			case "Aug":
				monthN = 8;break;
			case "Sep":
				monthN = 9;break;
			case "Oct":
				monthN = 10;break;
			case "Nov":
				monthN = 11;break;
			case "Dec":
				monthN = 12;break;
		}
		return monthN;
	}

}
