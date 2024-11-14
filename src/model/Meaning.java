package model;

import java.util.List;

public class Meaning {
	private String defination;
	private List <String> example;
	
	public Meaning() {
		
	}
	
	public Meaning(String defination, List<String> example) {
		super();
		this.defination = defination;
		this.example = example;
	}


	public String getDefination() {
		return defination;
	}


	public void setDefination(String defination) {
		this.defination = defination;
	}


	public List<String> getExample() {
		return example;
	}


	public void setExample(List<String> example) {
		this.example = example;
	}


	@Override
	public String toString() {
		return "Meaning [defination=" + defination + ", example=" + example + "]";
	}
	
	
}
