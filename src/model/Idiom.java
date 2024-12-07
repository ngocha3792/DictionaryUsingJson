package model;

import java.util.List;

public class Idiom {
	private String name;
	private List <Meaning> meanings;
	
	
	public Idiom(String name, List<Meaning> meanings) {
		super();
		this.name = name;
		this.meanings = meanings;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Meaning> getMeanings() {
		return meanings;
	}
	public void setMeanings(List<Meaning> meanings) {
		this.meanings = meanings;
	} 
	
}
