package com.compras.livrariaapi.controller;

import com.compras.livrariaapi.model.request.SignUpRequest;
import com.compras.livrariaapi.model.request.SigninRequest;
import com.compras.livrariaapi.model.request.UserLoggedResponse;
import com.compras.livrariaapi.model.request.UsuarioRequest;
import com.compras.livrariaapi.model.response.JwtAuthenticationResponse;
import com.compras.livrariaapi.model.response.UsuarioResponse;
import com.compras.livrariaapi.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Operações de login e autenticação de usuários")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("register")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("login")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }

    @GetMapping("users")
    public ResponseEntity<List<UsuarioResponse>> registeredUsers() {
        return ResponseEntity.ok().body(authenticationService.registeredUsers());
    }

    @GetMapping("loggedUser")
    public ResponseEntity<UserLoggedResponse> getUserLogged() {
        return ResponseEntity.ok().body(authenticationService.getUserLoggedSystem());
    }

    @PostMapping("registerUser")
    public ResponseEntity<UsuarioResponse> registerUser(@Valid @RequestBody UsuarioRequest usuarioRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.registerUser(usuarioRequest));
    }

    @GetMapping("user/{id}")
    public ResponseEntity<UsuarioResponse> getUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(authenticationService.getUser(id));
    }

    @PutMapping("updateUser/{id}")
    public ResponseEntity<UsuarioResponse> updateUser(@RequestBody UsuarioRequest usuarioRequest, @PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.updateUser(usuarioRequest, id));
    }

    @DeleteMapping("removeUser/{id}")
    public ResponseEntity<UsuarioResponse> removeUser(@PathVariable Long id) {
        UsuarioResponse UsuarioResponse = authenticationService.getUser(id);
        authenticationService.removeUser(UsuarioResponse);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(UsuarioResponse);
    }
}