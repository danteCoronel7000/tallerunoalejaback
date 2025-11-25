package com.taller1.colegioJMA2.service;

import com.taller1.colegioJMA2.model.UsuariosModel;
import com.taller1.colegioJMA2.repository.UsuariosRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority; // Importación clave
import java.util.stream.Collectors; // Importación clave
import java.util.List; // Importación clave

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UsuariosRepo usuariosRepo;

    public CustomUserDetailsService(UsuariosRepo usuariosRepo) {
        this.usuariosRepo = usuariosRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UsuariosModel usuario = usuariosRepo.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + login));

        
        List<SimpleGrantedAuthority> authorities = usuario.getRoles().stream()
                .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getNombre().toUpperCase()))
                .collect(Collectors.toList());

        return User.builder()
                .username(usuario.getLogin())
                .password(usuario.getPassword())
                .authorities(authorities)
                .build();
    }
}