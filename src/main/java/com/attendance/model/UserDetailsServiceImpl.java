package com.attendance.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.attendance.entity.User;
import com.attendance.repository.UserRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	//@Autowired
	private final UserRepo userRepo;
	
	
	public UserDetailsServiceImpl(UserRepo userRepo) {
		this.userRepo = userRepo;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepo.getUserByUsername(username);
        
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
         
        return new UserDetail(user);
	}

}
