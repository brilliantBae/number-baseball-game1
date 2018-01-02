package com.example.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Question {
	@Id
	@GeneratedValue
	private Long id;
	
	private String writer;
	private String title;
	private String contents;
	
	public Question() {}
	
	public Question(String writer, String title, String contents) {
		super();
		this.writer = writer;
		this.title = title;
		this.contents = contents;
	}
	public Long getId() {
		return id;
	}
	
	public String getWriter() {
		return writer;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getContents() {
		return contents;
	}
	public boolean isSameWriter(String newWriter) {
		if(this.writer.equals(newWriter)) {
			return true;
		}
		return false;
	}

	public void update(String newTitle, String newContents) {
		this.title = newTitle;
		this.contents = newContents; 
	}
	
}
