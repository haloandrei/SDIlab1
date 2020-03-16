package domain;

import java.util.List;
import java.util.Objects;

public class Client extends BaseEntity<Long>{
    private String name;
    private List<String> rented_movies;
    private double moneySpent;

    public Client() {
    }

    public Client(String name, List<String> rented_movies, double moneySpent) {
        this.name = name;
        this.rented_movies = rented_movies;
        this.moneySpent = moneySpent;
    }

    public double getMoneySpent() {
        return moneySpent;
    }

    public void setMoneySpent(double moneySpent) {
        this.moneySpent = moneySpent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getRented_movies() {
        return rented_movies;
    }

    public void setRented_movies(List<String> rented_movies) {
        this.rented_movies = rented_movies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return Double.compare(client.getMoneySpent(), getMoneySpent()) == 0 &&
                getName().equals(client.getName()) &&
                getRented_movies().equals(client.getRented_movies());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getRented_movies(), getMoneySpent());
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", rented_movies=" + rented_movies +
                ", moneySpent=" + moneySpent +
                '}';
    }

}
