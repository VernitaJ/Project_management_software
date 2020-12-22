package budget;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class Budget
{
    private double money;
    private double hours;
    @JsonIgnoreProperties
    public final String CURRENCY = "SEK";
    
    public Budget(){
        this.money = 0.0;
        this.hours = 0.0;
    }
    
    public Budget(double money, double hours){
        this.money = money;
        this.hours = hours;
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
    
    public boolean budgetExistMoney() {
        if(this.getMoney() > 0) {
            return true;
        }
        return false;
    }
    public boolean budgetExistHours() {
        if(this.getHours() > 0) {
            return true;
        }
        return false;
    }
}
