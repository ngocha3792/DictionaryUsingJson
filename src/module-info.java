/**
 * 
 */
/**
 * 
 */
module DictionaryUsingJson {
	opens controller to javafx.fxml;
	exports controller;
	exports view;

	requires javafx.base;
	requires transitive javafx.controls;
	requires javafx.fxml;
	requires javafx.media;
	requires javafx.swing;
	requires javafx.web;
	requires transitive javafx.graphics;
	requires com.google.gson;
	requires java.base;
}