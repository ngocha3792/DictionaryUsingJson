package function;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import model.Word;

public class DictionaryApp {
    private static final String FILE_PATH = "dictionary.json";
    private Map<String, Word> dictionary;
    private Gson gson;

    public DictionaryApp() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.dictionary = loadDictionary();
    }

    private Map<String, Word> loadDictionary() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type dictionaryType = new TypeToken<Map<String, Word>>() {}.getType();
            return gson.fromJson(reader, dictionaryType);
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public void addNewWord(Word newWord) {
        if (newWord != null && newWord.getWord() != null) {
            dictionary.put(newWord.getWord().toLowerCase(), newWord);
            saveDictionary();
        }
    }

    private void saveDictionary() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(dictionary, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Word searchWord(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }
        String lowerCaseWordKey = input.toLowerCase();
        return dictionary.getOrDefault(lowerCaseWordKey, null);
    }

    public void editWord(String wordKey, Word updatedWord) {
        if (wordKey == null || updatedWord == null || wordKey.isEmpty()) {
            return;
        }
        String lowerCaseWordKey = wordKey.toLowerCase();
        if (dictionary.containsKey(lowerCaseWordKey)) {
            dictionary.put(lowerCaseWordKey, updatedWord);
            saveDictionary();
        }
    }

    public void deleteWord(String wordKey) {
        if (wordKey == null || wordKey.isEmpty()) {
            return;
        }
        String lowerCaseWordKey = wordKey.toLowerCase();
        if (dictionary.containsKey(lowerCaseWordKey)) {
            dictionary.remove(lowerCaseWordKey);
            saveDictionary();
        }
    }

    public Map<String, Word> getAllWords() {
        return new HashMap<>(dictionary);
    }
}
