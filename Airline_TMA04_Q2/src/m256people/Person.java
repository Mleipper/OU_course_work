package m256people;

import m256date.*;

/**
 * Person objects represent people.
 */
public class Person implements java.io.Serializable
{    
    //attributes
    
    /**
     * the name of the person
     */
    private Name name;  
    
    /**
     * the sex of the person
     */
    private Sex sex;    
    
    /**
     * the date of birth of the person
     */
    private M256Date dateOfBirth; 
    
    
    //constructors
    
    /**
     * Initialises a new Person object with the given attribute values and age according to aDate.
     *
     * @param aName the name of the person
     * @param aSex the sex of the person
     * @param aDate the date of birth of the person
     */
    public Person(Name aName, Sex aSex, M256Date aDate)
    {
        name = aName;
        sex = aSex;
        dateOfBirth = aDate;
    }
    
    
    /**
     * Initialises a new Person object with the given name. Sets sex, date of birth and age to null.
     *
     * @param aName the name
     */
    public Person(Name aName)
    {
        this(aName, null, null);
    }
    
    
    /**
     * Initialises a new Person object with the same attribute values as the argument.
     *
     * @param aPerson the person
     */
    public Person(Person aPerson)
    {
        name = aPerson.getName();
        sex = aPerson.getSex();
        dateOfBirth = aPerson.getDateOfBirth();
    }
    
    
    //public protocol
    
    /**
     * Returns the name of this person.
     *
     * @return name
     */
    public Name getName()
    {
        return name;
    }
    
    
    /**
     * Returns the sex of this person.
     *
     * @return sex
     */
    public Sex getSex()
    {
        return sex;
    }
    
    
    /**
     * Returns the age of this person.
     *
     * @return age
     */
    public int getAge()
    {
        return dateOfBirth.getAge();
    }
    
    
    /**
     * Returns the date of birth of this person.
     *
     * @return dateOfBirth
     */
    public M256Date getDateOfBirth()
    {
        return dateOfBirth;
    }
    
    
    /**
     * Sets the name of this person.
     *
     * @param aName the new name
     */
    public void setName(Name aName)
    {
        name = aName;
    }
    
    
    /**
     * Returns a string representation of this person's name, sex and date of birth.
     *
     * @return a string object representing the receiver
     */
    public String toString()
    {
        return name + " " + sex + " " + dateOfBirth;
    }
}