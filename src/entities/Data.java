package entities;
import tools.UniqueID;

public abstract class Data {
    private String ID;

    Data(){
        ID = UniqueID.generateID();
    }

    public String getID(){
        return ID;
    }

    Boolean checkID(String ID){
        if(this.ID.equals(ID)){
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return "ID: " + ID;
    }

    public String printTaskInfo(Task task) {
        return(null);
    }
}


