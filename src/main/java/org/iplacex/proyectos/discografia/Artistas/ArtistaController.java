package org.iplacex.proyectos.discografia.Artistas;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.util.*;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class ArtistaController {


private final IArtistaRepository repo;


public ArtistaController(IArtistaRepository repo) {
this.repo = repo;
}

@PostMapping(value = "/artista", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<Object> HandleInsertArtistaRequest(@RequestBody Artistas body) {
try {
Artistas saved = repo.save(body);
return ResponseEntity.created(URI.create("/api/artista/" + saved._id)).body(saved);
} catch (Exception e) {
return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
}
}


@GetMapping(value = "/artistas", produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<List<Artistas>> HandleGetAristasRequest() {
return ResponseEntity.ok(repo.findAll());
}


@GetMapping(value = "/artista/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<Object> HandleGetArtistaRequest(@PathVariable String id) {
return repo.findById(id)
.<ResponseEntity<Object>>map(ResponseEntity::ok)
.orElseGet(() -> ResponseEntity.status(404).body(Map.of("error", "Artista no encontrado")));
}


@PutMapping(value = "/artista/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<Object> HandleUpdateArtistaRequest(@PathVariable String id, @RequestBody Artistas body) {
try {
if (!repo.existsById(id)) {
return ResponseEntity.status(404).body(Map.of("error", "Artista no existe"));
}
body._id = id; 
Artistas updated = repo.save(body);
return ResponseEntity.ok(updated);
} catch (Exception e) {
return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
}
}

@DeleteMapping(value = "/artista/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<Object> HandleDeleteArtistaRequest(@PathVariable String id) {
try {
if (!repo.existsById(id)) {
return ResponseEntity.status(404).body(Map.of("error", "Artista no existe"));
}
repo.deleteById(id);
return ResponseEntity.ok(Map.of("status", "eliminado"));
} catch (Exception e) {
return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
}
}
}