package com.library.model;

import java.util.Arrays;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Books {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bookid;
	private String name;
	private String category;
	private String publishyear;
	private String author;
	@Lob
	@Column(columnDefinition = "LongBlob")
	private byte[] imageofbook;
	@ManyToOne
	@JoinColumn(name="alluser_id")
	private AllUsers users;
	@Override
	public String toString() {
		return "Books";
	}
	
	

	
	
	
}
