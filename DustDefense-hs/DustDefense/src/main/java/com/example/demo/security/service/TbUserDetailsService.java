package com.example.demo.security.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.TbUser;
import com.example.demo.repository.TbUserRepository;
import com.example.demo.security.dto.TbUserAuthDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class TbUserDetailsService implements UserDetailsService{
	
	private final TbUserRepository tbUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws
		UsernameNotFoundException{
		
		log.info("TbUserDetailService loadUserByUsername " + username);
		
		Optional<TbUser> result = tbUserRepository.findById(username);
		
		if(result.isEmpty()) {
			throw new UsernameNotFoundException(" Check ID ");
		}
		
		TbUser tbUser = result.get();
		
		log.info("--------------------");
		log.info(tbUser);
		
		TbUserAuthDTO tbUserAuth = new TbUserAuthDTO(
				tbUser.getId(),
				tbUser.getPassword(),
				tbUser.getEmail(),
				tbUser.isValidMember(),
				tbUser.getRoleSet().stream().map(role -> new SimpleGrantedAuthority
						("ROLE_" + role.name())).collect(Collectors.toSet())
		);
		
		
		return tbUserAuth;

	}
	
	
}