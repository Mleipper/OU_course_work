package airlinecore;

/**
 * Airport objects represent airports used by the airline.
 */
public class Airport 
{
   /**attributes*/
    private final String name;     // the name of the airport

    /**constructor*/
    
    /**
    * Initialises a new Airport object with
    * name set to aName.
    *
    * @param aName the name of the airport
    */
    Airport(String aName)
    {
        name = aName;
    }
    
    
    /**public protocol*/
    
     /**
     * Returns the name of this airport.
     *
     * @return name
     */
    public String getName()
    {
        return name;
    } 

     /**
     * Returns this airport's name
     *
     * @return a string object representing the receiver
     */
    @Override
    public String toString()
    {
        return name;
    }
}
