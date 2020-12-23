package achievements;

public class Achievement {
    //name should be unique
    private String name;
    //title is the text we can print to user
    private String title;
    private String description;
    private int requiredPoints;
    private int maxTier;

    public Achievement(String name, String title, String description, int requiredPoints, int maxTier){
        this.name = name;
        this.requiredPoints = requiredPoints;
        this.title = title;
        this.description = description;
        this.maxTier=maxTier;
    }

    public Achievement(String name){
        defaultAchievements(name);
    }

    private void defaultAchievements(String name) {
        if(name == null) {
            throw new NullPointerException("This is not a default achievement! To add this achievement, locate Achievement:24");
        } else {
            if(name.equals("sendMessage")) {
                this.name = name;
                this.title = "Sender of many messages";
                this.description = "Send some messages to earn this achievement";
                this.requiredPoints = 25;
                this.maxTier= 3;
            } else if (name.equals("createProject")) {
                this.name = name;
                this.title = "Good idea?";
                this.description = "Create some projects to earn this achievement";
                this.requiredPoints = 5;
                this.maxTier= 3;
            } else if (name.equals("deleteProject")) {
                this.name = name;
                this.title = "Maybe not so good of an idea...";
                this.description = "Delete some of your projects to earn this achievement";
                this.requiredPoints = 3;
                this.maxTier= 3;
            } else if (name.equals("createTask")) {
                this.name = name;
                this.title = "So you think your a supervisor now?";
                this.description = "Create some tasks in a project to earn this achievement";
                this.requiredPoints = 10;
                this.maxTier= 3;
            }
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRequiredPoints(int requiredPoints) {
        this.requiredPoints = requiredPoints;
    }

    public void setMaxTier(int maxTier) {
        this.maxTier = maxTier;
    }

    public int getRequiredPoints() {
        return requiredPoints;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public int getMaxTier() {
        return maxTier;
    }

}
