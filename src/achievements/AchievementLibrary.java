package achievements;

import tools.Input;

import java.util.HashMap;

public class AchievementLibrary {

    private static AchievementLibrary instance = null;

    private AchievementLibrary(){}

    public static AchievementLibrary getInstance()
    {
        if (instance == null)
        {
            return new AchievementLibrary();
        }
        else
        {
            return instance;
        }
    }

    private  HashMap<String, Achievement> achievements;

    public Achievement getAchievement(String achievementName){
        return achievements.get(achievementName);
    }

    public int getAchievementRequirement(String achievementName){
        return achievements.get(achievementName).getRequiredPoints();
    }

    public boolean addNewAchievement(String achievementName, int requiredPoint){
        if(achievements.containsKey(achievementName)){
            return false;
        }
        else{
            achievements.put(achievementName, new Achievement(achievementName, requiredPoint));
        } return true;
    }


}
