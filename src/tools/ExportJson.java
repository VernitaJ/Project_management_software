package tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import entities.Project;
import entities.ProjectLibrary;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.fasterxml.jackson.databind.SerializationFeature.CLOSE_CLOSEABLE;
import static com.fasterxml.jackson.databind.SerializationFeature.WRAP_ROOT_VALUE;


public class ExportJson {
    private final ProjectLibrary projectLibrary;
    private final ObjectMapper MAPPER;

    public ExportJson(ProjectLibrary projectLibrary) throws IOException {
        this.projectLibrary = projectLibrary;
        this.MAPPER = new ObjectMapper();
        this.MAPPER.registerModule(new JavaTimeModule());
        this.MAPPER.configure(CLOSE_CLOSEABLE, false);
        this.MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.MAPPER.configure(WRAP_ROOT_VALUE, false);
    }

    public void writeJsonProject(Project currentProject) throws IOException {
        String projectFileName = "";
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        projectFileName = "export" + timeStamp +".json";
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(Paths.get(projectFileName).toFile(), currentProject);
        System.out.println("The file " + projectFileName + " has been created.");
    }
}
