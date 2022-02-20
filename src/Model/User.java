package Model;

public class User {
    private static String username;
    private static String id;

    public User(String name, String uID){
        this.username = name;
        this.id = uID;
    }

    public static String getUsername(){
        return username;
    }

    public static String getID(){
        return id;
    }
}
