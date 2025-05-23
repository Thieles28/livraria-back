package com.compras.livrariaapi.service;

import com.compras.livrariaapi.model.request.SignUpRequest;
import com.compras.livrariaapi.model.request.SigninRequest;
import com.compras.livrariaapi.model.request.UserLoggedResponse;
import com.compras.livrariaapi.model.request.UsuarioRequest;
import com.compras.livrariaapi.model.response.JwtAuthenticationResponse;
import com.compras.livrariaapi.model.response.UsuarioResponse;

import java.util.List;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);

    List<UsuarioResponse> registeredUsers();

    UserLoggedResponse getUserLoggedSystem();

    UserLoggedResponse getUserLoggedInSystem(String email);

    UsuarioResponse getUser(Long id);

    UsuarioResponse registerUser(UsuarioRequest users);

    UsuarioResponse updateUser(UsuarioRequest usersRequest, Long id);

    void removeUser(UsuarioResponse usersResponse);
}