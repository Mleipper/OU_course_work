package airlinecore;

import java.util.*;
import m256people.*;

/**
 * Passenger objects represent the airline's passengers
 */
public class Passenger 
{

   /**attributes*/
   private final Name name;                    //the name of the passenger
   private final String passportNumber;        //the unique passport number of the passenger

   /**links*/
   private final Collection<Flight> flights;    //the linked Flight objects

   /**constructor*/
   
   /**
    * Initialises a new Passenger object with 
    * name set to aName
    * and passportNumber set to aPassportNumber;
    * sets flights to reference an empty collection.
    *
    * @param aName the passenger's name
    * @param aPassportNumber the passenger's passport number
    */
   Passenger(Name aName, String aPassportNumber)
   {
      name = aName;
      passportNumber = aPassportNumber;
      flights = new HashSet<Flight>();
   }
   
   
   /**public protocol*/
   
   /**
    * Returns the name of this passenger.
    *
    * @return name
    */
   public Name getName()
   {
      return name;
   }
   
   /**
    * Returns the passport number of this passenger.
    *
    * @return passportNumber
    */
   public String getPassportNumber()
   {
      return passportNumber;
   }
   
   /**
    * Returns this customer's name and passport number
    *
    * @return a string object representing the receiver
    */
   @Override
   public String toString()
   {
      return name + " " + passportNumber;
   }
   
   /**package protocol*/
   
   /**
    * Adds the given flight to those booked by this customer.
    *
    * A reference to aFlight is recorded.
    *
    * @param aFlight the flight
    */
   void add(Flight aFlight)
   {
      flights.add(aFlight);
   }
   
   /**
    * Returns the flights booked by this customer.
    *
    * @return a collection of all the linked Flight objects
    */
   Collection<Flight> getFlights()
   {
      return new HashSet<Flight>(flights);
   }
   
   /**
    * Records the removal of the given flight (if present)
    * from those booked by the receiver.
    * The reference to aFlight is removed.
    *
    * @param aFlight the flight
    */
   void remove(Flight aFlight)
   {
      flights.remove(aFlight);
   }
   
}
