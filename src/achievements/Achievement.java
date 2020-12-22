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

    public Achievement(){

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
