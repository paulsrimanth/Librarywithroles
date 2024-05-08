package com.library.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.library.model.AllUsers;
import com.library.repository.AllUsersRepository;

@Service
public class GroupUserDetailsService implements UserDetailsService {
	
	
	private AllUsersRepository repository;

	public GroupUserDetailsService(AllUsersRepository repository) {
		super();
		this.repository = repository;
	}




	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		Optional<AllUsers> users = repository.findByEmail(username);
		return users.map(GroupUserDetails::new)
				.orElseThrow(()->new UsernameNotFoundException(username + "NOT FOUND"));
				
	}

}
