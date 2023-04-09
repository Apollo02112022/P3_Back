package com.back.projet3.security;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.back.projet3.repository.*;

/**
 * 
 * Me permet d'avoir accès aux infos de l'utilisateur dans spring security.
 */
@Service
public class CustomUserDetails implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public User loadUserByUsername(String username) throws UsernameNotFoundException {
    // T-753 Auto-generated method stub

    com.back.projet3.entity.User user = userRepository.findUserByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("J'ai pas trouvé le username pourtant j'ai essayé hein 🥵");
    }
    Collection<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
    roles.add(new SimpleGrantedAuthority("USER"));
    
    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), roles);
  }
} 


