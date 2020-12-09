package entities;

import java.util.ArrayList;

public class User extends Data {

   // private Roles role;
    private String userName;
    private String password;
    private String eMail;
    private String occupation;
    private String companyName;
    private float salary;
    private float workingHours;
    private ArrayList<Message> inbox;

    public User(String userName, String password, String eMail, String occupation, String companyName, float salary, float workingHours) {
        this.userName = userName;
        this.password = password;
        this.eMail = eMail;
        this.occupation = occupation;
        this.companyName = companyName;
        this.salary = salary;
        this.workingHours = workingHours;
        this.inbox = new ArrayList<>();
    }

    public void Profile() {
        System.out.println("Print here with " + userName);
        this.userName = userName;
        this.eMail = eMail;
        this.occupation = occupation;
        this.companyName = companyName;
        getUserName();
        getEmail();
        getOccupation();
        getCompanyName();
    }


    public ArrayList<Message> getInbox() {
        return inbox;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.eMail;
    }

    public String getOccupation() {
        return this.occupation;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public String geteMail() {
        return eMail;
    }

    public float getSalary() {
        return salary;
    }

    public float getWorkingHours() {
        return workingHours;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String eMail) {
        this.eMail = eMail;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public void setWorkingHours(float workingHours) {
        this.workingHours = workingHours;
    }

    /* public Roles getRole() {
        return role;
    }

    */

  //  public void setRole(Roles role) {
    //    this.role = role; }

}
