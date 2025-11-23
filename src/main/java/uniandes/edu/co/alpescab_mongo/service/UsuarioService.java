package uniandes.edu.co.alpescab_mongo.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import uniandes.edu.co.alpescab_mongo.model.Usuario;
import uniandes.edu.co.alpescab_mongo.repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(ObjectId id) {
        return usuarioRepository.findById(id);
    }

    public Usuario create(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> update(ObjectId id, Usuario usuario) {
        return usuarioRepository.findById(id)
                .map(existing -> {
                    usuario.setId(id);
                    return usuarioRepository.save(usuario);
                });
    }

    public void delete(ObjectId id) {
        usuarioRepository.deleteById(id);
    }
}
