package tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import entities.Data;
import entities.ProjectLibrary;
import entities.TaskLibrary;
import entities.UserLibrary;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;



public class ExportJSON {
    private final ProjectLibrary projectLibrary;
    private final TaskLibrary taskLibrary;
    private final UserLibrary userLibrary;

    public ExportJSON(ProjectLibrary projectLibrary, TaskLibrary taskLibrary, UserLibrary userLibrary) throws IOException {
        this.projectLibrary = projectLibrary;
        this.taskLibrary = taskLibrary;
        this.userLibrary = userLibrary;
        collectData();
        writeJSON();
    }

    public void writeJSON() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        String path = System.getProperty("user.home") + "/simpledirection.json";
        mapper.writerWithDefaultPrettyPrinter().writeValue(Paths.get(path).toFile(), this.projectLibrary.getDataList());
    }

    public List<Data> fetchAllData() {
        List filledList = new ArrayList<>();

        filledList.addAll(this.userLibrary.getAllUsers());
        filledList.addAll(this.projectLibrary.getDataList());

        //filledList.addAll(this.taskLibrary.getDataList());


        return(filledList);
    }

    // newline %x0A /
    // tab %x09 /

    private void collectData() {

        Object myItem = new Object();
        // for() {
        // User
        // userinfo (name, email etc.)
        // Project
        // Projectinfo (name, description etc.
        // Task
        // Taskinfo (name, description, assignees etc.)
        // Worklog
        // User + Workedhours
        // }
            
          /*  try {
                String serialized = new ObjectMapper().writeValueAsString(myItem);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } */
    }

    // User
    // userinfo (name, email etc.)
    // Project
    // Projectinfo (name, description etc.
    // Task
    // Taskinfo (name, description, assignees etc.)
    // Worklog
    // User + Workedhours

}
