package entities;

import tools.Input;

public class TaskLibrary extends DataLibrary{

    public void createTask(User createdBy){
        Input input = Input.getInstance();
        String name = input.getStr("Task Name: ");
        String description = input.getStr("Task Description: ");
        addToList(new Task(createdBy, name, description));
    }

    /*public void deleteTask(User currentUser, String taskIDToRemove){
        if(accesslevel>3)
            removeItFromList(taskIDToRemove);
    }*/


    //public void importTask(){}

}


