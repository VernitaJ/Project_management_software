package tools;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.*;
import java.io.IOException;

public class DataDeserializer extends JsonDeserializer<Data> {
    @Override
    public Data deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        ObjectNode root = (ObjectNode) mapper.readTree(jp);
        Class<? extends Data> instanceClass = null;
        System.out.println("Entered somewhere");
        if(root.equals(User.class)) {
            System.out.println("PogChamp");
            instanceClass = User.class;
        } else if (root.equals(Project.class)) {
            instanceClass =  Project.class;
        } else if (root.equals(Task.class)) {
            instanceClass = Task.class;
        } else if (root.equals(Team.class)) {
            instanceClass = Team.class;
        } else if (root.equals(Message.class)) {
            instanceClass = Message.class;
        }
        return mapper.readValue(jp, instanceClass);
    }

}
