package olive.app.upnp;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/* loaded from: classes.dex */
public class Device {
    static final String DEVICE_TYPE = "deviceType";
    static final String FRIENDLY_NAME = "friendlyName";
    static final String MANUFACTURER = "manufacturer";
    static final String MANUFACTURER_URL = "manufacturerURL";
    static final String MODEL_DESCRIPTION = "modelDescription";
    static final String MODEL_NAME = "modelName";
    static final String MODEL_NUMBER = "modelNumber";
    static final String MODEL_URL = "modelURL";
    static final String PRESENTATION_URL = "presentationURL";
    static final String SERIAL_NUMBER = "serialNumber";
    static final String SERVICE_TYPE = "serviceType";
    static final String UDN = "UDN";
    Service mContentDirectoryService;
    String mDeviceType;
    String mFriendlyName;
    String mManufactorer;
    String mManufactorerURL;
    String mModelDescription;
    String mModelName;
    String mModelNumber;
    String mModelURL;
    String mSerialNumber;
    String mUDN;
    String mUPC;
    String mURL;

    public static Device createInstanceFromXML(String url) {
        Device device = new Device();
        DefaultHandler dh = new DefaultHandler() { // from class: olive.app.upnp.Device.1
            String currentValue = null;

            @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
            public void characters(char[] ch, int start, int length) throws SAXException {
                this.currentValue = new String(ch, start, length);
            }

            @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
            public void endElement(String uri, String localName, String qName) throws SAXException {
                if (Device.DEVICE_TYPE.equals(qName) || Device.DEVICE_TYPE.equals(localName)) {
                    Device.this.mDeviceType = this.currentValue;
                    return;
                }
                if (Device.PRESENTATION_URL.equals(qName) || Device.PRESENTATION_URL.equals(localName)) {
                    Device.this.mURL = this.currentValue;
                    return;
                }
                if (Device.FRIENDLY_NAME.equals(qName) || Device.FRIENDLY_NAME.equals(localName)) {
                    Device.this.mFriendlyName = this.currentValue;
                    return;
                }
                if (Device.MANUFACTURER.equals(qName) || Device.MANUFACTURER.equals(localName)) {
                    Device.this.mManufactorer = this.currentValue;
                    return;
                }
                if (Device.MANUFACTURER_URL.equals(qName) || Device.MANUFACTURER_URL.equals(localName)) {
                    Device.this.mManufactorerURL = this.currentValue;
                    return;
                }
                if (Device.MODEL_DESCRIPTION.equals(qName) || Device.MODEL_DESCRIPTION.equals(localName)) {
                    Device.this.mModelDescription = this.currentValue;
                    return;
                }
                if (Device.MODEL_NUMBER.equals(qName) || Device.MODEL_NUMBER.equals(localName)) {
                    Device.this.mModelNumber = this.currentValue;
                    return;
                }
                if (Device.MODEL_URL.equals(qName) || Device.MODEL_URL.equals(localName)) {
                    Device.this.mModelURL = this.currentValue;
                    return;
                }
                if (Device.MODEL_NAME.equals(qName) || Device.MODEL_NAME.equals(localName)) {
                    Device.this.mModelName = this.currentValue;
                } else if (Device.UDN.equals(qName) || Device.UDN.equals(localName)) {
                    Device.this.mUDN = this.currentValue;
                } else if (!Device.SERVICE_TYPE.equals(qName)) {
                    Device.SERVICE_TYPE.equals(localName);
                }
            }
        };
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(url, dh);
            return device;
        } catch (IOException e) {
            e.printStackTrace();
            if (e.getMessage() == null || e.getMessage().indexOf("Couldn't open") <= -1) {
                return null;
            }
            device.mDeviceType = "Melody";
            return device;
        } catch (ParserConfigurationException e2) {
            e2.printStackTrace();
            return null;
        } catch (SAXException e3) {
            e3.printStackTrace();
            return null;
        }
    }

    public String getDeviceType() {
        return this.mDeviceType;
    }

    public String getManufacturer() {
        return this.mManufactorer;
    }

    public String getURL() {
        return this.mURL;
    }

    public String toString() {
        return this.mFriendlyName;
    }
}
