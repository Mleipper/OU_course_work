package airlinecore;

import java.text.ParseException;
import java.util.*;
import m256date.*;
import m256people.*;

/**
 * The coordinating class for the Flight Control core system.
 */
public final class AirlineCoord 
{

   /**links*/
   /**
    * a collection of all Airport objects
    */
   private final Collection<Airport> airports;
   /**
    * a collection of all Flight objects
    */
   private final Collection<Flight> flights;
   /**
    * a map whose values are all Passenger objects and
    * whose keys are the passport numbers
    */
   private final Map<String, Passenger> passengers;

   /**constructor*/
   /**
    * Initialises a new AirlineCoord object with some passengers, flights and airports.
    */
   public AirlineCoord()
   {
      airports = new HashSet<Airport>();
      Airport airport1 = addAirport("Edinburgh");
      Airport airport2 = addAirport("London Heathrow");
      Airport airport3 = addAirport("Cardiff");
      Airport airport4 = addAirport("Manchester");
      Airport airport5 = addAirport("Nice");
      Airport airport6 = addAirport("Prestwick");
      Airport airport7 = addAirport("Madrid");
      Airport airport8 = addAirport("Berlin");
      Airport airport9 = addAirport("Athens");
      Airport airport10 = addAirport("Brussels");

      flights = new HashSet<Flight>();
      try
      {
         Flight flight1 = addFlight(new M256Date("15/10/16"), 50, airport1, airport2);
         Flight flight2 = addFlight(new M256Date("25/10/16"), 20, airport2, airport3);
         Flight flight3 = addFlight(new M256Date("10/10/16"), 3, airport1, airport3);
      }
      catch (ParseException e)
      {
         System.out.println(e);
      }

      passengers = new TreeMap<String, Passenger>();
      addPassenger(new Name("Mrs", "Hilda", "Ogden"), "01234567");
      addPassenger(new Name("Ms", "Tracey", "Barlow"), "791436709");
      addPassenger(new Name("Miss", "Amy", "Barlow"), "907346323");
      addPassenger(new Name("Mr", "Steve", "McDonald"), "055545967");
   }

   /**public protocol*/
   /**
    * Returns all the airports.
    *
    * @return a collection of all the Airport objects
    */
   public Collection<Airport> getAirports()
   {
      return new HashSet<Airport>(airports);
   }

   /**
    * Returns all the flights.
    *
    * @return a collection of all the Flight objects
    */
   public Collection<Flight> getFlights()
   {
      return new HashSet<Flight>(flights);
   }

   /**
    * Returns all the passengers.
    *
    * @return a collection of all the Passenger objects
    */
   public Collection<Passenger> getPassengers()
   {
      return new ArrayList<Passenger>(passengers.values());
   }

   /**
    * Returns the passenger with the given passport number or null
    * if there is no such passenger.
    *
    * @param aPassportNumber the passport number
    *
    * @return a Passenger object or null
    */
   public Passenger getPassenger(String aPassportNumber)
   {
      return passengers.get(aPassportNumber);
   }

   /**
    * Returns the flights for which the passenger has bookings.
    *
    * @param aPassenger the passenger
    *
    * @return a collection of Flight objects 
    */
   public Collection<Flight> getFlights(Passenger aPassenger)
   {
      return aPassenger.getFlights();
   }

   /**
    * Returns the departure airport for the flight.
    *
    * @param aFlight the given flight
    *
    * @return the departure airport for the flight.
    */
   public Airport getDepartureAirport(Flight aFlight)
   {
      return aFlight.getDepartureAirport();
   }

   /**
    * Returns the arrival airport for the flight.
    *
    * @param aFlight the given flight
    *
    * @return the arrival airport for the flight.
    */
   public Airport getArrivalAirport(Flight aFlight)
   {
      return aFlight.getArrivalAirport();
   }

   /**
    * Creates and returns a new Airport object with
    * name set to aName.
    *
    * @param aName the name of the airport
    *
    * @return the new Airport object
    */
   public Airport addAirport(String aName)
   {
      Airport theAirport = new Airport(aName);
      airports.add(theAirport);
      return theAirport;
   }

   /**
    * Creates and returns a new Flight object with a unique flightNumber,
    * date set to aDate and capacity set to aCapacity;
    *  links the new Flight object to departureAirport and arrivalAirport.
    *
    * @param aDate the date of the flight
    * @param aCapacity the capacity of the flight
    * @param departureAirport the flight's departure airport
    * @param arrivalAirport the flight's arrival airport
    *
    * @return the new Flight object
    *
    * @throws IllegalArgumentException if departureAirport and arrivalAirport are
    * identical.
    * @throws IllegalArgumentException if aCapacity is less than 1
    */
   public Flight addFlight(M256Date aDate, int aCapacity, Airport departureAirport, Airport arrivalAirport)
   {
      if (departureAirport == arrivalAirport)
      {
         throw new IllegalArgumentException("Airports are the same");
      }
      if (aCapacity < 1)
      {
         throw new IllegalArgumentException("Capacity is less than 1");
      }
      Flight theFlight = new Flight(aDate, aCapacity, departureAirport, arrivalAirport);
      flights.add(theFlight);
      return theFlight;
   }

   /**
    * If there is not already a passenger with the given passport number
    * records a passenger with the given name and passportNumber and returns true.
    * Otherwise returns false.
    *
    * @param aName the name of the passenger
    * @param aPassportNumber the passport number of the passenger
    *
    * @return true if the passport number is not already recorded and false otherwise
    */
   public boolean addPassenger(Name aName, String aPassportNumber)
   {
      if (passengers.containsKey(aPassportNumber))
      {
         return false;
      }
      else
      {
         passengers.put( aPassportNumber, new Passenger(aName, aPassportNumber));
         return true;
      }
   }

   /**
    * If the flight has seats available records a booking by
    * the passenger for the flight and returns true otherwise
    * returns false.
    *
    * @param aPassenger the passenger
    * @param aFlight the flight
    * @return Whether a booking was made
    *
    * @throws IllegalStateException if the passenger is
    * already booked on the flight.
    */
   public boolean book(Passenger aPassenger, Flight aFlight)
   {
      if (aFlight.contains(aPassenger))
      {
         throw new IllegalStateException("Passenger already on flight");
      }
      else if (!aFlight.hasSeats())
      {
         return false;
      }
      else
      {
         aFlight.add(aPassenger);
         return true;
      }

   }

   /**
    * Records the cancellation of a booking (if one exists)
    * by the given passenger for the given flight.
    * Any link between aPassenger and aFlight is removed.
    *
    * @param aPassenger the passenger
    * @param aFlight the flight
    *
    */
   public void cancelBooking(Passenger aPassenger, Flight aFlight)
   {
      aFlight.remove(aPassenger);
   }

}
