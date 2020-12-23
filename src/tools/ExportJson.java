package tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import entities.Project;
import entities.ProjectLibrary;
import entities.TaskLibrary;
import entities.UserLibrary;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static com.fasterxml.jackson.databind.SerializationFeature.CLOSE_CLOSEABLE;
import static com.fasterxml.jackson.databind.SerializationFeature.WRAP_ROOT_VALUE;


public class ExportJson {
    private final ProjectLibrary projectLibrary;
    private final TaskLibrary taskLibrary;
    private final UserLibrary userLibrary;
    private final ObjectMapper MAPPER;
    private String projectFileName;

    public ExportJson(ProjectLibrary projectLibrary, TaskLibrary taskLibrary, UserLibrary userLibrary) throws IOException {
        this.projectLibrary = projectLibrary;
        this.taskLibrary = taskLibrary;
        this.userLibrary = userLibrary;
        this.MAPPER = new ObjectMapper();
        this.MAPPER.registerModule(new JavaTimeModule());
        this.MAPPER.configure(CLOSE_CLOSEABLE, false);
        this.MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.MAPPER.configure(WRAP_ROOT_VALUE, false);
        this.projectFileName = System.getProperty("user.home") + "/project.json";
        
        writeJsonProject();
       // readJsonProject();
    }

    public void writeJsonProject() throws IOException {
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(Paths.get(this.projectFileName).toFile(), this.projectLibrary.getDataList().get(0));
    }

    public void readJsonProject() throws IOException {
        Project readProject = MAPPER.readValue(new File(this.projectFileName), Project.class);
        projectLibrary.getDataList().add(readProject);
    }
}
