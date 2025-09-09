package org.iplacex.proyectos.discografia.Artistas;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface IArtistaRepository extends MongoRepository<Artistas, String> {
}