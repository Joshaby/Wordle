package com.edu.ifpb.worldle.services;

import com.edu.ifpb.worldle.entities.Usuario;
import com.edu.ifpb.worldle.repositories.UsuarioRepository;
import com.edu.ifpb.worldle.services.exceptions.PasswordException;
import com.edu.ifpb.worldle.services.exceptions.UsuarioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public Usuario existsUsuario(String email, String password) {
        Optional<Usuario> usuario = repository.findByEmail(email);
        if (usuario.isPresent()) {
            if (!usuario.get().getPassword().equals(password)) {
                throw new PasswordException("Senha errada! Digite novamente!\n");
            }
            return usuario.get();
        }
        else {
            throw new UsuarioException("Usuário não existe! Você precisa se cadastrar\n");
        }
    }

    public Usuario save(Usuario usuario) {
        try {
            return repository.save(usuario);
        } catch (Exception e) {
            throw new UsuarioException("Já existe um usuário com esse email!\n");
        }
    }

    public Usuario update(Usuario usuario) {
        return repository.save(usuario);
    }
}
