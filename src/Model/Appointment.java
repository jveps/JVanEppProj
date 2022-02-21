package Model;
/** This is the class that stores appointment data.
 * @author Jessie Van Epps*/
public class Appointment {
    private String appointmentId;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private String startDateTime;
    private String endDateTime;
    private int customerId;
    private int userId;

    /** This is the constructor for an appointment. Creates an appointment.
     * @param aptId The Appointment ID
     * @param title The appointment title
     * @param desc The appointment description
     * @param loc The location of an appointment
     * @param type The type of appointment
     * @param sDateTime The start date and time of the appointment
     * @param eDateTime The end date and time of an appointment
     * @param cId The customer ID of the appointment
     * @param uId The user ID of the appointment.*/
    public Appointment(String aptId, String title, String desc, String loc, String contact, String type, String sDateTime, String eDateTime, int cId, int uId){
        this.appointmentId = aptId;
        this.title = title;
        this.description = desc;
        this.location = loc;
        this.contact = contact;
        this.type = type;
        this.startDateTime = sDateTime;
        this.endDateTime = eDateTime;
        this.customerId = cId;
        this.userId = uId;
    }

    /** Gets the appointment ID. Returns the appointmentID of an appointment.
     * @return appointmentId a String containing the appointment ID*/
    public String getAppointmentId(){
        return appointmentId;
    }

    /** This method sets the appointment ID of an appointment. This method is used to set an appointments ID.
     * @param a The string containing an appointment ID*/
    public void setAppointmentId(String a) {
        this.appointmentId = a;
    }

    /** This method gets the title of an appointment. This method is used to return the title of an appointment
     * @return title A string containing the title of an appointment.*/
    public String getTitle(){
        return title;
    }

    /** This method sets the title of an appointment. This method is used to set the title of an appointment.
     * @param t A string containing the title of an appointment.*/
    public void setTitle(String t){
        this.title = t;
    }

    /** This method gets the description of an appointment. This method returns the description of an appointment.
     * @return description The string description of an appointment.*/
    public String getDescription(){
        return description;
    }

    /** This method sets an appointment description. This method is used to set the description of an appointment.
     * @param d A string containing the description of an appointment.*/
    public void setDescription(String d){
        this.description = d;
    }

    /** This method gets the location of an appointment. This method is returns the location of an appointment.
     * @return location The string location of an appointment.*/
    public String getLocation(){
        return location;
    }

    /** This method sets the location of an appointment. This method is used to set the location of an appointment.
     * @param l The string location of an appointment.*/
    public void setLocation(String l){
        this.location = l;
    }

    /** This method gets the contact of an appointment. This method returns the contact id of an appointment.
     * @return contact Returns the string id of a contact from the appointment.*/
    public String getContact(){
        return contact;
    }

    /** This method sets the appointment contact. This method is used to set the contact of an appointment.
     * @param c The contact ID to be set.*/
    public void setContact(String c){
        this.contact = c;
    }

    /** This method gets the type of an appointment. This method returns the type of an appointment.
     * @return type The type string of an appointment.*/
    public String getType(){
        return type;
    }

    /** This method sets the type of appointment. This method is used to set the type of an appointment.
     * @param t A string type of appointment.*/
    public void setType(String t){
        this.type = t;
    }

    /** This method gets the start date and time of an appointment. This method returns the start date and time of an appointment.
     * @return startDateTime The string of a start date and time of appointment.*/
    public String getStartDateTime(){
        return startDateTime;
    }

    /** This method sets the start date and time of an appointment. This method is used to set the start date and time of an appointment.
     * @param sDT A string start date and time of an appointment.*/
    public void setStartDateTime(String sDT){
        this.startDateTime = sDT;
    }

    //Returns Appointment end date and time
    /** This method gets the end date and time of an appointment. This method returns a string of the end date and time of an appointment.
     * @return endDateTime The end date and time of an appointment.*/
    public String getEndDateTime(){
        return endDateTime;
    }

    //Sets Appointment end date and time
    /** This method sets the end date and time of an appointment. This method is used to set the end date and time of an appointment.
     * @param eDT The end date and time of an appointment to be set.*/
    public void setEndDateTime(String eDT){
        this.endDateTime = eDT;
    }

    //Gets customer ID
    /** This method gets the customer ID of an appointment. This method returns the customer ID of an appointment.
     * @return customerId The customer ID associated with an appointment.*/
    public int getCustomerId(){
        return customerId;
    }

    //Set customer ID
    /** This method sets the customer ID of an appointment. This method is used to set the customer ID of an appointment.
     * @param c The customer ID of an appointment to be set.*/
    public void setCustomerId(int c){
        this.customerId = c;
    }

    //Gets user ID
    /** This method gets the user ID of an appointment. This method returns the user ID associated with an appointment.
     * @return userId The userID associated with an appointment.*/
    public int getUserId(){
        return userId;
    }

    //Sets user ID
    /** This method sets the user ID of an appointment. This method is used to set the User ID of an appointment.
     * @param i The userID of an appointment to be set.*/
    public void setUserId(int i){
        this.userId = i;
    }

}
