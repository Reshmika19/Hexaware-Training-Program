package entity;
public class Admin {
    private int adminId;
    private String adminName;
    private String password;

    public Admin() {
    }
    public Admin(int adminId, String adminName, String password) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.password = password;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString() {
        return "AdminId : " + adminId +
                ", AdminName : '" + adminName + '\'' +
                ", Password : '" + password + '\'';
    }
}
