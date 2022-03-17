package uy.com.sofka.tallerReactiva.model;

public class Jugador {
    public int id;
    public String name;
    public int age;
    public String icon;
    public String national;
    public int winners;
    public int games;
    public String club;

    public Jugador(){

    }

    public Jugador(int id, String name, int age, String icon, String national, int winners, int games, String club) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.icon = icon;
        this.national = national;
        this.winners = winners;
        this.games = games;
        this.club = club;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public String getNational() {
        return national;
    }
    public String getClub() {
        return club;
    }
}
