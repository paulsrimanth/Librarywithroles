package com.library.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.model.AllUsers;
import com.library.repository.AllUsersRepository;
import com.library.service.JwtService;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UsersController {
	private JwtService jwtservice;
	private AllUsersRepository alluserrepo;
	private final PasswordEncoder passwordEncoder;


	public UsersController(JwtService jwtservice, AllUsersRepository alluserrepo, PasswordEncoder passwordEncoder) {
		super();
		this.jwtservice = jwtservice;
		this.alluserrepo = alluserrepo;
		this.passwordEncoder = passwordEncoder;
	}
	@PostMapping("/register")
    public ResponseEntity<AllUsers> register(@RequestBody AllUsers request) {
		AllUsers user = new AllUsers();
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRoles(request.getRoles());

        return ResponseEntity.ok(alluserrepo.save(user));
    }
	/*
	 * poweruser creates admin
	 * only be accesible to poweruser
	 * user/power/createadmin
	 */
	 @PostMapping("/power/createadmin")
	    public ResponseEntity<AllUsers> createUserByAdmin(@RequestBody AllUsers user,@RequestHeader("Authorization") String token) {
		 
		 System.out.println(token);
		 String username = jwtservice.extractUsername(token.substring(7));
	
		 AllUsers admin = alluserrepo.findByEmail(username).orElseThrow();
	     System.out.println(admin.getUsers());
		 AllUsers newUser = new AllUsers();
				 //userService.createUser(user);
		 newUser.setEmail(user.getEmail());
		 newUser.setPassword(passwordEncoder.encode(user.getPassword()));
		 newUser.setRoles(user.getRoles());
		 AllUsers usernew = alluserrepo.save(newUser);
	        admin.getUsers().add(usernew);
	        alluserrepo.save(admin);
//	        userService.saveUser(admin);
	        
	        return ResponseEntity.status(HttpStatus.CREATED).body(usernew);
	        		//(alluserrepo.save(newUser));
	    }
	 /*
	  * admin or poweruser creates users
	  * accesible to admin,poweruser
	  * user/createuser
	  */
	 @PostMapping("/createuser")
	    public ResponseEntity<AllUsers> createUserByPower(@RequestBody AllUsers user,@RequestHeader("Authorization") String token) {
		 
		 System.out.println(token);
		 String username = jwtservice.extractUsername(token.substring(7));
	
		 AllUsers admin = alluserrepo.findByEmail(username).orElseThrow();
	     System.out.println(admin.getUsers());
		 AllUsers newUser = new AllUsers();
				 //userService.createUser(user);
		 newUser.setEmail(user.getEmail());
		 newUser.setPassword(passwordEncoder.encode(user.getPassword()));
		 newUser.setRoles(user.getRoles());
		 AllUsers usernew = alluserrepo.save(newUser);
	        admin.getUsers().add(usernew);
	        alluserrepo.save(admin);
	        
	        return ResponseEntity.status(HttpStatus.CREATED).body(usernew);

	    }
	 @GetMapping("/allusers")
	 public ResponseEntity<List<AllUsers>> getusers(@RequestHeader("Authorization") String token){
		 String roles = "USER";
		 String username = jwtservice.extractUsername(token.substring(7));
		 List<AllUsers> userdata = alluserrepo.findAllByRolesEquals(roles);
		 return ResponseEntity.ok(userdata);
	 }

	 
	


}
