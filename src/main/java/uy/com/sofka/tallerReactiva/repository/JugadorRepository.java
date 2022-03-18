package uy.com.sofka.tallerReactiva.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import uy.com.sofka.tallerReactiva.model.Jugador;

@Repository
public interface JugadorRepository extends ReactiveMongoRepository<Jugador, String> {

}
