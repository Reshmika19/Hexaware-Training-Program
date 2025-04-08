package entity;

public class UserC {
    private int userID;  // Change userID type to long
    private String userName;
    private String email;
    private String password;
    private String contactNumber;
    private String address;

    // Default constructor
    public UserC() {}

    // Parametrized constructor
    public UserC(int userID, String userName, String email, String password, String contactNumber, String address) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.contactNumber = contactNumber;
        this.address = address;
    }

    // Getter and Setter methods
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // toString method to display user details
    @Override
    public String toString() {
        return "userID : " + userID +
                ", userName : " + userName + '\'' +
                ", email : " + email + '\'' +
                ", password : " + password + '\'' +
                ", contactNumber :" + contactNumber + '\'' +
                ", address : " + address ;
    }
}
