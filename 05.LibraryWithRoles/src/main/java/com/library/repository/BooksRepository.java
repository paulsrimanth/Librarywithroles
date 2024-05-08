package com.library.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.model.Books;

@Repository
public interface BooksRepository extends JpaRepository<Books, Integer>{
	
//	Optional<Books> findAllByCategory(String category);
	List<Books> findAllByCategoryEquals(String category);
	

}
