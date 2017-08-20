package airlinegui;

import java.util.*;
import java.text.*;
import m256date.*;
import m256people.*;
import airlinecore.*;
import javax.swing.JOptionPane;

public class AirlineGUI extends javax.swing.JFrame
{
   //attributes

   private AirlineCoord airline; //the coordinating object

   //constructor
   /** Creates new form AirlineGUI */
   public AirlineGUI()
   {
      initComponents();
      airline = new AirlineCoord();

      displayPassengers1();
      displayPassengers2();
      displayPassengers3();
      // TO DO (b)(iv)
      displayDepartureAirports();
      

   }

   //Helper methods
   //*****************************************************************************
   //The methods in this section are concerned with the Add Flight screen
   private void displayDepartureAirports()
   {
      // TO DO (b)(i)
      
      addFlightDepartureAirports.setListData(airline.getAirports());
      addFlightDepartureAirports.setSelectedIndex(0);
      
   } 

   private void displayArrivalAirports()
   {
      Collection<Airport>airport =  airline.getAirports();
      airport.remove(addFlightDepartureAirports.getSelectedValue());
      addFlightArrivalAirports.setListData(airport);
      addFlightArrivalAirports.setSelectedIndex(0);
      
   }

   private void resetFlightFields()
   {
      // TO DO (b)(iii)
      addFlightDateField.setText("");
      addFlightCapacityField.setText("");

   }

   private boolean doAddFlight()
   {
       boolean flightOk = false;
       int capcity = 0;     
       M256Date date = null;
              
       try
       {

           capcity = Integer.parseInt(addFlightCapacityField.getText());
           if (capcity > 0)
           {
               date = new M256Date(addFlightDateField.getText());
               Flight theFlight = airline.addFlight(date, capcity,
                       (Airport) addFlightDepartureAirports.getSelectedValue(),
                       (Airport) addFlightArrivalAirports.getSelectedValue());
               addFlightOutcomeArea.setText(
                       "  New flight created with flight "
                       + "number OUX (where OUX is the flight number returned by "
                       + "the system)" + theFlight.toString());

               flightOk = true;
           }
           else
           {
               addFlightOutcomeArea.setText(
                       "Error: capacity must be greater than zero.");
           }
       }
        catch(NumberFormatException e)
        {
            addFlightOutcomeArea.setText("Error: Non-integer entered for capacity");
        }
        catch (ParseException e)
        {
          addFlightOutcomeArea.setText("Error: Incorrect date format");
        }
              
      return flightOk;
   }

   //*****************************************************************************
   //The methods in this section are concerned with the Add Passenger screen
   private void displayPassengersDetails()
   {
      String result = "";
      for (Passenger thePassenger : airline.getPassengers())
      {
         result = result + thePassenger + "\n";
      }
      addPassengerTextArea.setText(result);
   }

   private void doAddPassenger()
   {
      String title = addPassengerTitleField.getText();
      String firstName = addPassengerFirstNameField.getText();
      String surname = addPassengerSurnameField.getText();
      String passport = addPassengerPassportField.getText();
      if (title.equals("") | firstName.equals("") | surname.equals("") | passport.equals(""))
      {
         addPassengerOutcomeArea.setText("Error: incomplete passenger information");
      }
      else
      {
         Name name = new Name(title, firstName, surname);
         if (airline.addPassenger(name, passport))
         {
            addPassengerOutcomeArea.setText("Passenger " + name + " added");
            resetPassengerFields();
            displayPassengersDetails();
         }
         else
         {
            Name existingName = airline.getPassenger(passport).getName();
            addPassengerOutcomeArea.setText("Error: existing passenger " + existingName + " already has this passport number");
         }
      }
   }

   private void resetPassengerFields()
   {
      addPassengerTitleField.setText("");
      addPassengerFirstNameField.setText("");
      addPassengerSurnameField.setText("");
      addPassengerPassportField.setText("");
   }

   //***************************************************************************
   //The methods in this section are concerned with the List Passenger's Flights screen
   private void displayPassengers1()
   {
      listPasssengersPassengerList.setListData(airline.getPassengers());
      listPasssengersPassengerList.setSelectedIndex(0);
   }

   private void displayFlightDetails1()
   {
      String result = "";
      Passenger aPassenger = (Passenger) listPasssengersPassengerList.getSelectedValue();
      for (Flight theFlight : airline.getFlights(aPassenger))
      {
         result = result + theFlight.getFlightNumber();
         result = result + " " + theFlight.getDate();
         result = result + " " + airline.getDepartureAirport(theFlight);
         result = result + " to " + airline.getArrivalAirport(theFlight) + "\n";
      }
      listPasssengersFlightTextArea.setText(result);
   }
   //*****************************************************************************
   //The methods in this section are concerned with the Make Booking screen

   private void displayPassengers2()
   {
      bookFlightPassengerList.setListData(airline.getPassengers());
      bookFlightPassengerList.setSelectedIndex(0);
   }

   private void displayFlightsPassengerNotBookedOn()
   {
      // TO DO (d)(i)
      Passenger thePassenger = (Passenger) bookFlightPassengerList.getSelectedValue();
      Collection<Flight> theBookedFlights = airline.getFlights(thePassenger);
      Collection<Flight> notBookedOn = airline.getFlights();
      notBookedOn.removeAll(theBookedFlights);
      bookFlightFlightList.setListData(notBookedOn);
      bookFlightFlightList.setSelectedIndex(0);
   }

   private void displayFlightDetails2()
   {
        // TO DO (d)(ii)
       Flight theFlight = (Flight) bookFlightFlightList.getSelectedValue();
       Airport departureAirport = airline.getDepartureAirport(theFlight);
       Airport arrivalAirport = airline.getArrivalAirport(theFlight);
       M256Date date = theFlight.getDate();
       bookFlightCapacityField.setText(String.valueOf(theFlight.getCapacity()));
       bookFlightDateField.setText(date.toString());
       bookFlightDepartureField.setText(departureAirport.toString());
       bookFlightArrivalField.setText(arrivalAirport.toString());
   }

   private void doBookFlight()
   {
      // TO DO (d)(iii)
      Passenger thePassenger = (Passenger) bookFlightPassengerList.getSelectedValue();
      Flight theFlight = (Flight) bookFlightFlightList.getSelectedValue();
      boolean result  = airline.book(thePassenger, theFlight);
      displayFlightsPassengerNotBookedOn();
      if (result == true)
      {
          Name name = thePassenger.getName();
          bookFlightOutcomeArea.setText("passenger " + name.toString()
                  +" booked on flight " + theFlight.toString());}
      else
      {
          bookFlightOutcomeArea.setText("Error: flight " + theFlight.toString()
          + " is fully booked");
      
      
      }
          
   
      
      
   }
   //***************************************************************************
   //The methods in this section are concerned with the Cancel Booking screen

   private void displayPassengers3()
   {
      Collection<Passenger> thePassengers = airline.getPassengers();
      cancelBookingPassengerList.setListData(thePassengers);
      cancelBookingPassengerList.setSelectedIndex(0);
   }

   private void displayBookedFlights()
   {
      Passenger thePassenger = (Passenger) cancelBookingPassengerList.getSelectedValue();
      Collection<Flight> theBookedFlights = airline.getFlights(thePassenger);
      cancelBookingFlightList.setListData(theBookedFlights);
      cancelBookingFlightList.setSelectedIndex(0);
   }

   private void doCancelBooking()
   {
      Passenger thePassenger = (Passenger) cancelBookingPassengerList.getSelectedValue();
      Flight theFlight = (Flight) cancelBookingFlightList.getSelectedValue();
      airline.cancelBooking(thePassenger, theFlight);
      displayBookedFlights();
      cancelBookingOutcomeArea.setText("Booking for passenger " + thePassenger.getName()
              + " on flight " + theFlight.getFlightNumber() + " has been cancelled");
   }

   /** This method is called from within the constructor to
    * initialize the form.
    * WARNING: Do NOT modify this code. The content of this method is
    * always regenerated by the Form Editor.
    */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        addFlightScreen = new javax.swing.JPanel();
        addFlightOutcomeArea = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        addFlightDepartureAirports = new m256gui.M256JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        addFlightArrivalAirports = new m256gui.M256JList();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        addFlightDateField = new javax.swing.JTextField();
        addFlightCapacityField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        addFlightButton = new javax.swing.JButton();
        addPassengerScreen = new javax.swing.JPanel();
        addPassengerOutcomeArea = new javax.swing.JTextArea();
        jScrollPane7 = new javax.swing.JScrollPane();
        addPassengerTextArea = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        addPassengerTitleField = new javax.swing.JTextField();
        addPassengerPassportField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        addPassengerButton = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        addPassengerFirstNameField = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        addPassengerSurnameField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        listPassengersFlightsScreen = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        listPasssengersPassengerList = new m256gui.M256JList();
        jScrollPane6 = new javax.swing.JScrollPane();
        listPasssengersFlightTextArea = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        bookFlightScreen = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        bookFlightPassengerList = new m256gui.M256JList();
        jScrollPane9 = new javax.swing.JScrollPane();
        bookFlightFlightList = new m256gui.M256JList();
        jLabel16 = new javax.swing.JLabel();
        bookFlightCapacityField = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        bookFlightButton = new javax.swing.JButton();
        bookFlightOutcomeArea = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        bookFlightDateField = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        bookFlightDepartureField = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        bookFlightArrivalField = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        cancelBookingScreen = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        cancelBookingPassengerList = new m256gui.M256JList();
        jLabel11 = new javax.swing.JLabel();
        cancelBookingButton = new javax.swing.JButton();
        cancelBookingOutcomeArea = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        cancelBookingFlightList = new m256gui.M256JList();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("OU Airline System");
        getContentPane().setLayout(null);

        addFlightScreen.addComponentListener(new java.awt.event.ComponentAdapter()
        {
            public void componentShown(java.awt.event.ComponentEvent evt)
            {
                addFlightScreenComponentShown(evt);
            }
        });

        addFlightOutcomeArea.setEditable(false);
        addFlightOutcomeArea.setLineWrap(true);
        addFlightOutcomeArea.setWrapStyleWord(true);
        addFlightOutcomeArea.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        addFlightDepartureAirports.setModel(new javax.swing.AbstractListModel()
        {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        addFlightDepartureAirports.addListSelectionListener(new javax.swing.event.ListSelectionListener()
        {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt)
            {
                addFlightDepartureAirportsValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(addFlightDepartureAirports);

        addFlightArrivalAirports.setModel(new javax.swing.AbstractListModel()
        {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(addFlightArrivalAirports);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel1.setText("1. Select departure airport");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel2.setText("2. Select arrival airport");

        addFlightDateField.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                addFlightDateFieldActionPerformed(evt);
            }
        });

        addFlightCapacityField.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                addFlightCapacityFieldActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel4.setText("3. Enter flight date (DD/MM/YY)");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel5.setText("4. Enter flight seating capacity");

        addFlightButton.setText("Add flight");
        addFlightButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                addFlightButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout addFlightScreenLayout = new org.jdesktop.layout.GroupLayout(addFlightScreen);
        addFlightScreen.setLayout(addFlightScreenLayout);
        addFlightScreenLayout.setHorizontalGroup(
            addFlightScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(addFlightScreenLayout.createSequentialGroup()
                .add(addFlightOutcomeArea, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 622, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 71, Short.MAX_VALUE))
            .add(addFlightScreenLayout.createSequentialGroup()
                .add(58, 58, 58)
                .add(addFlightScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel1)
                    .add(jLabel4)
                    .add(addFlightDateField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 119, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(162, 162, 162)
                .add(addFlightScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel2)
                    .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(addFlightScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                        .add(addFlightScreenLayout.createSequentialGroup()
                            .add(addFlightCapacityField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 41, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(addFlightButton))
                        .add(jLabel5)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        addFlightScreenLayout.setVerticalGroup(
            addFlightScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(addFlightScreenLayout.createSequentialGroup()
                .add(27, 27, 27)
                .add(addFlightScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(jLabel2))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(addFlightScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                    .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .add(12, 12, 12)
                .add(addFlightScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4)
                    .add(jLabel5))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(addFlightScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(addFlightScreenLayout.createSequentialGroup()
                        .add(addFlightScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(addFlightDateField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(addFlightCapacityField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(30, 30, 30))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, addFlightScreenLayout.createSequentialGroup()
                        .add(addFlightButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)))
                .add(addFlightOutcomeArea, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 68, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Add Flight", addFlightScreen);

        addPassengerScreen.addComponentListener(new java.awt.event.ComponentAdapter()
        {
            public void componentShown(java.awt.event.ComponentEvent evt)
            {
                addPassengerScreenComponentShown(evt);
            }
        });

        addPassengerOutcomeArea.setEditable(false);
        addPassengerOutcomeArea.setLineWrap(true);
        addPassengerOutcomeArea.setWrapStyleWord(true);
        addPassengerOutcomeArea.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jScrollPane7.setPreferredSize(new java.awt.Dimension(140, 180));
        jScrollPane7.setVerifyInputWhenFocusTarget(false);

        addPassengerTextArea.setColumns(20);
        addPassengerTextArea.setEditable(false);
        addPassengerTextArea.setLineWrap(true);
        addPassengerTextArea.setRows(5);
        addPassengerTextArea.setWrapStyleWord(true);
        jScrollPane7.setViewportView(addPassengerTextArea);

        jLabel3.setText("Current passengers");

        jLabel7.setText("New passenger details");

        addPassengerButton.setText("Add passenger");
        addPassengerButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                addPassengerButtonActionPerformed(evt);
            }
        });

        jLabel14.setText("Title");

        jLabel6.setText("First name");

        jLabel15.setText("Surname");

        jLabel8.setText("Passport number");

        org.jdesktop.layout.GroupLayout addPassengerScreenLayout = new org.jdesktop.layout.GroupLayout(addPassengerScreen);
        addPassengerScreen.setLayout(addPassengerScreenLayout);
        addPassengerScreenLayout.setHorizontalGroup(
            addPassengerScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, addPassengerScreenLayout.createSequentialGroup()
                .add(addPassengerScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, addPassengerScreenLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(addPassengerOutcomeArea))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, addPassengerScreenLayout.createSequentialGroup()
                        .add(10, 10, 10)
                        .add(addPassengerScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jScrollPane7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 256, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 170, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(18, 18, 18)
                        .add(addPassengerScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(jLabel7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 200, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 127, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 172, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel15, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 141, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 200, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(addPassengerTitleField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 47, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(addPassengerSurnameField)
                            .add(addPassengerPassportField)
                            .add(addPassengerFirstNameField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 246, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(addPassengerScreenLayout.createSequentialGroup()
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(addPassengerButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 153, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        addPassengerScreenLayout.setVerticalGroup(
            addPassengerScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(addPassengerScreenLayout.createSequentialGroup()
                .add(17, 17, 17)
                .add(addPassengerScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(jLabel7))
                .add(6, 6, 6)
                .add(addPassengerScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(addPassengerScreenLayout.createSequentialGroup()
                        .add(jLabel14)
                        .add(4, 4, 4)
                        .add(addPassengerTitleField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(12, 12, 12)
                        .add(jLabel6)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(addPassengerFirstNameField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(jLabel15)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(addPassengerSurnameField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(24, 24, 24)
                        .add(jLabel8)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(addPassengerPassportField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jScrollPane7, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .add(18, 18, 18)
                .add(addPassengerButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(addPassengerOutcomeArea, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Add Passenger", addPassengerScreen);

        listPassengersFlightsScreen.addComponentListener(new java.awt.event.ComponentAdapter()
        {
            public void componentShown(java.awt.event.ComponentEvent evt)
            {
                listPassengersFlightsScreenComponentShown(evt);
            }
        });

        listPasssengersPassengerList.setModel(new javax.swing.AbstractListModel()
        {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        listPasssengersPassengerList.setSelectionMode(0);
        listPasssengersPassengerList.addListSelectionListener(new javax.swing.event.ListSelectionListener()
        {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt)
            {
                listPasssengersPassengerListValueChanged(evt);
            }
        });
        jScrollPane5.setViewportView(listPasssengersPassengerList);

        listPasssengersFlightTextArea.setEditable(false);
        listPasssengersFlightTextArea.setLineWrap(true);
        listPasssengersFlightTextArea.setWrapStyleWord(true);
        jScrollPane6.setViewportView(listPasssengersFlightTextArea);

        jLabel9.setText("Select passenger");

        jLabel10.setText("Selected passenger's flights");

        org.jdesktop.layout.GroupLayout listPassengersFlightsScreenLayout = new org.jdesktop.layout.GroupLayout(listPassengersFlightsScreen);
        listPassengersFlightsScreen.setLayout(listPassengersFlightsScreenLayout);
        listPassengersFlightsScreenLayout.setHorizontalGroup(
            listPassengersFlightsScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(listPassengersFlightsScreenLayout.createSequentialGroup()
                .addContainerGap()
                .add(listPassengersFlightsScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 251, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel9, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 110, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(26, 26, 26)
                .add(listPassengersFlightsScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 220, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jScrollPane6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 327, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
        );
        listPassengersFlightsScreenLayout.setVerticalGroup(
            listPassengersFlightsScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(listPassengersFlightsScreenLayout.createSequentialGroup()
                .add(30, 30, 30)
                .add(listPassengersFlightsScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel9)
                    .add(jLabel10))
                .add(6, 6, 6)
                .add(listPassengersFlightsScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 180, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jScrollPane5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 180, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
        );

        jTabbedPane1.addTab("List Passengers Flights", listPassengersFlightsScreen);

        bookFlightScreen.addComponentListener(new java.awt.event.ComponentAdapter()
        {
            public void componentShown(java.awt.event.ComponentEvent evt)
            {
                bookFlightScreenComponentShown(evt);
            }
        });

        bookFlightPassengerList.setModel(new javax.swing.AbstractListModel()
        {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        bookFlightPassengerList.setSelectionMode(0);
        bookFlightPassengerList.addListSelectionListener(new javax.swing.event.ListSelectionListener()
        {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt)
            {
                bookFlightPassengerListValueChanged(evt);
            }
        });
        jScrollPane8.setViewportView(bookFlightPassengerList);

        bookFlightFlightList.setModel(new javax.swing.AbstractListModel()
        {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        bookFlightFlightList.setSelectionMode(0);
        bookFlightFlightList.addListSelectionListener(new javax.swing.event.ListSelectionListener()
        {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt)
            {
                bookFlightFlightListValueChanged(evt);
            }
        });
        jScrollPane9.setViewportView(bookFlightFlightList);

        jLabel16.setText("Select passenger");

        bookFlightCapacityField.setEditable(false);
        bookFlightCapacityField.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bookFlightCapacityFieldActionPerformed(evt);
            }
        });

        jLabel18.setText("capacity:");

        bookFlightButton.setText("Book flight");
        bookFlightButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bookFlightButtonActionPerformed(evt);
            }
        });

        bookFlightOutcomeArea.setEditable(false);
        bookFlightOutcomeArea.setLineWrap(true);
        bookFlightOutcomeArea.setWrapStyleWord(true);
        bookFlightOutcomeArea.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel13.setText("Select flight");

        bookFlightDateField.setEditable(false);

        jLabel21.setText("date:");

        bookFlightDepartureField.setEditable(false);

        jLabel23.setText("departure airport:");

        jLabel17.setText("Selected flight details");

        bookFlightArrivalField.setEditable(false);
        bookFlightArrivalField.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bookFlightArrivalFieldActionPerformed(evt);
            }
        });

        jLabel24.setText("arrival airport:");

        org.jdesktop.layout.GroupLayout bookFlightScreenLayout = new org.jdesktop.layout.GroupLayout(bookFlightScreen);
        bookFlightScreen.setLayout(bookFlightScreenLayout);
        bookFlightScreenLayout.setHorizontalGroup(
            bookFlightScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(bookFlightScreenLayout.createSequentialGroup()
                .addContainerGap()
                .add(bookFlightScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(bookFlightScreenLayout.createSequentialGroup()
                        .add(bookFlightScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel16)
                            .add(jScrollPane8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 210, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(18, 18, 18)
                        .add(bookFlightScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jScrollPane9, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 121, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel13, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 97, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(bookFlightScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(bookFlightScreenLayout.createSequentialGroup()
                                .add(12, 12, 12)
                                .add(bookFlightScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                    .add(jLabel18)
                                    .add(jLabel21)
                                    .add(jLabel23)
                                    .add(jLabel24))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(bookFlightScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(bookFlightDepartureField)
                                    .add(bookFlightDateField)
                                    .add(bookFlightCapacityField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(bookFlightArrivalField)))
                            .add(jLabel17, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 160, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, bookFlightButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 136, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(bookFlightOutcomeArea, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 623, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(60, 60, 60))
        );
        bookFlightScreenLayout.setVerticalGroup(
            bookFlightScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(bookFlightScreenLayout.createSequentialGroup()
                .add(bookFlightScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(bookFlightScreenLayout.createSequentialGroup()
                        .add(33, 33, 33)
                        .add(bookFlightScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel16)
                            .add(jLabel13)))
                    .add(bookFlightScreenLayout.createSequentialGroup()
                        .add(25, 25, 25)
                        .add(jLabel17)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(bookFlightScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(bookFlightScreenLayout.createSequentialGroup()
                        .add(bookFlightScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jScrollPane9, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 180, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(bookFlightScreenLayout.createSequentialGroup()
                                .add(bookFlightScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                    .add(bookFlightCapacityField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jLabel18))
                                .add(50, 50, 50)
                                .add(bookFlightScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                    .add(bookFlightDateField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jLabel21))
                                .add(18, 18, 18)
                                .add(bookFlightScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                    .add(bookFlightDepartureField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jLabel23))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(bookFlightScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel24)
                                    .add(bookFlightArrivalField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 40, Short.MAX_VALUE)
                        .add(bookFlightButton)
                        .add(18, 18, 18))
                    .add(jScrollPane8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 180, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(bookFlightOutcomeArea, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 57, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Make Booking", bookFlightScreen);

        cancelBookingScreen.addComponentListener(new java.awt.event.ComponentAdapter()
        {
            public void componentShown(java.awt.event.ComponentEvent evt)
            {
                cancelBookingScreenComponentShown(evt);
            }
        });

        cancelBookingPassengerList.setModel(new javax.swing.AbstractListModel()
        {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        cancelBookingPassengerList.setSelectionMode(0);
        cancelBookingPassengerList.addListSelectionListener(new javax.swing.event.ListSelectionListener()
        {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt)
            {
                cancelBookingPassengerListValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(cancelBookingPassengerList);

        jLabel11.setText("Select passenger");

        cancelBookingButton.setText("Cancel booking");
        cancelBookingButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                cancelBookingButtonActionPerformed(evt);
            }
        });

        cancelBookingOutcomeArea.setEditable(false);
        cancelBookingOutcomeArea.setLineWrap(true);
        cancelBookingOutcomeArea.setWrapStyleWord(true);
        cancelBookingOutcomeArea.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        cancelBookingFlightList.setModel(new javax.swing.AbstractListModel()
        {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        cancelBookingFlightList.setSelectionMode(0);
        jScrollPane4.setViewportView(cancelBookingFlightList);

        jLabel12.setText("Select booked flight");

        org.jdesktop.layout.GroupLayout cancelBookingScreenLayout = new org.jdesktop.layout.GroupLayout(cancelBookingScreen);
        cancelBookingScreen.setLayout(cancelBookingScreenLayout);
        cancelBookingScreenLayout.setHorizontalGroup(
            cancelBookingScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(cancelBookingScreenLayout.createSequentialGroup()
                .add(cancelBookingScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(cancelBookingScreenLayout.createSequentialGroup()
                        .add(18, 18, 18)
                        .add(cancelBookingScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jScrollPane3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 211, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 180, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(31, 31, 31)
                        .add(cancelBookingScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jScrollPane4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 203, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel12, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 250, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(cancelBookingScreenLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(cancelBookingOutcomeArea))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, cancelBookingScreenLayout.createSequentialGroup()
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(cancelBookingButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 140, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        cancelBookingScreenLayout.setVerticalGroup(
            cancelBookingScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(cancelBookingScreenLayout.createSequentialGroup()
                .add(36, 36, 36)
                .add(cancelBookingScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel12)
                    .add(jLabel11))
                .add(6, 6, 6)
                .add(cancelBookingScreenLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 190, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jScrollPane3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 190, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 29, Short.MAX_VALUE)
                .add(cancelBookingButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(cancelBookingOutcomeArea, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 47, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Cancel Booking", cancelBookingScreen);

        getContentPane().add(jTabbedPane1);
        jTabbedPane1.setBounds(0, 0, 650, 410);

        setSize(new java.awt.Dimension(652, 434));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

   private void listPassengersFlightsScreenComponentShown(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_listPassengersFlightsScreenComponentShown
   {//GEN-HEADEREND:event_listPassengersFlightsScreenComponentShown
      displayPassengers1();
   }//GEN-LAST:event_listPassengersFlightsScreenComponentShown

   private void cancelBookingScreenComponentShown(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_cancelBookingScreenComponentShown
   {//GEN-HEADEREND:event_cancelBookingScreenComponentShown
      displayPassengers3();
      cancelBookingOutcomeArea.setText("");
   }//GEN-LAST:event_cancelBookingScreenComponentShown

   private void cancelBookingButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cancelBookingButtonActionPerformed
   {//GEN-HEADEREND:event_cancelBookingButtonActionPerformed
      if (!cancelBookingPassengerList.isSelectionEmpty() && !cancelBookingFlightList.isSelectionEmpty())
      {
         doCancelBooking();
      }
   }//GEN-LAST:event_cancelBookingButtonActionPerformed

   private void listPasssengersPassengerListValueChanged(javax.swing.event.ListSelectionEvent evt)//GEN-FIRST:event_listPasssengersPassengerListValueChanged
   {//GEN-HEADEREND:event_listPasssengersPassengerListValueChanged
      if (!listPasssengersPassengerList.getValueIsAdjusting())
      {
         if (!listPasssengersPassengerList.isSelectionEmpty())
         {
            displayFlightDetails1();
         }
      }
   }//GEN-LAST:event_listPasssengersPassengerListValueChanged

   private void addPassengerButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_addPassengerButtonActionPerformed
   {//GEN-HEADEREND:event_addPassengerButtonActionPerformed
      doAddPassenger();
   }//GEN-LAST:event_addPassengerButtonActionPerformed

   private void addPassengerScreenComponentShown(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_addPassengerScreenComponentShown
   {//GEN-HEADEREND:event_addPassengerScreenComponentShown
      displayPassengersDetails();
      resetPassengerFields();
      addPassengerOutcomeArea.setText("");
   }//GEN-LAST:event_addPassengerScreenComponentShown

   private void bookFlightPassengerListValueChanged(javax.swing.event.ListSelectionEvent evt)//GEN-FIRST:event_bookFlightPassengerListValueChanged
   {//GEN-HEADEREND:event_bookFlightPassengerListValueChanged
      if (!bookFlightPassengerList.getValueIsAdjusting())
      {
         if (!bookFlightPassengerList.isSelectionEmpty())
         {
            displayFlightsPassengerNotBookedOn();
         }
      }
   }//GEN-LAST:event_bookFlightPassengerListValueChanged

   private void bookFlightScreenComponentShown(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_bookFlightScreenComponentShown
   {//GEN-HEADEREND:event_bookFlightScreenComponentShown
      displayPassengers2();
      bookFlightOutcomeArea.setText("");
   }//GEN-LAST:event_bookFlightScreenComponentShown
 
   private void bookFlightFlightListValueChanged(javax.swing.event.ListSelectionEvent evt)//GEN-FIRST:event_bookFlightFlightListValueChanged
   {//GEN-HEADEREND:event_bookFlightFlightListValueChanged
      if (!bookFlightFlightList.getValueIsAdjusting())
      {
         if (!bookFlightFlightList.isSelectionEmpty())
         {
            displayFlightDetails2();
         }
      }
   }//GEN-LAST:event_bookFlightFlightListValueChanged

   private void bookFlightButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bookFlightButtonActionPerformed
   {//GEN-HEADEREND:event_bookFlightButtonActionPerformed
      if (!bookFlightPassengerList.isSelectionEmpty() && !bookFlightFlightList.isSelectionEmpty())
      {
         doBookFlight();
      }
      else
      {
         if (bookFlightFlightList.getModel().getSize() == 0)
         {
            bookFlightOutcomeArea.setText("There are no flights that this passenger can book");
         }
         else
         {
             bookFlightOutcomeArea.setText("You must select a passenger and a flight");
         }
      }
   }//GEN-LAST:event_bookFlightButtonActionPerformed

   private void cancelBookingPassengerListValueChanged(javax.swing.event.ListSelectionEvent evt)//GEN-FIRST:event_cancelBookingPassengerListValueChanged
   {//GEN-HEADEREND:event_cancelBookingPassengerListValueChanged
      if (!cancelBookingPassengerList.getValueIsAdjusting())
      {
         if (!cancelBookingPassengerList.isSelectionEmpty())
         {
            displayBookedFlights();
         }
      }
   }//GEN-LAST:event_cancelBookingPassengerListValueChanged

   private void bookFlightCapacityFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookFlightCapacityFieldActionPerformed
       // TODO add your handling code here:
   }//GEN-LAST:event_bookFlightCapacityFieldActionPerformed

    private void addFlightDepartureAirportsValueChanged(javax.swing.event.ListSelectionEvent evt)//GEN-FIRST:event_addFlightDepartureAirportsValueChanged
    {//GEN-HEADEREND:event_addFlightDepartureAirportsValueChanged
        displayArrivalAirports();
    }//GEN-LAST:event_addFlightDepartureAirportsValueChanged

    private void addFlightScreenComponentShown(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_addFlightScreenComponentShown
    {//GEN-HEADEREND:event_addFlightScreenComponentShown
        displayDepartureAirports();
        addFlightOutcomeArea.setText("");
        resetFlightFields();
    }//GEN-LAST:event_addFlightScreenComponentShown

    private void addFlightCapacityFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_addFlightCapacityFieldActionPerformed
    {//GEN-HEADEREND:event_addFlightCapacityFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addFlightCapacityFieldActionPerformed

    private void addFlightDateFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_addFlightDateFieldActionPerformed
    {//GEN-HEADEREND:event_addFlightDateFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addFlightDateFieldActionPerformed

    private void addFlightButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_addFlightButtonActionPerformed
    {//GEN-HEADEREND:event_addFlightButtonActionPerformed
       if(doAddFlight()== true)
       {
          resetFlightFields(); 
       }   
    }//GEN-LAST:event_addFlightButtonActionPerformed

    private void bookFlightArrivalFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bookFlightArrivalFieldActionPerformed
    {//GEN-HEADEREND:event_bookFlightArrivalFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bookFlightArrivalFieldActionPerformed

   /**
    * @param args the command line arguments
    */
   public static void main(String args[])
   {
      java.awt.EventQueue.invokeLater(new Runnable()
      {

         public void run()
         {
            new AirlineGUI().setVisible(true);
         }
      });
   }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private m256gui.M256JList addFlightArrivalAirports;
    private javax.swing.JButton addFlightButton;
    private javax.swing.JTextField addFlightCapacityField;
    private javax.swing.JTextField addFlightDateField;
    private m256gui.M256JList addFlightDepartureAirports;
    private javax.swing.JTextArea addFlightOutcomeArea;
    private javax.swing.JPanel addFlightScreen;
    private javax.swing.JButton addPassengerButton;
    private javax.swing.JTextField addPassengerFirstNameField;
    private javax.swing.JTextArea addPassengerOutcomeArea;
    private javax.swing.JTextField addPassengerPassportField;
    private javax.swing.JPanel addPassengerScreen;
    private javax.swing.JTextField addPassengerSurnameField;
    private javax.swing.JTextArea addPassengerTextArea;
    private javax.swing.JTextField addPassengerTitleField;
    private javax.swing.JTextField bookFlightArrivalField;
    private javax.swing.JButton bookFlightButton;
    private javax.swing.JTextField bookFlightCapacityField;
    private javax.swing.JTextField bookFlightDateField;
    private javax.swing.JTextField bookFlightDepartureField;
    private m256gui.M256JList bookFlightFlightList;
    private javax.swing.JTextArea bookFlightOutcomeArea;
    private m256gui.M256JList bookFlightPassengerList;
    private javax.swing.JPanel bookFlightScreen;
    private javax.swing.JButton cancelBookingButton;
    private m256gui.M256JList cancelBookingFlightList;
    private javax.swing.JTextArea cancelBookingOutcomeArea;
    private m256gui.M256JList cancelBookingPassengerList;
    private javax.swing.JPanel cancelBookingScreen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel listPassengersFlightsScreen;
    private javax.swing.JTextArea listPasssengersFlightTextArea;
    private m256gui.M256JList listPasssengersPassengerList;
    // End of variables declaration//GEN-END:variables
}
