package com.conversa.userservice.service;
import com.conversa.userservice.domain.User; import com.conversa.userservice.repository.UserRepository; import org.springframework.security.core.userdetails.*; import org.springframework.stereotype.Service; import java.util.Collections;
@Service public class CustomUserDetailsService implements UserDetailsService {
 private final UserRepository users; public CustomUserDetailsService(UserRepository users){this.users=users;}
 @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
  User u=users.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
  return new org.springframework.security.core.userdetails.User(u.getUsername(),u.getPassword(),Collections.emptyList());
 }
}