package com.hub.foro.forohub.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TopicoRequest(
    @NotBlank String titulo,
    @NotBlank String mensaje,
    @NotNull Long autorId,
    @NotNull Long cursoId
) {}
