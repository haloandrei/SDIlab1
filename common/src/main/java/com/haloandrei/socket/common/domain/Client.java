package com.haloandrei.socket.common.domain;

import java.util.Objects;

public class Client extends BaseEntity<Long>{
    private String name;
    private double moneySpent;

    public Client() {
    }

    public Client(String name, double moneySpent) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return Double.compare(client.getMoneySpent(), getMoneySpent()) == 0 &&
                getName().equals(client.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getMoneySpent());
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", moneySpent=" + moneySpent +
                '}';
    }

}
