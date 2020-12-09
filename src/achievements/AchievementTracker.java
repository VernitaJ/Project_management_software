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
        System.out.println(achievementName + " " + tracker.get(achievementName) + "/" + library.getAchievementRequirement(achievementName));
    }
}
