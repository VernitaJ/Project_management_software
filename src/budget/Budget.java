package budget;

import entities.Project;

public class Budget
{
    private double money;
    private double hours;
    public final String CURRENCY = "SEK";

    public Budget(){
        this.money = 0.0;
        this.hours = 0.0;
    }

    public Budget(double money, double hours){
        this.money = money;
        this.hours = hours;
    }

    public void calculation(Project currentProject){
        if (currentProject.getBudget().getMoney() != 0) {
        } else System.out.println("soz, no budget has been added. chat to PM");
    }

    public float timeLeftBeforeExceedingBudget()
    {
        return 0.0f;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }
}
