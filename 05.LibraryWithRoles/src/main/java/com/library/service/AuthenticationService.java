package com.library.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.library.model.AllUsers;
import com.library.model.AuthenticationResponse;
import com.library.repository.AllUsersRepository;

@Service
public class AuthenticationService {
	
	private final JwtService jwtservice;
	private final AuthenticationManager authenticationManager;
	private final AllUsersRepository alluserrepo;
	
	public AuthenticationService(JwtService jwtservice, AuthenticationManager authenticationManager,
			AllUsersRepository alluserrepo) {
		super();
		this.jwtservice = jwtservice;
		this.authenticationManager = authenticationManager;
		this.alluserrepo = alluserrepo;
	}
	public AuthenticationResponse authentice(AllUsers request) {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
					request.getEmail(),
					request.getPassword()
					)
				);
		}
		catch(Exception ae) {
			System.out.println("error at login"+ae.getMessage());
		}
	

		AllUsers user =alluserrepo.findByEmail(request.getEmail()).orElseThrow();
		String roles = user.getRoles();
		String token = jwtservice.generateToken(user);
		
		return new AuthenticationResponse(token,roles);
	}
	

}
