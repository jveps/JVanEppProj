package Model;

public class Customer {
    private String first_name;
    private String last_name;
    private String address;
    private int zipCode;
    private int phoneNum;

    public Customer(String first_name, String last_name, String address, int zipCode, int phoneNum){
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.zipCode = zipCode;
        this.phoneNum = phoneNum;

    }
    //Returns first name
    public String getFirstName(){
        return first_name;
    }

    //Sets first name
    public void setFirstName(String first){
        this.first_name = first;
    }

    //Returns last name
    public String getLastName(){
        return last_name;
    }

    //Sets last name
    public void setLastName(String last){
        this.last_name = last;
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
    public int getZip(){
        return zipCode;
    }

    //Sets zip
    public void setZip(int z){
        this.zipCode = z;
    }

    //Returns phone
    public int getPhoneNum(){
        return phoneNum;
    }

    //Sets phone
    public void setPhoneNum(int p){
        this.phoneNum = p;
    }

}
