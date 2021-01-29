package kr.co.gigatera.vo;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
public class CustomUserDetails implements UserDetails {

	private String Id;
    private String Password;
    private String Authority;
    private boolean Enabled;
    private String Name;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        auth.add(new SimpleGrantedAuthority(Authority));
        return auth;
    }
 
    @Override
    public String getPassword() {
        return Password;
    }
 
    @Override
    public String getUsername() {
        return Id;
    }
 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
        return Enabled;
    }
    
    public String getNAME() {
        return Name;
    }
 
    public void setNAME(String name) {
    	Name = name;
    }

	
}
