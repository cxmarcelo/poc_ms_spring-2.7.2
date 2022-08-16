package br.com.marcelo.msauthserver.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import br.com.marcelo.msauthserver.entities.UserEntity;
import br.com.marcelo.msauthserver.repositories.UserRepository;

@Repository
public class UserService implements UserDetailsService{

	@Autowired
	private UserRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity =  repo.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException(username));
		
		final var simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_" + userEntity.getType().name());
		
		return new User(userEntity.getEmail(), userEntity.getPassword(),  List.of(simpleGrantedAuthority));
	}

}
