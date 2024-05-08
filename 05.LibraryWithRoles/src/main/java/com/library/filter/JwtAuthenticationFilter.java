package com.library.filter;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import com.library.service.GroupUserDetailsService;
import com.library.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	private final JwtService jwtservice;
	
	private final GroupUserDetailsService userdetailsserviceimp;
	
	

	public JwtAuthenticationFilter(JwtService jwtservice, GroupUserDetailsService userdetailsserviceimp) {
		super();
		this.jwtservice = jwtservice;
		this.userdetailsserviceimp = userdetailsserviceimp;
	}



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String authHeader = request.getHeader("Authorization");
		System.out.println("in filter class");
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		String token = authHeader.substring(7);
				//replace(String.valueOf(/),"");
				//
				//split("/")[1].trim();

	String username = jwtservice.extractUsername(token);
	if(username != null && SecurityContextHolder.getContext().getAuthentication()==null) {
		
		UserDetails userDetails = userdetailsserviceimp.loadUserByUsername(username);
		
		if(jwtservice.isValid(token, userDetails)) {
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
			
			authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			SecurityContextHolder.getContext().setAuthentication(authToken);
		}
	}
	filterChain.doFilter(request, response);	
		
		
	}

}
