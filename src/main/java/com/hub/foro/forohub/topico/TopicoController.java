package com.hub.foro.forohub.topico;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTopico(@PathVariable Long id) {
        if (topicoService.topicoPorId(id).isPresent()) {
            topicoService.eliminarTopico(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarTopico(@PathVariable Long id, @RequestBody @Valid TopicoUpdateRequest request) {
        try {
            Topico topico = topicoService.actualizarTopico(id, request);
            TopicoResponse response = new TopicoResponse(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                topico.getAutor() != null ? topico.getAutor().getNombre() : null,
                topico.getCurso() != null ? topico.getCurso().getNombre() : null
            );
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalleTopico(@PathVariable Long id) {
        return topicoService.topicoPorId(id)
                .map(topico -> ResponseEntity.ok(new TopicoResponse(
                        topico.getId(),
                        topico.getTitulo(),
                        topico.getMensaje(),
                        topico.getFechaCreacion(),
                        topico.getStatus(),
                        topico.getAutor() != null ? topico.getAutor().getNombre() : null,
                        topico.getCurso() != null ? topico.getCurso().getNombre() : null
                )))
                .orElse(ResponseEntity.notFound().build());
    }
    @Autowired
    private TopicoService topicoService;

    @GetMapping
    public Page<TopicoResponse> listarTopicos(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("fechaCreacion").ascending());
        return topicoService.listarTopicos(pageable)
                .map(topico -> new TopicoResponse(
                        topico.getId(),
                        topico.getTitulo(),
                        topico.getMensaje(),
                        topico.getFechaCreacion(),
                        topico.getStatus(),
                        topico.getAutor() != null ? topico.getAutor().getNombre() : null,
                        topico.getCurso() != null ? topico.getCurso().getNombre() : null
                ));
    }

    @PostMapping
    public ResponseEntity<?> registrarTopico(@RequestBody @Valid TopicoRequest request) {
        try {
            Topico topico = topicoService.registrarTopico(request);
            TopicoResponse response = new TopicoResponse(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                topico.getAutor() != null ? topico.getAutor().getNombre() : null,
                topico.getCurso() != null ? topico.getCurso().getNombre() : null
            );
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
