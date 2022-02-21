package Model;
/** This class holds individual customer data.
 * @author Jessie Van Epps*/
public class Customer {
    private int id;
    private String custName;
    private String address;
    private String zipCode;
    private String phoneNum;
    private String country;
    private String division;

    /** This creates a customer. This is used to construct a customer with the given data.
     * @param id The id of a customer.
     * @param custName The name of a customer
     * @param address The adress of a customer.
     * @param country country of a customer
     * @param division The division of a customer
     * @param zipCode The zip of a customer.
     * @param phoneNum The phone number of a customer.*/
    public Customer(int id, String custName, String address, String country, String division, String zipCode, String phoneNum){
        this.id = id;
        this.custName = custName;
        this.address = address;
        this.zipCode = zipCode;
        this.phoneNum = phoneNum;
        this.country = country;
        this.division = division;

    }
    //Returns id
    /** Gets the ID of a customer. Returns the ID of a customer.
     * @return id The int ID of a customer.*/
    public int getId() {return id;}

    //Returns first name
    /** Gets the name of a customer. Returns a string name of a customer.
     * @return custName The string of a customer name.*/
    public String getCustName(){
        return custName;
    }

    //Sets first name
    /** This method sets the name of a customer. This method is used to set the name of a customer.
     * @param first The string of a name of a customer to be set.*/
    public void setCustName(String first){
        this.custName = first;
    }

    //Returns address
    /** This method gets the address of a customer. This method is used to return the address of a customer.
     * @return address the String address of a customer.*/
    public String getAddress(){
        return address;
    }

    //Sets address
    /** This method sets the address of a customer. This method is used to set the address of a customer.
     * @param addy The string address of a customer.*/
    public void setAddress(String addy){
        this.address = addy;
    }

    //Returns zip
    /** This method returns the zip code of a customer. This method is used to get the zip code of a customer.
     * @return zipCode The string zip code of a customer.*/
    public String getZipCode(){
        return zipCode;
    }

    //Sets zip
    /** This method sets the zip code of a customer. This method is used to set the zip code of a customer.
     * @param z The zip code of a customer.*/
    public void setZipCode(String z){
        this.zipCode = z;
    }

    //Returns phone
    /** This method gets the phone number of a customer. This method is used to return the phoneNum of a customer.
     * @return phoneNum The phone number string of a customer.*/
    public String getPhoneNum(){
        return phoneNum;
    }

    //Sets phone
    /** This method sets the phone number of a customer. This method is used to set the phone number of a customer.
     * @param p The phone number of a customer to be set.*/
    public void setPhoneNum(String p){
        this.phoneNum = p;
    }

    //Returns country
    /** This method gets the country of a customer. This method is used to return the country of a customer.
     * @return country The country of a customer.*/
    public String getCountry(){ return country; }

    //Sets country
    /** This method is used to set the country of a customer. This method sets the country of customer.
     * @param c The string country of a customer to be set.*/
    public void setCountry(String c){ this.country = c; }

    //Returns division
    /** This method gets the division of a customer. This method is used to return the division information of a customer.
     * @return division The string division of a customer.*/
    public String getDivision(){ return division; }

    //Sets division
    /** This method sets the division of a customer. This method is used to set the division of a customer.
     * @param d The division of a customer.*/
    public void setDivision(String d){ this.division = d; }

}
