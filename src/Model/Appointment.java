package Model;

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

    //Gets appointment ID
    public String getAppointmentId(){
        return appointmentId;
    }

    //Set appointment ID
    public void setAppointmentId(String a) {
        this.appointmentId = a;
    }

    //Gets appointment title
    public String getTitle(){
        return title;
    }

    //Sets appointment title
    public void setTitle(String t){
        this.title = t;
    }

    //Get appointment description
    public String getDescription(){
        return description;
    }

    //Set appointment description
    public void setDescription(String d){
        this.description = d;
    }

    //Get appointment location
    public String getLocation(){
        return location;
    }

    //Set appointment location
    public void setLocation(String l){
        this.location = l;
    }

    //Return appointment contact
    public String getContact(){
        return contact;
    }

    //Set appointment contact
    public void setContact(String c){
        this.contact = c;
    }

    //Return appointment type
    public String getType(){
        return type;
    }

    //Set appointment type
    public void setType(String t){
        this.type = t;
    }

    //Returns Appointment start date and time
    public String getStartDateTime(){
        return startDateTime;
    }

    //Sets Appointment start date and time
    public void setStartDateTime(String sDT){
        this.startDateTime = sDT;
    }

    //Returns Appointment end date and time
    public String getEndDateTime(){
        return endDateTime;
    }

    //Sets Appointment end date and time
    public void setEndDateTime(String eDT){
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
