package database.entity;

import database.Entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Examined extends Entity {
    @XmlAttribute(name = "bacteriaGenotype")
    private String genotype;
    @XmlAttribute(name = "bacteriaClass")
    private String bclass;
    private Integer flagellaId;
    private Integer toughnessId;
    public Examined() {
    }

    public Examined(String genotype, String bclass) {
        this.genotype = genotype;
        this.bclass = bclass;
    }
    public Examined(Integer id,String genotype, String bclass, Integer flagellaId, Integer toughnessId) {
        super(id);
        this.genotype = genotype;
        this.bclass = bclass;
    }

    public Examined(String genotype, String bclass, Integer flagellaId, Integer toughnessId) {
        this.genotype = genotype;
        this.bclass = bclass;
        this.flagellaId=flagellaId;
        this.toughnessId=toughnessId;
    }

    public String getGenotype() {
        return genotype;
    }

    public void setGenotype(String genotype) {
        this.genotype = genotype;
    }

    public String getBclass() {
        return bclass;
    }

    public void setBclass(String bclass) {
        this.bclass = bclass;
    }

    public Integer getFlagellaId() {
        return flagellaId;
    }

    public void setFlagellaId(Integer flagellaId) {
        this.flagellaId = flagellaId;
    }

    public Integer getToughnessId() {
        return toughnessId;
    }

    public void setToughnessId(Integer toughnessId) {
        this.toughnessId = toughnessId;
    }
}
