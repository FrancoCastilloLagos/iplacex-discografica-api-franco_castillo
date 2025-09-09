package org.iplacex.proyectos.discografia.Discos;

import org.iplacex.proyectos.discografia.Artistas.IArtistaRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.util.*;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class DiscoController {


private final IDiscoRepository discos;
private final IArtistaRepository artistas;


public DiscoController(IDiscoRepository discos, IArtistaRepository artistas) {
this.discos = discos;
this.artistas = artistas;
}

@PostMapping(value = "/disco", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<Object> HandlePostDiscoRequest(@RequestBody Disco body) {
try {
if (body.idArtista == null || body.idArtista.isBlank()) {
return ResponseEntity.badRequest().body(Map.of("error", "idArtista es requerido"));
}
if (!artistas.existsById(body.idArtista)) {
return ResponseEntity.status(400).body(Map.of("error", "El artista no existe"));
}
Disco saved = discos.save(body);
return ResponseEntity.created(URI.create("/api/disco/" + saved._id)).body(saved);
} catch (Exception e) {
return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
}
}

@GetMapping(value = "/discos", produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {
return ResponseEntity.ok(discos.findAll());
}

@GetMapping(value = "/disco/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<Object> HandleGetDiscoRequest(@PathVariable String id) {
return discos.findById(id)
.<ResponseEntity<Object>>map(ResponseEntity::ok)
.orElseGet(() -> ResponseEntity.status(404).body(Map.of("error", "Disco no encontrado")));
}

@GetMapping(value = "/artista/{id}/discos", produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(@PathVariable("id") String idArtista) {
return ResponseEntity.ok(discos.findDiscosByIdArtista(idArtista));
}
}