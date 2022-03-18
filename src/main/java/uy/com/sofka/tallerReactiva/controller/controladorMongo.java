package uy.com.sofka.tallerReactiva.controller;


import org.springframework.beans.BeanUtils;
import uy.com.sofka.tallerReactiva.model.Jugador;
import uy.com.sofka.tallerReactiva.model.JugadorMongo;

public class controladorMongo {
    public static JugadorMongo jugadorMongoAJugador(Jugador jugador) {
        JugadorMongo jugadorMongo = new JugadorMongo();
        BeanUtils.copyProperties(jugador,jugadorMongo);
        return jugadorMongo;
    }

    public static Jugador mongoToJugador(JugadorMongo mongojugador) {
        Jugador jugador = new Jugador();
        BeanUtils.copyProperties(mongojugador,jugador);
        return jugador;
    }
}
