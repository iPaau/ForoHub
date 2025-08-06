package com.hub.foro.forohub.topico;

import java.time.LocalDateTime;

public record TopicoResponse(
    Long id,
    String titulo,
    String mensaje,
    LocalDateTime fechaCreacion,
    String status,
    String autor,
    String curso
) {}
