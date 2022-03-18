package uy.com.sofka.tallerReactiva.service;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uy.com.sofka.tallerReactiva.controller.controladorMongo;

import org.springframework.stereotype.Service;


import uy.com.sofka.tallerReactiva.model.Jugador;
import uy.com.sofka.tallerReactiva.model.JugadorMongo;
import uy.com.sofka.tallerReactiva.repository.JugadorRepository;

@Service
public class JugadorService {

    @Autowired
   private JugadorRepository jugadorRepository;
    public Flux<JugadorMongo> obtenerJugadoresMayoresA34AñosDeEdad(){
        Flux<JugadorMongo> playersOlderThan34 = jugadorRepository.findAll()
                .buffer(120)
                .flatMap(jugador -> Flux.fromStream(jugador.parallelStream()))
                .filter(jugador -> jugador.getAge() > 34).map(controladorMongo::jugadorMongoAJugador);
        return playersOlderThan34;
    }

    public Flux<JugadorMongo> obtenerJugadores(){
        Flux<JugadorMongo> jugadores = jugadorRepository.findAll()
                .buffer(120)
                .flatMap(jugador -> Flux.fromStream(jugador.parallelStream()).map(controladorMongo::jugadorMongoAJugador));
        return jugadores;
    }

    public Flux<JugadorMongo> obtenerJugadoresPorClub(String club){
        Flux<JugadorMongo> playersByClub = jugadorRepository.findAll()
                .buffer(120)
                .flatMap(player -> Flux.fromStream(player.parallelStream()))
                .filter(playerNoNullClub -> Objects.nonNull(playerNoNullClub.getClub()))
                .filter(jugador -> jugador.getClub().equals(club)).map(controladorMongo::jugadorMongoAJugador);
        return playersByClub;
    }

    public Flux<List<JugadorMongo>> ordenarJugadoresPorNacionalidadYPuntaje(){
        Flux<List<JugadorMongo>> jugadoresPorPais = jugadorRepository
                .findAll().map(controladorMongo::jugadorMongoAJugador)
                .buffer(120)
                .flatMap(jugador -> Flux.fromStream(jugador.parallelStream()))
                .distinct().groupBy(JugadorMongo::getNational)
                .flatMap(Flux::collectList).map(lista ->
                {
                    lista.sort(Comparator.comparingDouble(JugadorMongo::getRanking));
                    return lista;
                });
        return jugadoresPorPais;
    }

    public Mono<JugadorMongo> guardarJugador(Mono<JugadorMongo> jugador) {

        return jugador.map(controladorMongo::mongoToJugador)
                .flatMap(jugadorRepository::insert)
                .map(controladorMongo::jugadorMongoAJugador);
    }
}
