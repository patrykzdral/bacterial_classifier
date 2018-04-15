package database.entity;

import database.Entity;

public class Toughness extends Entity {
    private Integer beta;
    private Integer gamma;
    private String rank;

    public Toughness() {
    }

    public Toughness(Integer beta, Integer gamma, String rank) {
        this.beta = beta;
        this.gamma = gamma;
        this.rank = rank;
    }
    public Toughness(Integer id,Integer beta, Integer gamma, String rank) {
        super(id);
        this.beta = beta;
        this.gamma = gamma;
        this.rank = rank;
    }

    public Integer getBeta() {
        return beta;
    }

    public void setBeta(Integer beta) {
        this.beta = beta;
    }

    public Integer getGamma() {
        return gamma;
    }

    public void setGamma(Integer gamma) {
        this.gamma = gamma;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
