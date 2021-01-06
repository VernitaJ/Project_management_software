package entities;

import achievements.AchievementTracker;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.ArrayList;

@JsonDeserialize(as = User.class)
@JsonPropertyOrder({ "userName", "password", "email", "occupation", "companyName", "salary", "workingHours", "inbox", "achievementTracker" })
public class User extends Data {

    private String userName;
    private String password;
    private String email;
    private String occupation;
    private String companyName;
    private float salary;
    private float workingHours;
    private ArrayList<Message> inbox;
    public AchievementTracker achievementTracker;

    public User(String userName, String password, String email, String occupation, String companyName, float salary, float workingHours) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.occupation = occupation;
        this.companyName = companyName;
        this.salary = salary;
        this.workingHours = workingHours;
        this.inbox = new ArrayList<>();
        this.achievementTracker = new AchievementTracker();
    }
    
    public User() {
        this.inbox = new ArrayList<>();
        this.achievementTracker = new AchievementTracker();
    }

    public void setName(String currentName) {
        this.userName = currentName;
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
        return this.email;
    }

    public String getOccupation() {
        return this.occupation;
    }

    public String getCompanyName() {
        return this.companyName;
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

    public void setEmail(String email) {
        this.email = email;
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
    
    public void setAchievementTracker(AchievementTracker achievementTracker) {
        this.achievementTracker = achievementTracker;
    }
    
    public void setInbox(ArrayList<Message> inbox) {
        this.inbox = inbox;
    }
    
    public void setID(String ID) {
        super.setID(ID);
    }
    
    @Override
    public String toString() {
        return getUserName();
    }

}
