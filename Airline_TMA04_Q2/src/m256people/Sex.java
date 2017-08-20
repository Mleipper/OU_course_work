package m256people;

/**
 * Provides two constants to represent sex (male or female).
 */
public enum Sex implements java.io.Serializable
{
    /**
     * Represents female.
     */
    F,
    /**
     * Represents male.
     */
    M;
    
    
    //public protocol
    
    /**
     * Returns a string version of the sex ("female" or "male").
     *
     * @return a String object representing the receiver
     */
    public String toString()
    {
        switch (this)
        {
            case F: return "female";
            default: return "male";
        }
    }
}