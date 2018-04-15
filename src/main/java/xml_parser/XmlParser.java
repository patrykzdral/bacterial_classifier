package xml_parser;

import auxiliaryStructures.ExaminedContainer;
import database.entity.Examined;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class XmlParser {

    public XmlParser() {
    }

    public void write(List<Examined> examinedBactriaList, String directoryPath) throws JAXBException {
        ExaminedContainer examinedBacteriaContainer = new ExaminedContainer(examinedBactriaList);
        JAXBContext jaxbContext = JAXBContext.newInstance(ExaminedContainer.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        File testTemplateFile = new File(directoryPath, generateFilename());
        jaxbMarshaller.marshal(examinedBacteriaContainer, testTemplateFile);
    }

    private String generateFilename() {
        return "examined_bacteria_list" + new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").format(new Date()) + ".xml";
    }
}
