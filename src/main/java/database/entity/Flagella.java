package database.entity;

import database.Entity;

public class Flagella extends Entity {
    private Integer alpha;
    private Integer beta;
    private Integer number;

    public Flagella() {
    }

    public Flagella( Integer alpha, Integer beta, Integer number) {
        this.alpha = alpha;
        this.beta = beta;
        this.number = number;
    }

    public Flagella(Integer id, Integer alpha, Integer beta, Integer number) {
        super(id);
        this.alpha = alpha;
        this.beta = beta;
        this.number = number;
    }

    public Integer getAlpha() {
        return alpha;
    }

    public void setAlpha(Integer alpha) {
        this.alpha = alpha;
    }

    public Integer getBeta() {
        return beta;
    }

    public void setBeta(Integer beta) {
        this.beta = beta;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }


}
