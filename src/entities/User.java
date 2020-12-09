package entities;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class User extends Data {

   // private Roles role;
    private String userName;
    private String password;
    private String eMail;
    private int experience;
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
        this.experience = 0;
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

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public void setWorkingHours(float workingHours) {
        this.workingHours = workingHours;
    }

    public void addExp(int xp)
    {
        experience += xp;
    }

    public int getLevel()
    {
        return experience/10;
    }

    public void getXpBar()
    {
        String box = "#";
        String empty = "_";
        int progress = experience%10;
        int remaining = 10-progress;
        System.out.println("Level: " + getLevel());
        System.out.println("[" + box.repeat(progress*2) + empty.repeat(remaining*2) + "]" + " Progress: " + progress*10 + "%");
    }
    /* public Roles getRole() {
        return role;
    }

    */

  //  public void setRole(Roles role) {
    //    this.role = role; }

}
