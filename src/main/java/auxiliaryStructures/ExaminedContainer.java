package auxiliaryStructures;

import database.entity.Examined;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ExaminedContainer {
    @XmlElementWrapper(name = "examinedBacteriaList")
    @XmlElement(name = "bacteria")
    private List<Examined> examinedBacteriaList;

    public ExaminedContainer() {
    }

    public ExaminedContainer(List<Examined> examinedBacteriaList) {
        this.examinedBacteriaList = examinedBacteriaList;
    }

    public List<Examined> getExaminedBacteriaList() {
        return examinedBacteriaList;
    }

    public void setExaminedBacteriaList(List<Examined> examinedBacteriaList) {
        this.examinedBacteriaList = examinedBacteriaList;
    }

    @Override
    public String toString() {
        return "ExaminedBacteriaContainer{" +
                "examinedBacteriaList=" + examinedBacteriaList +
                '}';
    }
}
