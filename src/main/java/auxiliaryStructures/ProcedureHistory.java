package auxiliaryStructures;

import java.util.Date;

public class ProcedureHistory {
    private Date date;
    private String genotype;
    private Integer alpha;
    private Integer beta;
    private Integer gamma;
    private String bacteriaClass;

    public ProcedureHistory() {
    }

    public ProcedureHistory(Date date, String genotype, Integer alpha, Integer beta, Integer gamma, String bacteriaClass) {
        this.date = date;
        this.genotype = genotype;
        this.alpha = alpha;
        this.beta = beta;
        this.gamma = gamma;
        this.bacteriaClass = bacteriaClass;
    }

    public Date getDate() {
        return date;
    }

    public String getGenotype() {
        return genotype;
    }

    public Integer getAlpha() {
        return alpha;
    }

    public Integer getBeta() {
        return beta;
    }

    public Integer getGamma() {
        return gamma;
    }

    public String getBacteriaClass() {
        return bacteriaClass;
    }
}
