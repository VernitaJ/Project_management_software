package entities;
import components.Login;
import exceptions.IDAlreadyInUseException;
import java.util.ArrayList;

// If necessary, more methods can be added in the future

public class DataLibrary {
    private ArrayList<Data> list = new ArrayList<>();


    //Exception might not be necessary for UUIDs, just wanted to be ready for other types of IDs :P
    public void addToList(Data dataToAdd){
        if(doesItExist(dataToAdd.getID())){
            throw new IDAlreadyInUseException();
        }else {
            list.add(dataToAdd);
        }
    }

    public boolean doesItExist(String idToSearch){
        for (Data data : list) {
            if (data.getID().equals(idToSearch)) {
                return true;
            }
        }
        return false;
    }

    // check the return value if its null, it means data does not exist
    public Data findItInList(String idToSearch){
        for (Data data : list) {
            if (data.getID().equals(idToSearch)) {
                return data;
            }
        }
        return null;
    }

    //need to test it
    public boolean removeItFromList(String idToRemove) {
        return list.removeIf(data -> (data.checkID(idToRemove)));
    }

    //if all classes that extend Data can have a toString, this
    //method can be useful to print 'em all
    public String listAll(){
        if(list.size() < 1){
            return null;
        } else {
            StringBuilder listOfAll = new StringBuilder();
            for (Data data : list) {
                listOfAll.append(data.toString() + "\n");
            }
            return listOfAll.toString();
        }
    }





}

