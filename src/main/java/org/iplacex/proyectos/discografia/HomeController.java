package org.iplacex.proyectos.discografia;

import org.iplacex.proyectos.discografia.Artistas.Artistas;
import org.iplacex.proyectos.discografia.Artistas.IArtistaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {

    private final IArtistaRepository repo;

    public HomeController(IArtistaRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/")  
    public List<Artistas> home() {
        return repo.findAll(); 
    }
}
