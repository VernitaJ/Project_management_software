package tools;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import entities.ProjectLibrary;
import entities.TaskLibrary;
import entities.UserLibrary;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;


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
    

        String path = System.getProperty("user.home") + "/simpledirection.json";
        JsonGenerator generator = factory.createGenerator(
                new File(path), JsonEncoding.UTF8);
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
       // objectMapper.getFactory().configure(JsonWriteFeature.ESCAPE_NON_ASCII.mappedFeature(), false);

        for(int i = 0; i < this.userLibrary.getAllUsers().size(); i++) {
            String serialized = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this.userLibrary.getAllUsers().get(i));

            //serialized = serialized.replaceAll("\\r", "").replaceAll("\\n", "");
            writer.writeValue(Paths.get(path).toFile(), serialized);

        }


        
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
