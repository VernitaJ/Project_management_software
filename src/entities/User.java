package entities;

import achievements.AchievementTracker;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;

@JsonDeserialize(as = User.class)
public class User extends Data {

    private String userName;
    private String password;
    private String eMail;
    private String occupation;
    private String companyName;
    private float salary;
    private float workingHours;
    private ArrayList<Message> inbox;
    public AchievementTracker achievementTracker;
    private int experience;


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
        this.achievementTracker = new AchievementTracker(this);
    }
    
    public User() {
    
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
    
    public void setExperience(int experience) {
        this.experience = experience;
    }
    
    public void setAchievementTracker(AchievementTracker achievementTracker) {
        this.achievementTracker = achievementTracker;
    }
    
    public void setInbox(ArrayList<Message> inbox) {
        this.inbox = inbox;
    }
    
    public void addExp(int xp)
    {
        experience += xp;
    }
    
    public int getExperience() {
        return experience;
    }
    
    public void setID(String ID) {
        super.setID(ID);
    }
    
    @Override
    public String toString() {
        return getUserName();
    }
    
    public void printXpBar()
    {
        String box = "#";
        String empty = "_";
        int progress = experience%10;
        int remaining = 10-progress;
        System.out.println("Level: " + getExperience()/10);
        System.out.println("[" + box.repeat(progress*2) + empty.repeat(remaining*2) + "]" + " Progress: " + progress*10 + "%");
    }

    public String printTag(){
        return " [Level " + getExperience()/10 + " - " + achievementTracker.printNumOfUserAchievements() + " Achievements]";
    }

}
