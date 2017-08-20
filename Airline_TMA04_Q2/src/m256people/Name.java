package m256people;

/**
 * Name objects represent names of people.
 */
public class Name implements Comparable<Name>, java.io.Serializable
{    
    //attributes
        
    /**
     * the title of the person
     */
    private final String title;
    
    /**
     * the first name of the person
     */
    private final String firstName;
    
    /**
     * the surname of the person
     */    
    private final String surname;
    
    
    //constructor
    
    /**
     * Initialises a new Name object with the given attribute values.
     *
     * @param aTitle the title
     * @param aFirstName the first name
     * @param aSurname the surname
     */
    public Name(String aTitle, String aFirstName, String aSurname)
    {
        title = aTitle;
        firstName = aFirstName;
        surname = aSurname;
    }
    
    
    //public protocol
    
    /**
     * Returns the title.
     *
     * @return title
     */
    public String getTitle()
    {
        return title;
    }
    
    
    /**
     * Returns the first name.
     *
     * @return firstName
     */
    public String getFirstName()
    {
        return firstName;
    }
    
    
    /**
     * Returns the surname.
     *
     * @return surname
     */
    public String getSurname()
    {
        return surname;
    }
    
    
    /**
     * Returns true if o is a Name object with title, firstName
     * and surname equal to those of the receiver, otherwise returns false.
     *
     * @param o the object to be checked for equality with the receiver
     *
     * @return true if the receiver and argument are equal, false otherwise
     */
    public boolean equals(Object o)
    {
        if (!(o instanceof Name))
        {
            return false;
        }
        Name n = (Name) o;
        return (n.title.equals(title)) && (n.firstName.equals(firstName)) && (n.surname.equals(surname));
    }
    
    
    /**
     * Returns the hashcode of the name.
     *
     * @return hashcode
     */
    public int hashCode()
    {
        int code = 17;
        code = 37*code + title.hashCode();
        code = 37*code + firstName.hashCode();
        code = 37*code + surname.hashCode();
        return code;
    }
    
    
    /**
     * Returns a negative integer if the receiver is alphabetically
     * before aName,
     * a positive integer if the receiver is  alphabetically
     * after aName
     * and zero otherwise.
     * Name objects are compared using surname, firstName and title in that order.
     *
     * @param aName the name to be compared with the receiver
     *
     * @return -1 if the receiver is before aName, 1 if it is after aName and 0
     * otherwise
     *
     */
    public int compareTo(Name aName)
    {
        if (surname.compareTo(aName.surname) < 0)
            return -1;
        if (surname.compareTo(aName.surname) > 0)
            return 1;
        if  (firstName.compareTo(aName.firstName) < 0)
            return -1;
        if  (firstName.compareTo(aName.firstName) > 0)
            return 1;
        if  (title.compareTo(aName.title) < 0)
            return -1;
        if  (title.compareTo(aName.title) > 0)
            return 1;
        return 0;
    }
    
    
    /**
     * Returns a string representation of this name's title, first name and surname.
     *
     * @return a String object representing the receiver
     */
    public String toString()
    {
        return title + " " + firstName + " " + surname;
    }
}