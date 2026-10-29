package olive.app;

import org.teleal.cling.model.message.header.EXTHeader;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/* loaded from: classes.dex */
public class XMLTrackListParser extends DefaultHandler {
    public static Items items = null;
    Boolean currentElement = false;
    String currentValue = null;
    private StringBuffer chars = new StringBuffer();

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        this.currentElement = true;
        if (localName.equals("tree")) {
            items = new Items();
            this.chars = new StringBuffer();
        }
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void endElement(String uri, String localName, String qName) throws SAXException {
        this.currentElement = false;
        if (localName.equalsIgnoreCase("itemsReturned")) {
            items.setItemsReturned(this.chars.toString().replace("\n", EXTHeader.DEFAULT_VALUE).replace(" ", EXTHeader.DEFAULT_VALUE));
        } else if (localName.equalsIgnoreCase("totalItems")) {
            items.setTotalItems(this.chars.toString().replace("\n", EXTHeader.DEFAULT_VALUE).replace(" ", EXTHeader.DEFAULT_VALUE));
        } else if (localName.equalsIgnoreCase("title")) {
            items.setTitle(this.chars.toString().replace("\n", EXTHeader.DEFAULT_VALUE));
        } else if (localName.equalsIgnoreCase("objid")) {
            items.setObjId(this.chars.toString().replace("\n", EXTHeader.DEFAULT_VALUE).replace(" ", EXTHeader.DEFAULT_VALUE));
        } else if (localName.equalsIgnoreCase("uuid")) {
            items.setUuid(this.chars.toString().replace("\n", EXTHeader.DEFAULT_VALUE).replace(" ", EXTHeader.DEFAULT_VALUE));
        } else if (localName.equalsIgnoreCase("ip")) {
            items.setIp(this.chars.toString().replace("\n", EXTHeader.DEFAULT_VALUE).replace(" ", EXTHeader.DEFAULT_VALUE));
        } else if (localName.equalsIgnoreCase("genre")) {
            items.setGenre(this.chars.toString().replace("\n", EXTHeader.DEFAULT_VALUE).replace(" ", EXTHeader.DEFAULT_VALUE));
        } else if (localName.equalsIgnoreCase("action")) {
            items.setAction(this.chars.toString().replace("\n", EXTHeader.DEFAULT_VALUE).replace(" ", EXTHeader.DEFAULT_VALUE));
        } else if (localName.equalsIgnoreCase("isDir")) {
            items.setIsDir(this.chars.toString().replace("\n", EXTHeader.DEFAULT_VALUE).replace(" ", EXTHeader.DEFAULT_VALUE));
        }
        this.chars = new StringBuffer();
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void characters(char[] ch, int start, int length) throws SAXException {
        this.chars.append(ch, start, length);
    }
}
