package kr.co.gigatera.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import kr.co.gigatera.dao.UserAuthDAO;
import kr.co.gigatera.vo.CustomUserDetails;

public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
    private UserAuthDAO userAuthDAO;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CustomUserDetails user = userAuthDAO.getUserById(username);
        if(user==null) {
            //throw new UsernameNotFoundException(username);
        	throw new InternalAuthenticationServiceException(username);
        }
        return user;
	}

}
