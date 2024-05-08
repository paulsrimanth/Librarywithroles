package com.library.model;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"books"})
@Entity
public class AllUsers {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String email;
	private String password;
	private String roles;

	
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name="admin_user",
	joinColumns= {@JoinColumn(name="admin_id")},
	inverseJoinColumns = {@JoinColumn(name="user_id")})
	private List<AllUsers> users = new ArrayList<AllUsers>();
	
	
	@OneToMany(mappedBy="users")
	private List<Books> books = new ArrayList<Books>();



	

	

}
