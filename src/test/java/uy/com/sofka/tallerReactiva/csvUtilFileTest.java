package uy.com.sofka.tallerReactiva;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uy.com.sofka.tallerReactiva.model.Jugador;

import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.csvUtilFile;

class csvUtilFileTest {
    @Test
    void converterData() {
        List<Jugador> list = csvUtilFile.getPlayers();
        assert list.size() == 18207;
    }

    @Test
    void stream_filtrarJugadoresMayoresA35() {
        List<Jugador> list = csvUtilFile.getPlayers();
        Map<String, List<Jugador>> listFilter = list.parallelStream()
                .filter(jugador -> jugador.age >= 35)
                .map(jugador -> {
                    jugador.name = jugador.name.toUpperCase(Locale.ROOT);
                    return jugador;
                })
                .flatMap(playerA -> list.parallelStream()
                        .filter(playerB -> playerA.club.equals(playerB.club))
                )
                .distinct()
                .collect(Collectors.groupingBy(Jugador::getClub));

        assert listFilter.size() == 322;
    }
    //FUNCIONES SOLICITADAS AQUI ABAJO
    @Test
    void JugadoresMayoresA34filtro() {
        List<Jugador> list = csvUtilFile.getPlayers();
        Flux<Jugador> listFlux = Flux.fromStream(list.parallelStream()).cache();
        Mono<Map<String, Collection<Jugador>>> listFilter = listFlux
                .filter(jugador -> jugador.age >= 34)
                .distinct()
                .collectMultimap(Jugador::getName);
        System.out.println("Jugadores Mayores a 34 años o de 34 años");
        listFilter.block().forEach((jugadores, players) -> {
            players.forEach(jugador -> {
                System.out.println("Nombre: " + jugador.name + " edad: " + jugador.age + " años");
            });
        });
    }
    @Test
    void porClub() {
        List<Jugador> list = csvUtilFile.getPlayers();
        Flux<Jugador> listFlux = Flux.fromStream(list.parallelStream()).cache();
        Mono<Map<String, Collection<Jugador>>> listFilter = listFlux
                .filter(jugador -> jugador.club.equals("Milan"))
                .distinct()
                .collectMultimap(Jugador::getClub);
        System.out.print("Equipo: ");
        listFilter.block().forEach((equipo, players) -> {
            System.out.println(equipo);
            players.forEach(jugador -> {
                System.out.println("Nombre: " + jugador.name);
                assert jugador.club.equals("Milan");
            });
        });
        assert listFilter.block().size() == 1;
    }
    @Test
    void obtenerPorRankingDeWinsYNacionalidad() {
        List<Jugador> list = csvUtilFile.getPlayers();
        Flux<Jugador> listFlux = Flux.fromStream(list.parallelStream()).cache();
        Mono<Map<String, Collection<Jugador>>> listFilter = listFlux
                .buffer(100)
                .flatMap(jugardor1 -> listFlux
                        .filter(jugador2 -> jugardor1.stream()
                                .anyMatch(a -> a.national.equals(jugador2.national)))).distinct()
                .sort((k,jugador) -> jugador.winners).collectMultimap(Jugador::getNational);
        System.out.println("Por Nacionalidad: ");
        System.out.println(listFilter.block().size());
        listFilter.block().forEach((pais, players) -> {
            System.out.println("Pais (" + pais + ")");
            players.forEach(jugador -> {
                System.out.println("Nombre: " + jugador.name + " wins: " + jugador.winners);
            });
        });
    }
}