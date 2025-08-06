package com.hub.foro.forohub.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
    @NotBlank String correoElectronico,
    @NotBlank String contrasena
) {}
