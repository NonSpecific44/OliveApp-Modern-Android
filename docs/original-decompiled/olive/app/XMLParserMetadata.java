package olive.app;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/* loaded from: classes.dex */
public class XMLParserMetadata extends DefaultHandler {
    public static Metadata metadata = null;
    Boolean currentElement = false;
    String currentValue = null;
    private StringBuffer chars = new StringBuffer();

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        this.currentElement = true;
        if (localName.equals("playmetadata")) {
            metadata = new Metadata();
            this.chars = new StringBuffer();
        }
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void endElement(String uri, String localName, String qName) throws SAXException {
        this.currentElement = false;
        String myValue = this.chars.toString();
        while (true) {
            if (!myValue.startsWith(" ") && !myValue.startsWith("\n")) {
                break;
            } else {
                myValue = myValue.substring(1, myValue.length());
            }
        }
        if (localName.equalsIgnoreCase("volume")) {
            metadata.setVolume(myValue);
        } else if (localName.equalsIgnoreCase("volVal")) {
            metadata.setVolVal(myValue);
        } else if (localName.equalsIgnoreCase("transportState")) {
            metadata.setTransportState(myValue);
        } else if (localName.equalsIgnoreCase("progress")) {
            metadata.setProgress(myValue);
        } else if (localName.equalsIgnoreCase("currentPlayMode")) {
            metadata.setCurrentPlayMode(myValue);
        } else if (localName.equalsIgnoreCase("PlayType")) {
            metadata.setPlayType(myValue);
        } else if (localName.equalsIgnoreCase("numberOfTracks")) {
            metadata.setNumberOfTracks(myValue);
        } else if (localName.equalsIgnoreCase("currentTrack")) {
            metadata.setCurrentTrack(myValue);
        } else if (localName.equalsIgnoreCase("currentTrackDuration")) {
            metadata.setCurrentTrackDuration(myValue);
        } else if (localName.equalsIgnoreCase("relativeTimePosition")) {
            metadata.setRelativeTimePosition(myValue);
        } else if (localName.equalsIgnoreCase("objId")) {
            metadata.setObjId(myValue);
        } else if (localName.equalsIgnoreCase("major_genre")) {
            metadata.setMajor_genre(myValue);
        } else if (localName.equalsIgnoreCase("composerLastname")) {
            metadata.setComposerLastname(myValue);
        } else if (localName.equalsIgnoreCase("composerFirstname")) {
            metadata.setComposerFirstname(myValue);
        } else if (localName.equalsIgnoreCase("dateComposed")) {
            metadata.setDateComposed(myValue);
        } else if (localName.equalsIgnoreCase("releasedDate")) {
            metadata.setReleasedDate(myValue);
        } else if (localName.equalsIgnoreCase("publishedBy")) {
            metadata.setPublishedBy(myValue);
        } else if (localName.equalsIgnoreCase("producedBy")) {
            metadata.setProducer(myValue);
        } else if (localName.equalsIgnoreCase("dctitle")) {
            metadata.setDctitle(myValue);
        } else if (localName.equalsIgnoreCase("stationTitle")) {
            metadata.setStationTitle(myValue);
        } else if (localName.equalsIgnoreCase("radioStationURL")) {
            metadata.setStationUrl(myValue);
        } else if (localName.equalsIgnoreCase("album")) {
            metadata.setAlbum(myValue);
        } else if (localName.equalsIgnoreCase("albumart")) {
            metadata.setAlbumart(myValue);
        } else if (localName.equalsIgnoreCase("artist")) {
            metadata.setArtist(myValue);
        } else if (localName.equalsIgnoreCase("additionalArtists")) {
            metadata.setAdditionalArtists(myValue);
        } else if (localName.equalsIgnoreCase("country")) {
            metadata.setCountry(myValue);
        } else if (localName.equalsIgnoreCase("bitrate")) {
            metadata.setBitrate(myValue);
        } else if (localName.equalsIgnoreCase("genre")) {
            metadata.setGenre(myValue);
        } else if (localName.equalsIgnoreCase("work")) {
            metadata.setWork(myValue);
        } else if (localName.equalsIgnoreCase("period")) {
            metadata.setPeriod(myValue);
        } else if (localName.equalsIgnoreCase("featuredInstruments")) {
            metadata.setFeaturedInstruments(myValue);
        } else if (localName.equalsIgnoreCase("originalPerformer")) {
            metadata.setOriginalPerformer(myValue);
        } else if (localName.equalsIgnoreCase("orchestra")) {
            metadata.setOrchestra(myValue);
        } else if (localName.equalsIgnoreCase("chorus")) {
            metadata.setChorus(myValue);
        } else if (localName.equalsIgnoreCase("myRating")) {
            metadata.setMyRating(myValue);
        } else if (localName.equalsIgnoreCase("originalRecording")) {
            metadata.setOriginalRecording(myValue);
        } else if (localName.equalsIgnoreCase("dateOfRecording")) {
            metadata.setDateOfRecording(myValue);
        } else if (localName.equalsIgnoreCase("conductorFirstname")) {
            metadata.setConductorFirstname(myValue);
        } else if (localName.equalsIgnoreCase("conductorLastname")) {
            metadata.setConductorLastname(myValue);
        }
        this.chars = new StringBuffer();
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void characters(char[] ch, int start, int length) throws SAXException {
        this.chars.append(ch, start, length);
    }
}
