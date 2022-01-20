package Model;

public class Customer {
    private int id;
    private String custName;
    private String address;
    private String zipCode;
    private String phoneNum;
    private String country;
    private String division;

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
    public int getId() {return id;}

    //Returns first name
    public String getCustName(){
        return custName;
    }

    //Sets first name
    public void setCustName(String first){
        this.custName = first;
    }

    //Returns address
    public String getAddress(){
        return address;
    }

    //Sets address
    public void setAddress(String addy){
        this.address = addy;
    }

    //Returns zip
    public String getZipCode(){
        return zipCode;
    }

    //Sets zip
    public void setZipCode(String z){
        this.zipCode = z;
    }

    //Returns phone
    public String getPhoneNum(){
        return phoneNum;
    }

    //Sets phone
    public void setPhoneNum(String p){
        this.phoneNum = p;
    }

    //Returns country
    public String getCountry(){ return country; }

    //Sets country
    public void setCountry(String c){ this.country = c; }

    //Returns division
    public String getDivision(){ return division; }

    //Sets division
    public void setDivision(String d){ this.division = d; }

}
