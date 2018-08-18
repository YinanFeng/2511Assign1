package ass1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class HotelBookingSystem {

    private ArrayList<Hotel> hotelList = new ArrayList<>();
    private ArrayList<Booking> bookingList = new ArrayList<>();

    public static void main(String[] args) {
		Scanner sc = null;
	      try
	      {
	          sc = new Scanner(new File(args[0]));    // args[0] is the first command line argument
	          // Read input from the scanner here
	          //BookingManager bm = new BookingManager();
              HotelBookingSystem hbs = new HotelBookingSystem();
	          while(sc.hasNext()) {
	        	  String[] newInfoLine = sc.nextLine().split(" ");
	        	  String infoDetail="";
	        	  if(newInfoLine[0].equals("Hotel")){
	        		  hbs.addNewRoom(newInfoLine);
	        		  continue;
	        	  }

	        	  if(newInfoLine[0].equals("Booking")) {
	        		  infoDetail = hbs.addNewBooking(newInfoLine);
	        	  }

	        	  if(newInfoLine[0].equals("Cancel")) {
	        		  infoDetail = hbs.cancelBooking(newInfoLine);
	        	  }

	        	  if(newInfoLine[0].equals("Change")) {
	        		  infoDetail = hbs.changeBooking(newInfoLine);
	        	  }

	        	  if(newInfoLine[0].equals("Print")) {
	        		  infoDetail = hbs.allHotelInfo(newInfoLine[1]);
	        	  }

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


    public void addNewRoom(String[] roomInfo) {
		Hotel findHotel = null;
		if(hotelList.size() == 0) {
			findHotel = new Hotel(roomInfo[1]);
			this.hotelList.add(findHotel);
		}else{
			boolean same=false;
			for(Hotel hotel:this.hotelList) {
				if(hotel.getName().equals(roomInfo[1])){
					same = true;
					findHotel = hotel;
					break;
				}
			}
			//it is a new hotel
			if(same == false) {
				findHotel = new Hotel(roomInfo[1]);
				this.hotelList.add(findHotel);
			}
		}
		
		findHotel.addNewRoom(roomInfo);
	}


    //return -1 if the booking is not successful, return 0 if it is success.
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

    private Hotel checkRoomAvailable(Booking newBooking) {
        Hotel availableHotel = null;
        for(Hotel hotel:this.hotelList) {
            int isAvailableHotel = 1;
            for(int i=0;i<newBooking.totalOrderNumber();i++) {
                int availableNum = hotel.availableSpecTypeRoom(newBooking.getStartDate(),newBooking.getDuration(),newBooking.getOrderList().get(i).getRoomType());
                if(availableNum < newBooking.getSpecOrderNor(i)) {
                	isAvailableHotel =0;
                }
            }
            if(isAvailableHotel == 1) {
                availableHotel = hotel;
                break;
            }
        }
        return availableHotel;
    }

    private String cancelBooking(String[] cancelInfo) {
        Booking booking= findBookingByBooker(cancelInfo[1]);
        if(booking == null) {
            return "Cancel rejected\n";
        }
        Hotel hotel = findHotelByName(booking.getHotelName());
        //set the status of room, change it to available.
        hotel.cancelRoom(booking.getRoomOrdered(),booking.getStartDate(),booking.getDuration());
        booking.setBookingStatus("Cancel");

        StringBuilder sb = new StringBuilder();
        sb.append("Cancel ");
        sb.append(booking.getName()).append("\n");

        return new String(sb);
    }

    public Booking findBookingByBooker(String bookerName) {
        for(Booking booking:bookingList) {
            if(booking.getName().equals(bookerName) && (booking.getBookingStatus().equals("Booking")||booking.getBookingStatus().equals("Change"))) {
                return booking;
            }
        }
        return null;
    }

    private Hotel findHotelByName(String hotelName) {
        for(Hotel hotel:this.hotelList) {
            if(hotel.getName().equals(hotelName)) {
                return hotel;
            }
        }
        return null;
    }




    private String changeBooking(String[] changeInfo) {
        Booking booking = findBookingByBooker(changeInfo[1]);
        if(booking == null) {
            return "Change rejected\n";
        }
        Hotel hotel = findHotelByName(booking.getHotelName());
        hotel.cancelRoom(booking.getRoomOrdered(),booking.getStartDate(),booking.getDuration());
        booking.setBookingStatus("CancelByChange");
        //try to reorder to see whether success

        String changeResult = this.addNewBooking(changeInfo);
        if(changeResult.equals("Booking rejected\n")) {
            //remain the original booking if change rejected.
            booking.setBookingStatus("Booking");
            hotel.rebookRoom(booking.getRoomOrdered(),booking.getStartDate(),booking.getDuration());
            return "Change rejected\n";
        }
        changeResult = changeResult.replace("Booking","Change");
        return changeResult;
    }

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
		//monthN or monthN -1
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
