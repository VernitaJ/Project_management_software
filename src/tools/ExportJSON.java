package tools;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import entities.ProjectLibrary;
import entities.TaskLibrary;
import entities.UserLibrary;

import java.io.File;
import java.io.IOException;


public class ExportJSON {
   private ProjectLibrary projectLibrary;
    private TaskLibrary taskLibrary;
    private UserLibrary userLibrary;
    
    public ExportJSON(ProjectLibrary projectLibrary, TaskLibrary taskLibrary, UserLibrary userLibrary) throws IOException {
        this.projectLibrary = projectLibrary;
        this.taskLibrary = taskLibrary;
        this.userLibrary = userLibrary;
        collectData();
        writeJSON();
    }
    
    public void writeJSON() throws IOException {
        JsonFactory factory = new JsonFactory();
    

        
        JsonGenerator generator = factory.createGenerator(
                new File(System.getProperty("user.home") + "/simpledirection.json"), JsonEncoding.UTF8);
    
        generator.writeStartObject(); // = {
        generator.writeRaw('\n');
        generator.writeStringField("brand", "Mercedes");
        
        generator.writeNumberField("doors", 5);
        generator.writeEndObject(); // = }
        
        generator.close();
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
