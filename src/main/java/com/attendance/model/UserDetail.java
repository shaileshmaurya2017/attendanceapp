package com.attendance.model;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.attendance.entity.User;

@Component
public class UserDetail implements UserDetails {

	private static final long serialVersionUID = 1L;
	@Autowired
	private User userentity;

	public UserDetail(User userentity) {
	//	super();
		this.userentity = userentity;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userentity.getRoles());
        return Arrays.asList(authority);
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return userentity.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userentity.getUsername();
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
        return true;
    }
}
