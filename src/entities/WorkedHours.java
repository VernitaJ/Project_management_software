package entities;

import java.time.LocalDate;
// 0.50 means 30minutes for workedHours
public class WorkedHours {
    private LocalDate logTime;
    private User user;
    private double workedHours;

    public WorkedHours(User user, double workedHours){
        LocalDate logTime=LocalDate.now();
        this.workedHours=workedHours;
    }

    public User getUser() {
        return user;
    }

    public double getWorkedHours() {
        return workedHours;
    }

    public LocalDate getLogTime() {
        return logTime;
    }
}
