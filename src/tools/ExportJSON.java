package tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.ProjectLibrary;
import entities.TaskLibrary;
import entities.UserLibrary;
import java.io.IOException;
import java.nio.file.Paths;


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
        String path = System.getProperty("user.home") + "/simpledirection.json";
        mapper.writerWithDefaultPrettyPrinter().writeValue(Paths.get(path).toFile(), this.userLibrary.getAllUsers());
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
