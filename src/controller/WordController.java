package controller;

import model.Word;

import java.util.ArrayList;
import java.util.List;

public class WordController {
    private List<Word> words;

    public WordController() {
        this.words = new ArrayList<>();
    }

    public List<Word> getAllWords() {
        return words;
    }

    public Word getWord(String word) {
        return words.stream()
                .filter(w -> w.getWord().equalsIgnoreCase(word))
                .findFirst()
                .orElse(null);
    }

    public boolean addWord(Word word) {
        if (word != null && getWord(word.getWord()) == null) {
            words.add(word);
            return true;
        }
        return false;
    }

    public boolean updateWord(String word, Word updatedWord) {
        Word existingWord = getWord(word);
        if (existingWord != null) {
            int index = words.indexOf(existingWord);
            words.set(index, updatedWord);
            return true;
        }
        return false;
    }

    public boolean deleteWord(String word) {
        Word existingWord = getWord(word);
        if (existingWord != null) {
            words.remove(existingWord);
            return true;
        }
        return false;
    }
}
