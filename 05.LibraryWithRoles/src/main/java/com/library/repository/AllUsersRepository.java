package com.library.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.model.AllUsers;
import com.library.model.Books;

//library v2
@Repository
public interface AllUsersRepository extends JpaRepository<AllUsers,Integer> 
{
	Optional<AllUsers> findByEmail(String email);
	List<AllUsers> findAllByRolesEquals(String roles);
}
