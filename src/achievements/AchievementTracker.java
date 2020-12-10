package achievements;

import java.util.ArrayList;
import java.util.HashMap;

public class AchievementTracker {
    private AchievementLibrary library = AchievementLibrary.getInstance();
    private HashMap<String, Integer> tracker;

    public int addPoints(String achievementName, int point){
        int total = tracker.containsKey(achievementName) ? tracker.get(achievementName) : 0;
        total += point;
        tracker.put(achievementName, total);
        return total;
    }
    public int getCurrentTier(String achievement){
        int currentPoints = tracker.get(achievement);
        int requiredPoints = library.getAchievement(achievement).getRequiredPoints();
        return currentPoints/requiredPoints;
    }

    public ArrayList<String> getUserAchievements(){
        ArrayList<String> accomplishedOnes = new ArrayList<>();
        for(String achievementName : tracker.keySet()){

            //if user have enough points to have this achievement, add name to return list;
            if(tracker.get(achievementName) >= library.getAchievementRequirement(achievementName)){
                accomplishedOnes.add(achievementName);
            }
        }
        return accomplishedOnes;
    }

    public void printAchievementStatus(String achievementName){
        if(!tracker.containsKey(achievementName)){
            return;
        }
        int currentTier = getCurrentTier(achievementName);
        String tier;
        if(currentTier >= library.getAchievementMaxTier(achievementName)){
            tier = "Tier Max";
        } else {
            tier = "Tier" + currentTier;
        }
        System.out.println(library.getAchievementTitle(achievementName)+ " - " + tier);
        System.out.println(library.getAchievementDescription(achievementName));
        if(!tier.equalsIgnoreCase("Tier Max")){
            int progress = tracker.get(achievementName) - (currentTier* library.getAchievementRequirement(achievementName));
            System.out.println(progress + " points more required to achieve next tier");
        }

    }

    public void printUserAchievements(){
        ArrayList<String> accomplishedOnes = getUserAchievements();
        for(String achievement : accomplishedOnes){
            printAchievementStatus(achievement);
        }
    }





}
