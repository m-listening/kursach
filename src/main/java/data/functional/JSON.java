package data.functional;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.micro_objects.Kamikaze;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static app.Play.world;
import static data.functional.MicroObjectConfig.convertToConfig;
import static data.functional.Utilities.deleteWarrior;

public class JSON {
    public static void saveData(List<Kamikaze> arrayToJson, String path) throws IOException {
        FileWriter file = new FileWriter(path, false);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(file);

        jsonGenerator.writeStartArray();
        arrayToJson.forEach(object -> {
            try {
                jsonGenerator.writeObject(convertToConfig(object));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        jsonGenerator.writeEndArray();
        jsonGenerator.close();
    }

    /**
     * Parser for MicroObjectConfig
     */
    public static void parseData(File file) throws IOException {
        if (file == null) return;
        deleteWarrior(world.getAllWarriors());
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(file);
        for (JsonNode item : jsonNode) {
            MicroObjectConfig object = objectMapper.readValue(item.traverse(), MicroObjectConfig.class);
            object.convertToObject();
        }
    }
}
