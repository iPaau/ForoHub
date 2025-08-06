
package com.hub.foro.forohub.topico;

import com.hub.foro.forohub.usuario.Usuario;
import com.hub.foro.forohub.usuario.UsuarioRepository;
import com.hub.foro.forohub.curso.Curso;
import com.hub.foro.forohub.curso.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class TopicoService {

    @Transactional
    public void eliminarTopico(Long id) {
        topicoRepository.deleteById(id);
    }

    @Transactional
    public Topico actualizarTopico(Long id, TopicoUpdateRequest request) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tópico no encontrado"));
        // Verificar duplicados (excluyendo el propio tópico)
        topicoRepository.findByTituloAndMensaje(request.titulo(), request.mensaje())
                .filter(t -> !t.getId().equals(id))
                .ifPresent(t -> { throw new IllegalArgumentException("Ya existe un tópico con el mismo título y mensaje."); });
        Usuario autor = usuarioRepository.findById(request.autorId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        Curso curso = cursoRepository.findById(request.cursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));
        topico.setTitulo(request.titulo());
        topico.setMensaje(request.mensaje());
        topico.setAutor(autor);
        topico.setCurso(curso);
        return topicoRepository.save(topico);
    }
    public java.util.Optional<Topico> topicoPorId(Long id) {
        return topicoRepository.findById(id);
    }
    public Page<Topico> listarTopicos(Pageable pageable) {
        return topicoRepository.findAll(pageable);
    }
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CursoRepository cursoRepository;

    @Transactional
    public Topico registrarTopico(TopicoRequest request) {
        if (topicoRepository.findByTituloAndMensaje(request.titulo(), request.mensaje()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un tópico con el mismo título y mensaje.");
        }
        Usuario autor = usuarioRepository.findById(request.autorId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        Curso curso = cursoRepository.findById(request.cursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));
        Topico topico = new Topico();
        topico.setTitulo(request.titulo());
        topico.setMensaje(request.mensaje());
        topico.setAutor(autor);
        topico.setCurso(curso);
        return topicoRepository.save(topico);
    }
}
