package tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import entities.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;



public class ExportJSON {
    private final ProjectLibrary projectLibrary;
    private final TaskLibrary taskLibrary;
    private final UserLibrary userLibrary;
    private final ObjectMapper MAPPER;
    private String path;

    public ExportJSON(ProjectLibrary projectLibrary, TaskLibrary taskLibrary, UserLibrary userLibrary) throws IOException {
        this.projectLibrary = projectLibrary;
        this.taskLibrary = taskLibrary;
        this.userLibrary = userLibrary;
        this.MAPPER = new ObjectMapper();
        this.MAPPER.registerModule(new JavaTimeModule());
        this.MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.path = System.getProperty("user.home") + "/simpledirection.json";

        collectData();
        writeJSON();
        readJSON();
    }

    public void writeJSON() throws IOException {
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(Paths.get(this.path).toFile(), this.projectLibrary.getDataList());
    }

    public void readJSON() throws IOException {
        //ArrayList<String> pogger = MAPPER.readValue(new File(this.path), ArrayList.class);
        // project = MAPPER.readValue(new File(this.path), Project.class);
        this.projectLibrary.addProjectToList(MAPPER.readValue(new File(this.path), Project.class));
        //System.out.println(project.toString());
        //for(int i = 0; i < pogger.size(); i++) {

        //}
        //System.out.println(pogger.toString());
    }















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
