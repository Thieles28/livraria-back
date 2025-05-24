package com.compras.livrariaapi.service.impl;

import com.compras.livrariaapi.entity.Usuario;
import com.compras.livrariaapi.model.request.SignUpRequest;
import com.compras.livrariaapi.model.request.SigninRequest;
import com.compras.livrariaapi.model.request.UserLoggedResponse;
import com.compras.livrariaapi.model.request.UsuarioRequest;
import com.compras.livrariaapi.model.response.JwtAuthenticationResponse;
import com.compras.livrariaapi.model.response.UsuarioResponse;
import com.compras.livrariaapi.repository.UserRepository;
import com.compras.livrariaapi.service.AuthenticationService;
import com.compras.livrariaapi.service.JwtService;
import com.compras.livrariaapi.util.Role;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        var user = Usuario.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER).build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public List<UsuarioResponse> registeredUsers() {
        return userRepository.findAll().stream().map(user ->
                modelMapper.map(user, UsuarioResponse.class)).collect(Collectors.toList());
    }

    @Override
    public UserLoggedResponse getUserLoggedSystem() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return getUserLoggedInSystem(authentication.getName());
    }

    public UserLoggedResponse getUserLoggedInSystem(String email) {
        Usuario usuario = userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário logado não encontrado"));

        List<String> permissions = Arrays.stream(usuario.getRole().name().split("_"))
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        return UserLoggedResponse.builder()
                .id(usuario.getId())
                .userLogged(usuario.getFirstName())
                .permissions(permissions)
                .build();
    }

    @Override
    public UsuarioResponse getUser(Long id) {
        Usuario usuario = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        return modelMapper.map(usuario, UsuarioResponse.class);
    }

    @Override
    public UsuarioResponse registerUser(UsuarioRequest usuarioRequest) {
        usuarioRequest.setPassword(passwordEncoder.encode(usuarioRequest.getPassword()));
        Usuario usuario = userRepository.save(modelMapper.map(usuarioRequest, Usuario.class));
        return modelMapper.map(usuario, UsuarioResponse.class);
    }

    @Override
    public UsuarioResponse updateUser(UsuarioRequest usuarioRequest, Long id) {
        UsuarioResponse usuarioResponse = getUser(id);
        modelMapper.map(usuarioRequest, usuarioResponse);

        Usuario usuario = modelMapper.map(usuarioResponse, Usuario.class);
        userRepository.save(usuario);

        return usuarioResponse;
    }

    @Override
    public void removeUser(UsuarioResponse usuarioResponse) {
        Usuario usuario = modelMapper.map(usuarioResponse, Usuario.class);
        userRepository.delete(usuario);
    }

    @PostConstruct
    public void initializeUserData() {
        Usuario admin = Usuario.builder()
                .firstName("Thieles")
                .lastName("Martins")
                .email("thieles@livraria.com")
                .password(passwordEncoder.encode("senha123"))
                .role(Role.ADMIN)
                .build();

        Usuario usuario = Usuario.builder()
                .firstName("João")
                .lastName("Silva")
                .email("teste@livraria.com")
                .password(passwordEncoder.encode("senha123"))
                .role(Role.USER)
                .build();

        userRepository.saveAll(List.of(admin, usuario));
    }
}
