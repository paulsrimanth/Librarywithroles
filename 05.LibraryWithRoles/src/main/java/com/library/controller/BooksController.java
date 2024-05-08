package com.library.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.library.model.AllUsers;
import com.library.model.Books;
import com.library.repository.AllUsersRepository;
import com.library.repository.BooksRepository;
import com.library.service.AuthenticationService;
import com.library.service.JwtService;


@RestController
@RequestMapping("/book")
@CrossOrigin
public class BooksController {
	
	private AllUsersRepository alluserrepo;
	private AuthenticationService authService;
	private BooksRepository bookrepo;
	private JwtService jwtservice;

	public BooksController(AllUsersRepository alluserrepo, AuthenticationService authService, BooksRepository bookrepo,
			JwtService jwtservice) {
		super();
		this.alluserrepo = alluserrepo;
		this.authService = authService;
		this.bookrepo = bookrepo;
		this.jwtservice = jwtservice;
	}
	@GetMapping("/fetchbooks")
	public ResponseEntity<List<Books>> getallbooks()
	{
		List<Books> bookstodisplay= bookrepo.findAll();
		
		return ResponseEntity.ok(bookstodisplay);
	}
	@GetMapping("/getbookdata/{bookid}")
	public ResponseEntity<Books> getbook(@PathVariable Integer bookid){
		System.out.println("in controller");
		System.out.println(bookid);
		Books book = bookrepo.findById(bookid).orElseThrow();
		return ResponseEntity.ok(book);
	}

	@PostMapping("/addbook/{adminid}")
    public ResponseEntity<Books> register(@PathVariable Integer adminid,@RequestBody Books request) {
		AllUsers user = alluserrepo.findById(adminid).orElseThrow();
		System.out.println(user);
		Books book = new Books();
		book.setName(request.getName());
		book.setCategory(request.getCategory());
		book.setPublishyear(request.getPublishyear());
		book.setUsers(user);
		
//		AllUserser = new AllUsers();
//		user.setEmail(request.getEmail());
//		user.setPassword(passwordEncoder.encode(request.getPassword()));
//		user.setRoles(request.getRoles());
        return ResponseEntity.ok(bookrepo.save(book));
    }
	
	@PostMapping("/upload")
	public Books uploadImage(@RequestParam("imageofbook")MultipartFile imageofbook,
			@RequestParam("name")String name,
			@RequestParam("author")String author,
			@RequestParam("category") String category,
			@RequestParam("publishyear")String publishyear,
			@RequestHeader("Authorization")String jwt
			)throws  Exception
	{
//		@RequestHeader("Authorization")String jwt,
		Books book = new Books();
		System.out.println(jwt);
		String token = jwt.substring(7);
		System.out.println("substring "+token);
		String email = jwtservice.extractUsername(token);
		System.out.println(email);
		AllUsers createduser = alluserrepo.findByEmail(jwtservice.extractUsername(token)).orElseThrow();
		book.setImageofbook((imageofbook.getBytes()));
		book.setName(name);
		book.setAuthor(author);
		book.setCategory(category);
		book.setPublishyear(publishyear);

//		book.setUsers(createduser);

		return bookrepo.save(book);
		
	}
	
	
	@PostMapping("/assignbooks/{bookid}")
	public Books AssignBooks(
			@PathVariable Integer bookid,
			@RequestHeader("Authorization")String jwt
			)throws  Exception
	{
//		@RequestHeader("Authorization")String jwt,
		Books book = bookrepo.findById(bookid).orElseThrow();
//		System.out.println(jwt);
		String token = jwt.substring(7);
		//System.out.println("substring "+token);
		String email = jwtservice.extractUsername(token);
		System.out.println(email);
		AllUsers assignuser = alluserrepo.findByEmail(email).orElseThrow();
		book.setUsers(assignuser);
		assignuser.getBooks().add(book);
		


		return bookrepo.save(book);
		
	}
	
	@PostMapping("/releasebook/{bookid}")
	public ResponseEntity<AllUsers> ReleaseBooks(@RequestHeader("Authorization") String jwt,@PathVariable Integer bookid) {
		
		String token = jwt.substring(7);
		String email = jwtservice.extractUsername(token);
		AllUsers userdata = alluserrepo.findByEmail(email).orElseThrow();
		Books book = bookrepo.findById(bookid).orElseThrow();
//		AllUsers user = book.getUsers();
//		System.out.println(book.getUsers().getBooks());
		System.out.println("book"+book.getUsers());
//		List<AllUsers> usersassignes=  book.getUsers();
		List<Books> booklist = userdata.getBooks();
//		System.out.println("before book list...");
//		System.out.println(userdata.getBooks());
//		System.out.println(booklist);
//		booklist.remove(book);
		userdata.getBooks().remove(book);
		AllUsers users = book.getUsers();
		book.setUsers(null);
		bookrepo.save(book);
//		book.setUsers(null);
//		userdata.setBooks(booklist);
		
		return ResponseEntity.ok(alluserrepo.save(userdata));
	}
	
	@PostMapping("/getmybooks")
	public ResponseEntity<List<Books>> getassignedbooks(@RequestHeader("Authorization") String jwt){
		
		String token = jwt.substring(7);
		String email = jwtservice.extractUsername(token);
		AllUsers userdata = alluserrepo.findByEmail(email).orElseThrow();
		System.out.println(userdata.getBooks());
		
		return ResponseEntity.ok(userdata.getBooks());
	}
	
	
//	@PostMapping("/category")
//	public ResponseEntity<Books> getbycategory(Books book){
//		String cat = book.getCategory();
//		Books booko = bookrepo.findAllByCategory(book.getCategory()).orElseThrow();
//		System.out.println(booko);
//		return ResponseEntity.ok(booko);
//	}
	@PostMapping("/category/{book}")
	public ResponseEntity<List<Books>> getbycategory(@PathVariable String book){
		System.out.println(book);
		String cat = book;
		System.out.println(book);
		System.out.println(cat);
		List<Books> books = bookrepo.findAllByCategoryEquals(cat);
		System.out.println(books);
		return ResponseEntity.ok(books);
	}
	
	
	
	
}
