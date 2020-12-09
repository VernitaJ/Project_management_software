package achievements;

public class Achievement {
    private String name;
    private int requiredPoints;
    //private int level;

    public Achievement(String name, int requiredPoints){
        this.name = name;
        this.requiredPoints = requiredPoints;
    }

    public int getRequiredPoints() {
        return requiredPoints;
    }

    public String getName() {
        return name;
    }
}
