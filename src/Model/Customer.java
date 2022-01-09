package Model;

public class Customer {
    private int id;
    private String custName;
    private String address;
    private String zipCode;
    private String phoneNum;

    public Customer(int id, String custName, String address, String zipCode, String phoneNum){
        this.id = id;
        this.custName = custName;
        this.address = address;
        this.zipCode = zipCode;
        this.phoneNum = phoneNum;

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

}
