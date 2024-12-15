package function;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Idiom;

public class IdiomApp {
    private static final String FILE_PATH = "idioms.json";
    private List<Idiom> idioms;
    private final Gson gson;

    public IdiomApp() {
        this.gson = new Gson();
        this.idioms = loadIdioms();
    }

    private List<Idiom> loadIdioms() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            return gson.fromJson(reader, new TypeToken<List<Idiom>>() {}.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void saveIdioms() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(idioms, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Idiom> getAllIdioms() {
        return idioms;
    }
}