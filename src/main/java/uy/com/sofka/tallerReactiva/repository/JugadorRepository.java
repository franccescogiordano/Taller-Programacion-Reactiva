package uy.com.sofka.tallerReactiva.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import uy.com.sofka.tallerReactiva.model.JugadorMongo;

public interface JugadorRepository extends ReactiveMongoRepository<JugadorMongo, String> {

}
