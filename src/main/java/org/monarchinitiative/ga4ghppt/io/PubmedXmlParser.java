package org.monarchinitiative.ga4ghppt.io;

import org.monarchinitiative.ga4ghppt.model.PubmedEntry;
import org.monarchinitiative.phenol.base.PhenolRuntimeException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Optional;

public class PubmedXmlParser {

    private String title = null;
    private Integer pmid = null;

    public PubmedXmlParser(String xmlString) {
        //XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        //XMLEventReader reader = xmlInputFactory.createXMLEventReader(xmlString);
        Reader reader = new StringReader(xmlString);
        XMLInputFactory factory = XMLInputFactory.newInstance(); // Or newFactory()
        try {
            XMLStreamReader xmlReader = factory.createXMLStreamReader(reader);
            String currentElement = "";
            while (xmlReader.hasNext()) {
                int next = xmlReader.next();
                if(next == XMLStreamReader.START_ELEMENT) {
                    currentElement = xmlReader.getLocalName();

                    if (currentElement.equals("ArticleTitle")) {
                        next = xmlReader.next();
                        if (next == XMLStreamReader.CHARACTERS) {
                            title = xmlReader.getText();
                        }
                    } else if (currentElement.equals("PMID")) {
                        next = xmlReader.next();
                        if (next == XMLStreamReader.CHARACTERS) {
                            pmid = Integer.parseInt(xmlReader.getText());
                        }

                    }
                }
            }
        } catch (XMLStreamException e) {
            throw new PhenolRuntimeException("Could not parse XML:" + e.getMessage());
        }
    }

    public Optional<String> getTitle() {
        if (title == null) {
            return Optional.empty();
        }
        return Optional.of(title);
    }

    public Optional<String> getPmid() {
        if (pmid == null) {
            return Optional.empty();
        }
        return Optional.of(String.format("PMID:%d", pmid));
    }

    public Optional<PubmedEntry> getPubmedEntry() {
        Optional<String> optTitle = getTitle();
        Optional<String> optPmid = getPmid();
        if (optPmid.isPresent() && optTitle.isPresent()) {
            return Optional.of(new PubmedEntry(optPmid.get(), optTitle.get()));
        } else {
            return Optional.empty();
        }
    }


}
