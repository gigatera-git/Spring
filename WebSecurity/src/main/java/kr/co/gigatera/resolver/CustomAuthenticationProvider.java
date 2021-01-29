package kr.co.gigatera.resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import kr.co.gigatera.vo.CustomUserDetails;

public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	BCryptPasswordEncoder bcryptPasswordEncoder;
	
	@Autowired
    private UserDetailsService userDeSer;
 
    @SuppressWarnings("unchecked")
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        
    	//BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder(10);
    	
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        //password = passwordEncoder.encode(password);
        
        
        CustomUserDetails user = (CustomUserDetails) userDeSer.loadUserByUsername(username);
        System.out.println("password : " + password);
        System.out.println("user.getPassword() : " + user.getPassword());
        System.out.println("passwordEncoder.matches : " + bcryptPasswordEncoder.matches(password,user.getPassword()));

        
        if(!user.isEnabled() || !user.isCredentialsNonExpired()) {
            throw new AuthenticationCredentialsNotFoundException(username);
        }
        
//        if(!matchPassword(password, user.getPassword())) {
//            throw new BadCredentialsException(username);
//        }
        if (!bcryptPasswordEncoder.matches(password,user.getPassword())) {
        	throw new BadCredentialsException(username);
        }
 
        if(!user.isEnabled()) {
            throw new BadCredentialsException(username);
        }
        
        return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
    }
 
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
    
    private boolean matchPassword(String loginPwd, String password) {
        return loginPwd.equals(password);
    }

}
