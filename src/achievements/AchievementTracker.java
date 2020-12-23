package achievements;

import entities.Message;
import entities.User;

import java.util.ArrayList;
import java.util.HashMap;

public class AchievementTracker {
    private AchievementLibrary library = AchievementLibrary.getInstance();
    private HashMap<String, Integer> tracker;
    private int experience;
    
    public AchievementTracker(){
        tracker = new HashMap<>();
        this.experience = 0;
    }
    
    public HashMap<String, Integer> getTracker() {
        return tracker;
    }
    
    public void setTracker(HashMap<String, Integer> tracker) {
        this.tracker = tracker;
    }
    
    public int addPoints(String achievementName, int point, User user){
        int total = tracker.containsKey(achievementName) ? tracker.get(achievementName) : 0;
        total += point;
        tracker.put(achievementName, total);
        //send a message if user earned an achievement
        int progress = tracker.get(achievementName) - (listCurrentTier(achievementName) * library.getAchievementRequirement(achievementName));
        int required = library.getAchievementRequirement(achievementName)-progress;
        if(required == library.getAchievementRequirement(achievementName) &&
            listCurrentTier(achievementName) <= library.getAchievementMaxTier(achievementName) ){
            sendCongratsMessage(achievementName, user);
            addExp(library.getAchievementRequirement(achievementName));
        }
        return total;
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
        return " [Level " + getExperience()/10 + " - " + printNumOfUserAchievements() + " Achievements]";
    }
    
    public void addExp(int xp)
    {
        experience += xp;
    }
    
    public int getExperience() {
        return experience;
    }
    
    public void setExperience(int experience) {
        this.experience = experience;
    }
    
    private int listCurrentTier(String achievement){
        int currentPoints = tracker.get(achievement);
        int requiredPoints = library.getAchievement(achievement).getRequiredPoints();
        return currentPoints/requiredPoints;
    }
    
    public ArrayList<String> listUserAchievements(){
        ArrayList<String> accomplishedOnes = new ArrayList<>();
        for(String achievementName : tracker.keySet()){

            //if user have enough points to have this achievement, add name to return list;
            if(tracker.get(achievementName) >= library.getAchievementRequirement(achievementName)){
                accomplishedOnes.add(achievementName);
            }
        }
        return accomplishedOnes;
    }

    // public int getNumOfUserAchivements() {
    public int printNumOfUserAchievements(){
        int totalAchievements = 0;
        for(String achievementName : tracker.keySet()){
            //if user have enough points to have this achievement, add name to return list;
            if(tracker.get(achievementName) >= library.getAchievementRequirement(achievementName)){
                totalAchievements++;
            }
        }
        return totalAchievements;
    }

    private String getAchievementStatus(String achievementName){
        if(!tracker.containsKey(achievementName)){
            return "";
        }
        int currentTier = listCurrentTier(achievementName);
        String tier;
        if(currentTier >= library.getAchievementMaxTier(achievementName)){
            tier = "Tier Max";
        } else {
            tier = "Tier " + currentTier;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(library.getAchievementTitle(achievementName)+ " - " + tier);
        builder.append(System.getProperty("line.separator"));
        builder.append(library.getAchievementDescription(achievementName));
        builder.append(System.getProperty("line.separator"));
        if(!tier.equalsIgnoreCase("Tier Max")){
            int progress = tracker.get(achievementName) - (currentTier* library.getAchievementRequirement(achievementName));
            int required = library.getAchievementRequirement(achievementName)-progress;
            builder.append(required + " more points required to achieve next tier");
        }
        //just the new line
        builder.append(System.getProperty("line.separator"));
        return  builder.toString();

    }

    public void printUserAchievements(){
        ArrayList<String> accomplishedOnes = listUserAchievements();
        for(String achievement : accomplishedOnes){
            System.out.println(getAchievementStatus(achievement));

        }
    }
    private void sendNotification(User userToNotify, String message) {
        userToNotify.getInbox().add(new Message("System", userToNotify.getUserName(), message));
    }

    private void sendCongratsMessage(String achievementName, User user){
        StringBuilder builder = new StringBuilder();
        builder.append("Congratulations");
        builder.append(System.getProperty("line.separator"));
        builder.append("Dear " + user.getUserName() + ",");
        builder.append(System.getProperty("line.separator"));
        builder.append("We are happy to report that you have earned a new achievement.");
        builder.append(System.getProperty("line.separator"));
        builder.append(getAchievementStatus(achievementName));
        sendNotification(user, builder.toString());
    }





}
