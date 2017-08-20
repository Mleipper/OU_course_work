package airlinecore;

import java.util.*;
import m256date.*;

/**
 * Flight objects represent the airline's flights
 */
public class Flight 
{

   private static int lastFlightNumber = 0; // the last flight number

   /**attributes*/
   private final String flightNumber; // the unique number of the flight
   private final M256Date date; // the date of the flight
   private final int capacity; // the passenger capacity of the flight

   /**links*/
   private final Airport[] airports; // the two linked Airport objects
   private final Collection<Passenger> passengers; // the linked Passenger objects

   /**constructor*/
   /**
    * Initialises a new Flight object with a unique flightNumber,
    * date set to aDate and capacity set to aCapacity.
    * Sets airports to reference aDepartureAirport and anArrivalAirport,
    * and passengers to reference an empty collection.
    *
    * @param aDate the date of the flight
    * @param aCapacity the capacity of the flight
    * @param aDepartureAirport the flight's departure airport
    * @param anArrivalAirport the flight's arrival airport
    */
   Flight(M256Date aDate, int aCapacity, Airport aDepartureAirport, Airport anArrivalAirport)
   {
      lastFlightNumber++;
      flightNumber = "OU" + lastFlightNumber;
      date = aDate;
      capacity = aCapacity;
      airports = new Airport[2];
      airports[0] = aDepartureAirport;
      airports[1] = anArrivalAirport;
      passengers = new HashSet<Passenger>();
   }

   /**public protocol*/
   /**
    * Returns the flight number of this flight.
    *
    * @return flightNumber
    */
   public String getFlightNumber()
   {
      return flightNumber;
   }

   /**
    * Returns the date of this flight.
    *
    * @return date
    */
   public M256Date getDate()
   {
      return date;
   }

   /**
    * Returns the passenger capacity of this flight.
    *
    * @return capacity
    */
   public int getCapacity()
   {
      return capacity;
   }

   

   /**
    * Returns this flight's number
    *
    * @return a string object representing the receiver
    */
   @Override
   public String toString()
   {
      return flightNumber;
   }

   /**package protocol*/
   
   /**
    * Returns the departure airport of this flight.
    *
    * @return the departure airport
    */
   Airport getDepartureAirport()
   {
      return airports[0];
   }

   /**
    * Returns the arrival airport of this flight.
    *
    * @return the arrival airport
    */
   Airport getArrivalAirport()
   {
      return airports[1];
   }

   /**
    * Returns the passengers on this flight.
    *
    * @return a collection of all the linked Passenger objects
    */
   Collection<Passenger> getPassengers()
   {
      return new HashSet<Passenger>(passengers);
   }

   /**
    * Returns the number of passengers currently booked on this flight.
    *
    * @return numberOfPassengers
    */
    int getNumberOfPassengers()
   {
      return passengers.size();
   }



   /**
    * Returns true if the flight's capacity
    * has not been reached; false otherwise.
    *
    * @return true if size of passengers is less than capacity, false otherwise
    */
   boolean hasSeats()
   {
      return (passengers.size() < capacity);
   }

   /**
    * Adds the passenger to those on this flight.
    * A reference to aPassenger is recorded.
    * aPassenger records a reference to the receiver.
    *
    * @param aPassenger the passenger
    */
   void add(Passenger aPassenger)
   {
      passengers.add(aPassenger);
      aPassenger.add(this);
   }

   /**
    * Returns true if the passenger is booked
    * on this flight; false otherwise.
    *
    * @return true if aPassenger is linked
    * to the receiver; false otherwise
    *
    * @param aPassenger the passenger
    */
   boolean contains(Passenger aPassenger)
   {
      return (passengers.contains(aPassenger));
   }

   /**
    * Records the removal of the booking (if it exists)
    * by the given passenger for this flight.
    * The receiver's reference to aPassenger is removed;
    * aPassenger's reference to the receiver is removed.
    *
    * @param aPassenger the passenger
    *
    */
   void remove(Passenger aPassenger)
   {
      passengers.remove(aPassenger);
      aPassenger.remove(this);
   }
}
