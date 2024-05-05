package JVF.loginui;

import com.example.DataSingleton;

public class User {
    private final String email;
    private final String password;
    private final String firstname;
    private final String lastname;
    private DatabaseManager databaseManager;


    // Constructor, getters, and setters
    public User(String email, String password, String firstname, String lastname) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;

        this.databaseManager = new DatabaseManager();


        }



    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getFirstname() { return firstname; }
    public String getLastname() { return lastname; }

}