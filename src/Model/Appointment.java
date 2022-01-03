package Model;

public class Appointment {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private String startDateTime;
    private String endDateTime;
    private int customerId;
    private int userId;

    public Appointment(int aptId, String title, String desc, String loc, String contact, String type, String sDateTime, String eDateTime, int cId, int uId){
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

    //Gets appointment ID
    public int getAppointmentId(){
        return appointmentId;
    }

    //Set appointment ID
    public void setAppointmentId(int a){
        this.appointmentId = a;
    }

    //Gets appointment title
    public String getAppointmentTitle(){
        return title;
    }

    //Sets appointment title
    public void setAppointmentTitle(String t){
        this.title = t;
    }

    //Get appointment description
    public String getAppointmentDescription(){
        return description;
    }

    //Set appointment description
    public void setAppointmentDescription(String d){
        this.description = d;
    }

    //Get appointment location
    public String getAppointmentLocation(){
        return location;
    }

    //Set appointment location
    public void setAppointmentLocation(String l){
        this.location = l;
    }

    //Return appointment contact
    public String getAppointmentContact(){
        return contact;
    }

    //Set appointment contact
    public void setAppointmentContact(String c){
        this.contact = c;
    }

    //Return appointment type
    public String getAppointmentType(){
        return type;
    }

    //Set appointment type
    public void setAppointmentType(String t){
        this.type = t;
    }

    //Returns Appointment start date and time
    public String getAppointmentStartDateTime(){
        return startDateTime;
    }

    //Sets Appointment start date and time
    public void setAppointmentStartDateTime(String sDT){
        this.startDateTime = sDT;
    }

    //Returns Appointment end date and time
    public String getAppointmentEndDateTime(){
        return endDateTime;
    }

    //Sets Appointment end date and time
    public void setAppointmentEndDateTime(String eDT){
        this.endDateTime = eDT;
    }

    //Gets customer ID
    public int getCustomerId(){
        return customerId;
    }

    //Set customer ID
    public void setCustomerId(int c){
        this.customerId = c;
    }

    //Gets user ID
    public int getUserId(){
        return userId;
    }

    //Sets user ID
    public void setUserId(int i){
        this.userId = i;
    }

}
