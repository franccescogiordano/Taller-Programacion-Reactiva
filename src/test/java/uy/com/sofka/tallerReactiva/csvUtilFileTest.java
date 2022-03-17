package uy.com.sofka.tallerReactiva;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uy.com.sofka.tallerReactiva.model.Jugador;
import java.util.*;
import java.util.stream.Collectors;
import  com.example.demo.csvUtilFile;

class csvUtilFileTest {
    @Test
    void converterData(){
        List<Jugador> list =csvUtilFile.getPlayers();
        assert list.size() == 18207;
    }

    @Test
    void stream_filtrarJugadoresMayoresA35(){
        List<Jugador> list = csvUtilFile.getPlayers();
        Map<String, List<Jugador>> listFilter = list.parallelStream()
                .filter(player -> player.age >= 35)
                .map(player -> {
                    player.name = player.name.toUpperCase(Locale.ROOT);
                    return player;
                })
                .flatMap(playerA -> list.parallelStream()
                        .filter(playerB -> playerA.club.equals(playerB.club))
                )
                .distinct()
                .collect(Collectors.groupingBy(Jugador::getClub));

        assert listFilter.size() == 322;
    }


    @Test
    void reactive_filtrarJugadoresMayoresA35(){
        List<Jugador> list = csvUtilFile.getPlayers();
        Flux<Jugador> listFlux = Flux.fromStream(list.parallelStream()).cache();
        Mono<Map<String, Collection<Jugador>>> listFilter = listFlux
                .filter(player -> player.age >= 35)
                .map(player -> {
                    player.name = player.name.toUpperCase(Locale.ROOT);
                    return player;
                })
                .buffer(100)
                .flatMap(playerA -> listFlux
                        .filter(playerB -> playerA.stream()
                                .anyMatch(a ->  a.club.equals(playerB.club)))
                )
                .distinct()
                .collectMultimap(Jugador::getClub);

        assert listFilter.block().size() == 322;
    }

}