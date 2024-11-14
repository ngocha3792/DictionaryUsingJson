package funtion;

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

	public DictionaryApp(Map<String, Word> dictonary, Gson gson) {
		this.dictionary = loadDictionary();
		this.gson = new GsonBuilder().setPrettyPrinting().create();
	}

	private Map<String, Word> loadDictionary() {
		try (FileReader reader = new FileReader(FILE_PATH)) {
			Type dictionaryType = new TypeToken<Map<String, Word>>() {
			}.getType();
			return gson.fromJson(reader, dictionaryType);
		} catch (IOException e) {
			e.printStackTrace();
			return new HashMap<String, Word>();
		}
	}

	public void addNewWord(Word newWord) {
		dictionary.put(newWord.getWord().toLowerCase(), newWord);
		saveDictionary();
	}

	private void saveDictionary() {
		try (FileWriter writer = new FileWriter(FILE_PATH)) {
			gson.toJson(dictionary, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Word searchWord(String input) {
		String lowerCaseWordKey = input.toLowerCase();
		if (dictionary.containsKey(lowerCaseWordKey)) {
			return dictionary.get(lowerCaseWordKey);
		} else
			return null;
	}

	public void editWord(String wordKey, Word updateWord) {
		String lowerCaseWordKey = wordKey.toLowerCase();
		if (dictionary.containsKey(lowerCaseWordKey)) {
			dictionary.put(lowerCaseWordKey, updateWord);
			saveDictionary();
		} else
			return;
	}
	
}
