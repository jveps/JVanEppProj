package Model;
/** This method stores user data.
 * @author Jessie Van Epps*/
public class User {
    private static String username;
    private static String id;

    /** This method creates a user object. This method is used to create a user object.
     * @param name The name of a user
     * @param uID the ID of a user.*/
    public User(String name, String uID){
        this.username = name;
        this.id = uID;
    }

    /** This method gets the username of a user. This method returns the username of a user.
     * @return username The username of a user.*/
    public static String getUsername(){
        return username;
    }

    /** This method gets the ID of a user. This method returns the ID of a user.
     * @return id The ID of a user.*/
    public static String getID(){
        return id;
    }
}
