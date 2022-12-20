package br.com.estudo.hateoas.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.estudo.hateoas.Entities.Usuario;
import br.com.estudo.hateoas.Repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	public List<Usuario> SelectAllUser() {
		return repository.findAll();
	}
	
	public Optional<Usuario> SelectUser(Long id) {
		return repository.findById(id);
	}
	
	public Usuario save(Usuario user) {
		return repository.save(user);
	}
	
}
