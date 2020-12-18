package tools;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import entities.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;



public class ExportJSON {
    private final ProjectLibrary projectLibrary;
    private final TaskLibrary taskLibrary;
    private final UserLibrary userLibrary;
    private String path;

    public ExportJSON(ProjectLibrary projectLibrary, TaskLibrary taskLibrary, UserLibrary userLibrary) throws IOException {
        this.projectLibrary = projectLibrary;
        this.taskLibrary = taskLibrary;
        this.userLibrary = userLibrary;
        this.path = System.getProperty("user.home") + "/simpledirection.json";
        collectData();
        writeJSON();
    }

    public void writeJSON() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.writerWithDefaultPrettyPrinter().writeValue(Paths.get(this.path).toFile(), this.projectLibrary.getDataList());
    }

    public void readJSON() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //MyValue older = mapper.readValue(new File("my-older-stuff.json"), MyValue.class);

        // // Or if you prefer JSON Tree representation:
        //  JsonNode root = mapper.readTree(newState);
        //  // and find values by, for example, using a JsonPointer expression:
        //  int age = root.at("/personal/age").getValueAsInt();

        ArrayList<String> pogger = mapper.readValue(new File(this.path), ArrayList.class);
        for(int i = 0; i < pogger.size(); i++) {

        }
        System.out.println(pogger.toString());
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
