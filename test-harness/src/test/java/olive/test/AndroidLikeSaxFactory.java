package olive.test;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/** Android's SAX parser reports localName by default; the JDK's only does when namespace aware. */
public class AndroidLikeSaxFactory extends SAXParserFactory {
    private final SAXParserFactory delegate = SAXParserFactory.newDefaultInstance();

    public AndroidLikeSaxFactory() {
        delegate.setNamespaceAware(true);
    }

    @Override
    public SAXParser newSAXParser() throws ParserConfigurationException, SAXException {
        return delegate.newSAXParser();
    }

    @Override
    public void setFeature(String name, boolean value) throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException {
        delegate.setFeature(name, value);
    }

    @Override
    public boolean getFeature(String name) throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException {
        return delegate.getFeature(name);
    }
}
