package olive.app;

import org.teleal.cling.model.message.header.EXTHeader;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/* loaded from: classes.dex */
public class XMLParser extends DefaultHandler {
    public static Items items = null;
    Boolean currentElement = false;
    String currentValue = null;
    private StringBuffer chars = new StringBuffer();

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        this.currentElement = true;
        if (localName.equals("Result") || localName.equals("tree")) {
            items = new Items();
            this.chars = new StringBuffer();
        } else if (localName.equals("container")) {
            items.setObjId(attributes.getValue("id"));
            items.setIsDir(attributes.getValue("restricted"));
        } else if (localName.equals("err")) {
            items = new Items();
        }
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void endElement(String uri, String localName, String qName) throws SAXException {
        this.currentElement = false;
        if (localName.equalsIgnoreCase("TotalMatches")) {
            items.setTotalItems(this.chars.toString().replace("\n", EXTHeader.DEFAULT_VALUE));
        } else if (localName.equalsIgnoreCase("title")) {
            items.setTitle(this.chars.toString().replace("\n", EXTHeader.DEFAULT_VALUE));
        } else if (localName.equalsIgnoreCase("albumArtURI")) {
            items.setArtwork(this.chars.toString().replace("\n", EXTHeader.DEFAULT_VALUE));
        } else if (localName.equalsIgnoreCase("artist")) {
            items.setArtist(this.chars.toString().replace("\n", EXTHeader.DEFAULT_VALUE));
        } else if (localName.equalsIgnoreCase("msg")) {
            items.setError(this.chars.toString().replace("\n", EXTHeader.DEFAULT_VALUE));
        }
        this.chars = new StringBuffer();
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void characters(char[] ch, int start, int length) throws SAXException {
        this.chars.append(ch, start, length);
    }
}
