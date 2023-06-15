package data.functional.forObjects;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.objects.micro_objects.Kamikaze;
import data.functional.forObjects.micro.MicroObjectConfig;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static app.Play.globalStage;
import static app.Play.world;
import static data.functional.forObjects.micro.MethodsOfMicro.deleteWarrior;
import static data.functional.forObjects.micro.MicroObjectConfig.convertToConfig;

public class JSON {
    private static void saveData(List<Kamikaze> arrayToJson, String path) throws IOException {
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

    private static void parseData(File file) throws IOException {
        if (file == null) return;
        deleteWarrior(world.getAllWarriors());
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(file);
        for (JsonNode item : jsonNode) {
            MicroObjectConfig object = objectMapper.readValue(item.traverse(), MicroObjectConfig.class);
            object.convertToObject();
        }
    }

    public static void saveDataToFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\"));
        fileChooser.setInitialFileName("data.json");
        FileChooser.ExtensionFilter textFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(textFilter);
        File file = fileChooser.showSaveDialog(globalStage);
        if (file != null) JSON.saveData(world.getAllWarriors(), file.getPath());
    }

    public static void openDataFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\"));
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("JSON files(*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showOpenDialog(globalStage);
        JSON.parseData(file);
    }
}
