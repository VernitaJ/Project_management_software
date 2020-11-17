package entities;
//currently just for testing Data, not the final class
//please dont edit it yet
public class Project extends Data{
    public String dummyProjectOwner;
    public String dummyTask;

    public Project(String dummyOwner, String dummyTask){
        this.dummyProjectOwner=dummyOwner;
        this.dummyTask = dummyTask;
    }

    public void changeDummyOwner(String x){
        dummyProjectOwner=x;
    }
    public void changeTask(String task){
        dummyTask=task;
    }

    @Override
    public String toString() {
        return "Project ID: " + getID()+
                " DummyProjectOwner: " + dummyProjectOwner + " Task: "+ dummyTask ;
    }
}
