package entities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import tools.DataDeserializer;
import tools.UniqueID;

@JsonDeserialize(using = DataDeserializer.class)
public abstract class Data {
    
    @JsonIgnoreProperties
    private String ID;

    Data(){
        ID = UniqueID.generateID();
    }

    public String getID(){
        return ID;
    }

    protected void setID(String ID) {
        this.ID = ID;
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


