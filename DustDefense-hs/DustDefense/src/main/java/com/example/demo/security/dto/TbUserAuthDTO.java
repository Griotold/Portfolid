package com.example.demo.security.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Data
public class TbUserAuthDTO extends User{
	
	private String id;
	
	private String email;
	
	private boolean validMember;

	public TbUserAuthDTO(
			String username, 
			String password,
			String email,
			boolean validMember,
			Collection<? extends GrantedAuthority> authorities) {
		
		super(username, password, authorities);
		this.id = username;
		this.email = email;
	}

}