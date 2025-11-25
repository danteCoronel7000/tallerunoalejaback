package com.taller1.colegioJMA2.controller;

import com.taller1.colegioJMA2.configuration.JwtUtil;
import com.taller1.colegioJMA2.dto.LoginResponseDTO;
import com.taller1.colegioJMA2.dto.PasswordUpdateRequestDTO;
import com.taller1.colegioJMA2.model.DatosModel;
import com.taller1.colegioJMA2.model.PersonalModel;
import com.taller1.colegioJMA2.model.UsuariosModel;
import com.taller1.colegioJMA2.repository.UsuariosRepo;
import com.taller1.colegioJMA2.service.usuarioService;
import com.taller1.colegioJMA2.utils.AuthRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuariosController {

	@Autowired
	private UsuariosRepo usuariosRepo;
	@Autowired
	private usuarioService usuarioservice;

	// GET
	@GetMapping()
	public List<UsuariosModel> ListarUsuarios() {
		return usuariosRepo.findAll();
	}

	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;
	private final JwtUtil jwtUtil;

	public UsuariosController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService,
							  JwtUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.jwtUtil = jwtUtil;
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody AuthRequest request) {
		// 1. Autenticación
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		// 2. Carga los detalles de usuario de Spring Security
		final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

		// 3. Obtiene el objeto completo de UsuariosModel para acceder a Personal y
		// Roles
		UsuariosModel usuario = usuariosRepo.findById(request.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

		// 4. Genera el JWT
		String token = jwtUtil.generateToken(userDetails.getUsername());

		// 5. Extraer el objeto Personal y el nombre corto
		PersonalModel personal = usuario.getPersonal();
		String nombre = (personal != null) ? personal.getNombre() : usuario.getLogin();

		// 5a. Obtener la cédula como String
		String cedulaString = null;
		if (personal != null && personal.getDatos() != null) {
			cedulaString = personal.getDatos().getCedula();
		}

		// 5b. Crear la lista de String de la cédula
		List<String> cedulaList = (cedulaString != null) ? Collections.singletonList(cedulaString)
				: Collections.emptyList();

		// 6. Construye la respuesta con los 6 argumentos
		LoginResponseDTO responseDTO = new LoginResponseDTO(token, usuario.getLogin(), nombre, cedulaList, personal,
				usuario.getRoleNames());

		return ResponseEntity.ok(responseDTO);
	}
	// creacion de usuarios
	//@PostMapping("/api/usuarios")
	@PostMapping("/create")
	public ResponseEntity<?> CrearUsuario(@RequestBody UsuariosModel nuevoUsuario) {

		PersonalModel personal = nuevoUsuario.getPersonal();

		if (personal != null && personal.getDatos() != null) {
			personal.getDatos().setPersonal(personal);
			DatosModel datos = personal.getDatos();

			datos.setPersonal(personal);
		}
		try {
			UsuariosModel usuarioGuardado = usuariosRepo.save(nuevoUsuario);
			return new ResponseEntity<>(usuarioGuardado, HttpStatus.CREATED); // Retorna 201 Created

		} catch (DataIntegrityViolationException e) {
			String detail = e.getRootCause().getMessage();
			String errorMessage = "Error de Persistencia: Verifique la clave primaria (login), claves foráneas (roles) o campos NOT NULL. ";

			if (detail != null && (detail.contains("usuarios_pkey")
					|| detail.contains("llave duplicada") && detail.contains("login"))) {
				errorMessage = "Error de Duplicidad: El login ('" + nuevoUsuario.getLogin()
						+ "') ya está registrado como usuario.";
			} else if (detail != null && detail.contains("llave duplicada")) {
				String cedula = nuevoUsuario.getPersonal() != null && nuevoUsuario.getPersonal().getDatos() != null
						&& nuevoUsuario.getPersonal().getDatos().getId() != null
								? nuevoUsuario.getPersonal().getDatos().getId().getCedula()
								: "una cédula";

				errorMessage = "Error de Duplicidad: La Cédula ('" + cedula
						+ "') ya está asociada a otra persona en el sistema.";
			} else {
				errorMessage += " Detalle: " + detail;
			}

			System.err.println("Error de Integridad de Datos: " + errorMessage);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);

		} catch (Exception e) {
			System.err.println("Error al crear el usuario: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					"Error al crear el usuario. Verifique el formato de los datos o si faltan campos obligatorios. Detalle: "
							+ e.getMessage());
		}
	}

	// PUT: para activar y desactivar
	@PutMapping("/api/usuarios/estado/{login}/{nuevoEstado}")
	public ResponseEntity<Void> CambiarEstadoUsuario(@PathVariable String login, @PathVariable int nuevoEstado) {

		if (nuevoEstado != 0 && nuevoEstado != 1) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El estado debe ser 0 (Desactivo) o 1 (Activo).");
		}

		Optional<UsuariosModel> usuarioOpt = usuariosRepo.findById(login);

		if (usuarioOpt.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado.");
		}

		UsuariosModel usuario = usuarioOpt.get();

		if (usuario.getEstado() == nuevoEstado) {
			return ResponseEntity.ok().build();
		}
		usuario.setEstado(nuevoEstado);
		usuariosRepo.save(usuario);

		return ResponseEntity.ok().build();
	}

	// DELETE
	@DeleteMapping("/{login}")
	public ResponseEntity<String> EliminarUsuario(@PathVariable String login) {
		try {
			usuariosRepo.deleteById(login);

			return ResponseEntity.ok("El usuario con login '" + login + "' se ha eliminado correctamente.");

		} catch (EmptyResultDataAccessException e) {
			String errorMessage = "Error al eliminar: No se encontró el usuario con login '" + login + "'.";
			System.err.println(errorMessage);

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);

		}
	}

	// PUT
	@PutMapping("/{login}")
	public void ActualizarUsuario(@RequestBody UsuariosModel usuario, @PathVariable String login) {
		usuario.setLogin(login);
		usuariosRepo.save(usuario);
	}

	@PutMapping("/api/usuarios/password/{login}")
	public ResponseEntity<?> ModificarPassword(@RequestBody PasswordUpdateRequestDTO request,
			@PathVariable String login) {

		Optional<UsuariosModel> usuarioOpt = usuariosRepo.findById(login);

		if (usuarioOpt.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Usuario no encontrado para modificar la contraseña.");
		}

		UsuariosModel usuario = usuarioOpt.get();

		String rawPassword = request.getNewPassword();
		if (rawPassword == null || rawPassword.trim().isEmpty()) {
			return ResponseEntity.badRequest().body("La nueva contraseña no puede estar vacía.");
		}

		usuario.setPassword(rawPassword);

		usuariosRepo.save(usuario);

		return ResponseEntity.ok().build();
	}
	// GET: paginado
	@GetMapping("/get/paginado")
    public ResponseEntity<Page<UsuariosModel>> getAllUsuarioPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "login") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                sortDir.equalsIgnoreCase("desc") ?
                        Sort.by(sortBy).descending() :
                        Sort.by(sortBy).ascending()
        );

        Page<UsuariosModel> usuarios = usuarioservice.getUsuarioPaginados(pageable);
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
}
