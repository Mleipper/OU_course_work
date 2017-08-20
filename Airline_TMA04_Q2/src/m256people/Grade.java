package m256people;

/**
 * Provides constants to be used for grades of junior doctors.
 */
public enum Grade implements java.io.Serializable
{
    /**
     * Represents grade 1
     */
    ONE,
    /**
     * Represents grade 2
     */
    TWO,
    /**
     * Represents grade 3
     */
    THREE;
    
    
    //public protocol
    
    /**
     * Returns the grade as a string ("1", "2" or "3").
     *
     * @return a String object representing the receiver
     */
    public String toString()
    {
        switch (this)
        {
            case ONE: return "1";
            case TWO:  return "2";
            default: return "3";
        }
    }
}