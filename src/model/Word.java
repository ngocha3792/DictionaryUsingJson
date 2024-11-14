package model;

import java.util.List;

public class Word {
	
	private String word;
	private String pronunciation;
	private List <Meaning> meanings;
	private String partOfSpeech; // Ví dụ về cách sử dụng từ
	private List <String> synonyms; // Từ đồng nghĩa
	private List <String> antonyms; // Từ trái nghĩa
	private String origin;
	
	
	public Word() {
		
	}

	public Word(String word, String pronunciation, List<Meaning> meanings, String partOfSpeech, 
			List<String> synonyms, List<String> antonyms, String origin) {
		super();
		this.word = word;
		this.pronunciation = pronunciation;
		this.meanings = meanings;
		this.partOfSpeech = partOfSpeech;
		this.synonyms = synonyms;
		this.antonyms = antonyms;
		this.origin = origin;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getPronunciation() {
		return pronunciation;
	}

	public void setPronunciation(String pronunciation) {
		this.pronunciation = pronunciation;
	}

	public List<Meaning> getMeanings() {
		return meanings;
	}

	public void setMeanings(List<Meaning> meanings) {
		this.meanings = meanings;
	}

	public String getPartOfSpeech() {
		return partOfSpeech;
	}

	public void setPartOfSpeech(String partOfSpeech) {
		this.partOfSpeech = partOfSpeech;
	}

	public List<String> getSynonyms() {
		return synonyms;
	}

	public void setSynonyms(List<String> synonyms) {
		this.synonyms = synonyms;
	}

	public List<String> getAntonyms() {
		return antonyms;
	}

	public void setAntonyms(List<String> antonyms) {
		this.antonyms = antonyms;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	@Override
	public String toString() {
		return "Word [word=" + word + ", pronunciation=" + pronunciation + ", meanings=" + meanings + ", partOfSpeech="
				+ partOfSpeech + ",  synonyms=" + synonyms + ", antonyms=" + antonyms
				+ ", origin=" + origin + "]";
	}
	
	
}
