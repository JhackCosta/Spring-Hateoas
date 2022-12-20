package br.com.estudo.hateoas.Controllers;

//Precisa importar isso para funcionar;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estudo.hateoas.Entities.Usuario;
import br.com.estudo.hateoas.Services.UsuarioService;

/*add vem da classe que exttendemos
linkTo e method on criam os links para cada usuario
linkTo cria o link
methodon mostra qual metodo sera disparado, recebe dois parametros o controllers e o metodo a ser disparado
withSelfRel significa que sera um link para cada um dos usuarios
withRel significa que sera apenas um link, e passa o comentario da referencia */

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService service;
	
	@GetMapping(value = "/alluser")
	public ResponseEntity<Object> selectAll() {
		
		List<Usuario> lista =  service.SelectAllUser();
		
		if(lista.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
		
			for(Usuario user: lista) {
				long id = user.getId();

				user.add(linkTo(methodOn(UsuarioController.class).getSelectId(id)).withSelfRel());
			}
			return new ResponseEntity<>(lista, HttpStatus.OK);
		}
	}
	
	@GetMapping(value = "/user/{id}")
	public ResponseEntity<Object> getSelectId(@PathVariable(value = "id") Long id) {
		
		Optional<Usuario> userById =  service.SelectUser(id);
		
		if(!userById.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			userById.get().add(linkTo(methodOn(UsuarioController.class).selectAll()).withRel("Listas de Usuarios"));
			return new ResponseEntity<>(userById, HttpStatus.CREATED);
		}
	}
	
	@PostMapping(value = "/cadastro")
	public ResponseEntity<Usuario> saveUser(@RequestBody Usuario user) {
		
		Usuario userResponse = service.save(user);
		
		return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
	}
	
	
	
	
	

}
