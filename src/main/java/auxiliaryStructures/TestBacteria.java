package auxiliaryStructures;

public class TestBacteria {
    private Integer genotype;
    private Integer alpha;
    private Integer beta;
    private Integer gamma;

    public TestBacteria (Integer genotype){
        this.genotype=genotype;
        convert();
    }
    public Integer getGenotype() {
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

    private void convert(){
        String temp;
        temp=String.valueOf(Integer.toString(genotype).charAt(0))+String.valueOf(Integer.toString(genotype).charAt(5));
        alpha=Integer.parseInt(temp);

        temp=String.valueOf(Integer.toString(genotype).charAt(1))+String.valueOf(Integer.toString(genotype).charAt(4));
        beta=Integer.parseInt(temp);

        temp=String.valueOf(Integer.toString(genotype).charAt(2))+String.valueOf(Integer.toString(genotype).charAt(3));
        gamma=Integer.parseInt(temp);
    }
}

