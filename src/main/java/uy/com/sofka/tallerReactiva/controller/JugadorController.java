package uy.com.sofka.tallerReactiva.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import uy.com.sofka.tallerReactiva.model.JugadorMongo;
import uy.com.sofka.tallerReactiva.service.JugadorService;
import java.util.List;
@RestController
@CrossOrigin("*")
@RequestMapping("/api")

public class JugadorController {
    @Autowired
    private JugadorService jugadorService;

    @GetMapping(value = "/jugadores")
    public Flux<JugadorMongo> obtenerJugadores(){
        return jugadorService.obtenerJugadores();
    }

    @GetMapping(value = "/players/filtrarporedad")
    public Flux<JugadorMongo> jugadoresMayoresA34(){
        return jugadorService.obtenerJugadoresMayoresA34AñosDeEdad();
    }
    @GetMapping(value = "/players/filtrarporclub/{club}")
    public Flux<JugadorMongo> jugadoresPorClub(@PathVariable("club") String club){
        return jugadorService.obtenerJugadoresPorClub(club);
    }
    @GetMapping(value = "/jugadoresporpais")
    public Flux<List<JugadorMongo>> jugadoresPorPais(){
        return jugadorService.ordenarJugadoresPorNacionalidadYPuntaje();
    }

}



