package com.backend.pishop.secuirity;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.backend.pishop.entity.Account;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AccountService accountService;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
	                                HttpServletResponse response,
	                                FilterChain filterChain)
	        throws ServletException, IOException {
		System.out.println("➡️ Incoming request: " + request.getMethod() + " " + request.getRequestURI());


	    String path = request.getServletPath();

	    // Public API
	    if (path.startsWith("/auth") ||
	        path.startsWith("/product") ||
	        path.startsWith("/brand") ||
	        path.startsWith("/category") ||
	        path.startsWith("/promotion")) {
	    	System.out.println("🔓 Public API, skip JWT filter: " + path);
	        filterChain.doFilter(request, response);
	        return;
	    }

	    final String authHeader = request.getHeader("Authorization");

	    String token = null;
	    String email = null;

	    if (authHeader != null && authHeader.startsWith("Bearer ")) {
	        token = authHeader.substring(7);
	        try {
	            email = jwtUtil.extractEmail(token);
	        } catch (Exception e) {
	            System.out.println("Invalid JWT");
	        }
	    }

	    if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

	        UserDetails userDetails = accountService.loadUserByUsername(email);

	        if (userDetails != null && jwtUtil.validateToken(token, userDetails)) {

	            // ✔ Lấy Account đúng cách
	            Account account = accountService.findByEmail(email);

	            if (!account.isActive()) {
	                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	                response.getWriter().write("Account inactive");
	                return;
	            }

	            UsernamePasswordAuthenticationToken authToken =
	                    new UsernamePasswordAuthenticationToken(
	                            userDetails,
	                            null,
	                            userDetails.getAuthorities()
	                    );

	            authToken.setDetails(
	                    new WebAuthenticationDetailsSource().buildDetails(request)
	            );

	            SecurityContextHolder.getContext().setAuthentication(authToken);
	        }
	    }

	    

	    filterChain.doFilter(request, response);
	}
}
