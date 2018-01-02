package com.example.demo.domain;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Question {
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
	private User writer;
	private String title;
	private String contents;
	
	public Question() {}
	
	public Question(User writer, String title, String contents) {
		super();
		this.writer = writer;
		this.title = title;
		this.contents = contents;
	}
	public Long getId() {
		return id;
	}
	
	public User getWriter() {
		return writer;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getContents() {
		return contents;
	}
	public boolean isSameWriter(User newWriter) {
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
