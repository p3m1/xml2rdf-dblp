package eu.wdaqua.dblp;

import com.ctc.wstx.api.WstxInputProperties;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
//    public static String outputFile = "/home_expes/dd77474h/datasets/dblp_new/dump/dblp.nt";
//    public static String inputFile = "/home_expes/dd77474h/datasets/dblp_new/dump/dblp.xml";

//    Directory of tests on Pedro's computer
    public static String outputFile = "/home/pedro/Documentos/WDAqua/dblp.nt";
//    public static String inputFile = "/home/pedro/Documentos/WDAqua/dblp.xml";
    public static String inputFile = "/home/pedro/Documentos/WDAqua/personRecordsExample.xml";

    public static List typeList = Arrays.asList("article","proceedings","inproceedings","incollection","book","phdthesis","mastersthesis","www");
    public static List elementList = Arrays.asList("author","editor","title","booktitle","pages","year","address","journal","volume","number","month","url","ee","cdrom","cite","publisher","note","crossref","isbn","series","school","chapter","publnr");

    public static void main(String[] args) throws IOException, XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();

        factory.setProperty(XMLInputFactory.IS_VALIDATING, true);
        factory.setProperty(WstxInputProperties.P_MAX_ENTITY_COUNT, Integer.valueOf(999999999));

        FileInputStream fileXML = new FileInputStream(inputFile);

        XMLEventReader reader = factory.createXMLEventReader(inputFile, fileXML);

        String elementName = "";
        String type = "";

        Map<String,String> elements = new HashMap<>();
        Map<String,String> persons;

        persons = Manipulation.extractPersonRecords();

        Manipulation.writeVocabulary(typeList, elementList);

        String booktitle ="";

        String key ="";

        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            if (event.isStartElement()) {
                if(typeList.contains(event.asStartElement().getName().toString()))
                   type = event.asStartElement().getName().toString();
                elementName = event.asStartElement().getName().toString();
                Iterator attributes = event.asStartElement().getAttributes();
                while (attributes.hasNext()) {
                    Attribute attribute = (Attribute) attributes.next();
                    if(attribute.getName().toString().equals("key"))
                        key = attribute.getValue();
                    elements.put(attribute.getValue(), attribute.getName().toString());
                }
            }
            else if(event.isCharacters()){
                if(!event.asCharacters().getData().toString().equals("\n")) {
                    if(type.equals("inproceedings")) {
                        if (elementName.equals("booktitle"))
                            booktitle = event.asCharacters().getData().toString();
                        if (elementName.equals("url")) {
                            elements.put("http://dblp.uni-trier.de/" + event.asCharacters().getData().toString().split("#")[0], "booktitle");
                            elements.remove(booktitle);
                            booktitle = "";
                        }
                    }
                    if(elementName.equals("crossref")) {
                        String[] crossref = event.asCharacters().getData().toString().split("/");
                        key = "http://dblp.uni-trier.de/db/" + crossref[0] + "/" + crossref[1];
                        elements.put(key, elementName.toString());
                    }
                    else if(elementName.equals("journal")){
                        key = "http://dblp.uni-trier.de/db/" +key.split("/")[0] + "/" + key.split("/")[1];
                        elements.put(key, elementName.toString());
                    }
                    else
                        elements.put(event.asCharacters().getData().toString(), elementName.toString());
                }
            }
            else if(event.isEndElement()){
                if(event.asEndElement().getName().toString().contains(type)){

                    Manipulation.mapToRDF(type, elements, persons);
                    elements.clear();
                }
            }
        }
    }
}
