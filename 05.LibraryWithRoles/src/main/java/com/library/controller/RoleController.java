package com.library.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.library.model.AuthenticationResponse;
import com.library.repository.AllUsersRepository;
import com.library.service.AuthenticationService;
import com.library.service.JwtService;

@RestController
@RequestMapping("/roles")
@CrossOrigin
public class RoleController {
	
	private AllUsersRepository alluserrepo;
	private AuthenticationService authService;
	private JwtService jwtservice;

	public RoleController(AllUsersRepository alluserrepo, AuthenticationService authService, JwtService jwtservice) {
		super();
		this.alluserrepo = alluserrepo;
		this.authService = authService;
		this.jwtservice = jwtservice;
	}


	@GetMapping("/fetchAll")
	public ResponseEntity<AllUsers> getAllProducts() {
		System.out.println("in admin authorized");
		AllUsers users = alluserrepo.findById(4).orElseThrow();
		return ResponseEntity.ok(users);
	} 
	

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AllUsers request) {
    	System.out.println(request.getEmail());
        return ResponseEntity.ok(authService.authentice(request));
    }
	 /*
	  * gets all users that are created by by spefific admin
	  * by taking admin's id
	  * should be only accesed by admin
	  */
	 
	 @PostMapping("/usersbyadmin")
	 public ResponseEntity<List<AllUsers>> AdminCreatedUsers(@RequestHeader("Authorization") String token,@RequestBody AllUsers user)
	 {
		 String email = jwtservice.extractUsername(token.substring(7));
		 AllUsers admin = alluserrepo.findByEmail(email).orElseThrow();
		 List<AllUsers> createdByAdmin = admin.getUsers();
		 return ResponseEntity.ok(createdByAdmin);
	 }
	 /*
	  * gets all users that are created by by spefific poweruser
	  * by taking power's id
	  * should be only accesed by poweruser
	  */
	 @GetMapping("/usersbypower")
	 public ResponseEntity<List<AllUsers>> Powerusercreatedusers(@RequestHeader("Authorization") String token)
	 {
		 String email = jwtservice.extractUsername(token.substring(7));
		 AllUsers power = alluserrepo.findByEmail(email).orElseThrow();
		return ResponseEntity.ok(power.getUsers());
	 }
	 
	 @DeleteMapping("/deleteuser/{userid}")
	 public ResponseEntity<String> deleteuserbyadmin(@PathVariable Integer userid){
		 alluserrepo.deleteById(userid);
		return ResponseEntity.ok("deleted");
		 
	 }
	 


}
