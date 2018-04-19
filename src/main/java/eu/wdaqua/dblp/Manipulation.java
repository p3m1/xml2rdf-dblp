package eu.wdaqua.dblp;

import com.ctc.wstx.api.WstxInputProperties;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Manipulation {

    public static void mapToRDF(String type, Map<String, String> elements, Map<String, String> persons) throws IOException {

        StringBuffer writer = new StringBuffer();

        String elementPattern = "";
        if (type.equals("article")) {
            elementPattern = "<https://dblp.org/rec/html/";
        } else if (type.equals("www")) {
            elementPattern = "<https://dblp.org/pid/";
        }
        String finalElementPattern = elementPattern;
        elements.entrySet().forEach((pair) -> {
            switch (pair.getKey()) {
                case ConstantList.MDATE:
                    writer.append(
                            Utility.MDATE +
                                    "\"" + elements.get("mdate") + "\"" +
                                    "^^<http://www.w3.org/2001/XMLSchema#date> ." +
                                    "\n");
                    break;
                case ConstantList.AUTHOR:
                    writer.append(finalElementPattern + elements.get("key") + ">");
                    if (!type.equals("www")) {
                        writer.append(Utility.AUTHOR);
                        writer.append("<" + persons.get(elements.get("author")) + "> .\n");
                    }
                    else {
                        writer.append(Utility.NAME);
                        writer.append("\"" + elements.get("author") + "\"" + "^^<http://www.w3.org/2001/XMLSchema#string> .\n");
                    }
                    break;
                case ConstantList.TITLE:
                    writer.append(
                            Utility.TITLE + "\"" + elements.get("title") + "\"" + "^^<http://www.w3.org/2001/XMLSchema#string> .\n");
                    break;
                case ConstantList.PAGES:
                    writer.append(
                            Utility.PAGES + "\"" + elements.get("pages") + "\"" + "^^<http://www.w3.org/2001/XMLSchema#string> .\n");
                    break;
                case ConstantList.YEAR:
                    writer.append(
                            Utility.YEAR + "\"" + elements.get("year") + "\"" + "^^<http://www.w3.org/2001/XMLSchema#gYear> ." + "\n");
                    break;
                case ConstantList.VOLUME:
                    writer.append(
                            Utility.VOLUME + "\"" + elements.get("volume") + "\"" + "^^<http://www.w3.org/2001/XMLSchema#string> .\n");
                    break;
                case ConstantList.JOURNAL:
                    writer.append(
                            Utility.JOURNAL + "\"" + elements.get("journal") + "\"" + "^^<http://www.w3.org/2001/XMLSchema#string> .\n");
                    break;
                case ConstantList.NUMBER:
                    writer.append(
                            Utility.NUMBER + "\"" + elements.get("number") + "\"" + "^^<http://www.w3.org/2001/XMLSchema#string> .\n");
                    break;
                case ConstantList.URL:
                    writer.append(finalElementPattern + elements.get("key") + ">");
                    if (!type.equals("www"))
                        writer.append(Utility.URL);
                    else
                        writer.append(Utility.URL_WWW);
                    writer.append("\"" + elements.get("url") + "\"" + "^^<http://www.w3.org/2001/XMLSchema#string> .\n");
                    break;
                case ConstantList.EE:
                    writer.append(
                            Utility.EE + "<" + elements.get("ee") + "> .\n");
                    break;
                case ConstantList.NOTE:
                    writer.append(
                            Utility.NOTE + "<" + elements.get("note") + "> .\n");
                    break;
                case ConstantList.CITE:
                    writer.append(
                            Utility.CITE + "<" + elements.get("cite") + "> .\n");
                    break;
                case ConstantList.MONTH:
                    writer.append(
                            Utility.MONTH + "\"" + elements.get("month") + "\"" + "^^<https://www.w3.org/2001/XMLSchema#gMonth> .\n");
                    break;
                case ConstantList.ADDRESS:
                    writer.append(
                            Utility.ADDRESS + "\"" + elements.get("address") + "\"" + "^^<http://www.w3.org/2001/XMLSchema#string> .\n");
                    break;
                case ConstantList.EDITOR:
                    writer.append(
                            Utility.EDITOR + "\"" + elements.get("editor") + "\"" + "^^<http://www.w3.org/2001/XMLSchema#string> .\n");
                    break;
                case ConstantList.PUBLISHER:
                    writer.append(
                            Utility.PUBLISHER + "\"" + elements.get("publisher") + "\"" + "^^<http://www.w3.org/2001/XMLSchema#string> .\n");
                    break;
                case ConstantList.BOOKTITLE:
                    writer.append(
                            Utility.BOOKTITLE + "\"" + elements.get("booktitle") + "\"" + "^^<http://www.w3.org/2001/XMLSchema#string> .\n");
                    break;
                case ConstantList.CDROM:
                    writer.append(
                            Utility.CDROM + "\"" + elements.get("cdrom") + "\"" + "^^<http://www.w3.org/2001/XMLSchema#string> .\n");
                    break;
                case ConstantList.CROSSREF:
                    writer.append(
                            Utility.CROSSREF + "\"" + elements.get("crossref") + "\"" + "^^<http://www.w3.org/2001/XMLSchema#string> .\n");
                    break;
                case ConstantList.ISBN:
                    writer.append(
                            Utility.ISBN + "\"" + elements.get("isbn") + "\"" + "^^<http://www.w3.org/2001/XMLSchema#string> .\n");
                    break;
                case ConstantList.SERIES:
                    writer.append(
                            Utility.SERIES + "\"" + elements.get("series") + "\"" + "^^<http://www.w3.org/2001/XMLSchema#string> .\n");
                    break;
                case ConstantList.SCHOOL:
                    writer.append(
                            Utility.SCHOOL + "\"" + elements.get("school") + "\"" + "^^<http://www.w3.org/2001/XMLSchema#string> .\n");
                    break;
                case ConstantList.CHAPTER:
                    writer.append(
                            Utility.CHAPTER + "\"" + elements.get("chapter") + "\"" + "^^<http://www.w3.org/2001/XMLSchema#string> .\n");
                    break;
                case ConstantList.PUBLNR:
                    writer.append(
                            Utility.PUBLNR + "\"" + elements.get("publnr") + "\"" + "^^<http://www.w3.org/2001/XMLSchema#string> .\n");
                    break;
                default:
                    break;
            }
        });

        writer.append(
                " <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ");
        switch (type) {
            case ConstantList.ARTICLE:
                writer.append(Utility.ARTICLE + ".\n");
                break;
            case ConstantList.PROCEEDINGS:
                writer.append(Utility.PROCEEDINGS + ".\n");
                break;
            case ConstantList.INPROCEEDINGS:
                writer.append(Utility.INPROCEEDINGS + ".\n");
                break;
            case ConstantList.INCOLLECTION:
                writer.append(Utility.INCOLLECTION + ".\n");
                break;
            case ConstantList.BOOK:
                writer.append(Utility.BOOK + ".\n");
                break;
            case ConstantList.PHDTHESIS:
                writer.append(Utility.PHDTHESIS + ".\n");
                break;
            case ConstantList.MASTERSTHESIS:
                writer.append(Utility.MASTERSTHESIS + ".\n");
                break;
            case ConstantList.WWW:
                writer.append(Utility.WWW + ".\n");
                break;
            default:
                break;
        }


        Utility.writeStringBuffer(writer, Main.outputFile, true);
        writer.delete(0, writer.length());
    }

    public static void writeVocabulary(List<String> typeList, List<String> elementList) throws IOException {
        StringBuffer writer = new StringBuffer();

        System.out.println("Starting to write the classes");

        for (Object item : typeList) {
            switch (item.toString()) {
                case ConstantList.ARTICLE:
                    writer.append(Utility.ARTICLE + "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2000/01/rdf-schema#Class> .\n");
                    break;
                case ConstantList.PROCEEDINGS:
                    writer.append(Utility.PROCEEDINGS + "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2000/01/rdf-schema#Class> .\n");
                    break;
                case ConstantList.INPROCEEDINGS:
                    writer.append(Utility.INPROCEEDINGS + "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2000/01/rdf-schema#Class> .\n");
                    break;
                case ConstantList.INCOLLECTION:
                    writer.append(Utility.INCOLLECTION + "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2000/01/rdf-schema#Class> .\n");
                    break;
                case ConstantList.BOOK:
                    writer.append(Utility.BOOK + "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2000/01/rdf-schema#Class> .\n");
                    break;
                case ConstantList.PHDTHESIS:
                    writer.append(Utility.PHDTHESIS + "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2000/01/rdf-schema#Class> .\n");
                    break;
                case ConstantList.MASTERSTHESIS:
                    writer.append(Utility.MASTERSTHESIS + "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2000/01/rdf-schema#Class> .\n");
                    break;
                case ConstantList.WWW:
                    writer.append(Utility.WWW + "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2000/01/rdf-schema#Class> .\n");
                    break;
                default:
                    break;
            }
        }

        System.out.println("Finishing to write the classes \nStarting to write the properties");

        for (Object item : elementList) {
            switch (item.toString()) {
                case ConstantList.MDATE:
                    writer.append(Utility.MDATE +
                            "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/1999/02/22-rdf-syntax-ns#Property> .\n");
                    break;
                case ConstantList.AUTHOR:
                    writer.append(
                            Utility.AUTHOR + "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/1999/02/22-rdf-syntax-ns#Property> .\n");
                    break;
                case ConstantList.TITLE:
                    writer.append(
                            Utility.TITLE + "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/1999/02/22-rdf-syntax-ns#Property> .\n");
                    break;
                case ConstantList.PAGES:
                    writer.append(
                            Utility.PAGES + "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/1999/02/22-rdf-syntax-ns#Property> .\n");
                    break;
                case ConstantList.YEAR:
                    writer.append(
                            Utility.YEAR + "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/1999/02/22-rdf-syntax-ns#Property> .\n");
                    break;
                case ConstantList.VOLUME:
                    writer.append(
                            Utility.VOLUME + "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/1999/02/22-rdf-syntax-ns#Property> .\n");
                    break;
                case ConstantList.JOURNAL:
                    writer.append(
                            Utility.JOURNAL + "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/1999/02/22-rdf-syntax-ns#Property> .\n");
                    break;
                case ConstantList.NUMBER:
                    writer.append(
                            Utility.NUMBER + "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/1999/02/22-rdf-syntax-ns#Property> .\n");
                    break;
                case ConstantList.URL:
                    writer.append(
                            Utility.URL + "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/1999/02/22-rdf-syntax-ns#Property> .\n");
                    break;
                case ConstantList.EE:
                    writer.append(
                            Utility.EE + "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/1999/02/22-rdf-syntax-ns#Property> .\n");
                    break;
                case ConstantList.NOTE:
                    writer.append(
                            Utility.NOTE + "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/1999/02/22-rdf-syntax-ns#Property> .\n");
                    break;
                case ConstantList.CITE:
                    writer.append(
                            Utility.CITE + "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/1999/02/22-rdf-syntax-ns#Property> .\n");
                    break;
                case ConstantList.MONTH:
                    writer.append(
                            Utility.MONTH + "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/1999/02/22-rdf-syntax-ns#Property> .\n");
                    break;
                case ConstantList.ADDRESS:
                    writer.append(
                            Utility.ADDRESS + "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/1999/02/22-rdf-syntax-ns#Property> .\n");
                    break;
                case ConstantList.EDITOR:
                    writer.append(
                            Utility.EDITOR + "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/1999/02/22-rdf-syntax-ns#Property> .\n");
                    break;
                case ConstantList.PUBLISHER:
                    writer.append(
                            Utility.PUBLISHER + "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/1999/02/22-rdf-syntax-ns#Property> .\n");
                    break;
                case ConstantList.BOOKTITLE:
                    writer.append(
                            Utility.BOOKTITLE + "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/1999/02/22-rdf-syntax-ns#Property> .\n");
                    break;
                case ConstantList.CDROM:
                    writer.append(
                            Utility.CDROM + "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/1999/02/22-rdf-syntax-ns#Property> .\n");
                    break;
                case ConstantList.CROSSREF:
                    writer.append(
                            Utility.CROSSREF + "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/1999/02/22-rdf-syntax-ns#Property> .\n");
                    break;
                case ConstantList.ISBN:
                    writer.append(
                            Utility.ISBN + "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/1999/02/22-rdf-syntax-ns#Property> .\n");
                    break;
                case ConstantList.SERIES:
                    writer.append(
                            Utility.SERIES + "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/1999/02/22-rdf-syntax-ns#Property> .\n");
                    break;
                case ConstantList.SCHOOL:
                    writer.append(
                            Utility.SCHOOL + "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/1999/02/22-rdf-syntax-ns#Property> .\n");
                    break;
                case ConstantList.CHAPTER:
                    writer.append(
                            Utility.CHAPTER + "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/1999/02/22-rdf-syntax-ns#Property> .\n");
                    break;
                case ConstantList.PUBLNR:
                    writer.append(
                            Utility.PUBLNR + "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/1999/02/22-rdf-syntax-ns#Property> .\n");
                    break;
                default:
                    break;
            }
        }

        System.out.println("Finishing to write the properties");

        Utility.writeStringBuffer(writer, Main.outputFile, false);
        writer.delete(0, writer.length());
    }

    public static Map<String, String> extractPersonRecords() throws XMLStreamException, FileNotFoundException {
                XMLInputFactory factory = XMLInputFactory.newInstance();

        factory.setProperty(XMLInputFactory.IS_VALIDATING, true);
        factory.setProperty(WstxInputProperties.P_MAX_ENTITY_COUNT, Integer.valueOf(999999999));

        FileInputStream fileXML = new FileInputStream(Main.inputFile);

        XMLEventReader reader = factory.createXMLEventReader(Main.inputFile, fileXML);

        Map<String,String> persons = new HashMap<String, String>();

        String key = "";
        String personName;
        boolean extractName = false;
        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            if (event.isStartElement()) {
                if(event.asStartElement().getName().toString().equals("www")){
                    key = event.asStartElement().getAttributeByName(new QName("key")).getValue();
                } else if((event.asStartElement().getName().toString().equals("author")) && key.contains("homepage")){
                    extractName = true;
                }
            } else if(event.isCharacters() && extractName) {
                if (!event.asCharacters().getData().toString().equals("\n")) {
                    personName = event.asCharacters().getData().toString();

                    persons.put(personName, key.replace("homepages/","https://dblp.org/pid/" ));
                    extractName = false;
                }
            }
        }
        return persons;
    }
}
