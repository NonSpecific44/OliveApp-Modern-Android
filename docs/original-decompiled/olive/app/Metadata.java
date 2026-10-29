package olive.app;

import org.teleal.cling.model.message.header.EXTHeader;

/* loaded from: classes.dex */
public class Metadata {
    private String volVal;
    private String volume;
    private String transportState = EXTHeader.DEFAULT_VALUE;
    private String progress = EXTHeader.DEFAULT_VALUE;
    private String currentPlayMode = EXTHeader.DEFAULT_VALUE;
    private String PlayType = EXTHeader.DEFAULT_VALUE;
    private String numberOfTracks = EXTHeader.DEFAULT_VALUE;
    private String currentTrack = EXTHeader.DEFAULT_VALUE;
    private String currentTrackDuration = EXTHeader.DEFAULT_VALUE;
    private String relativeTimePosition = EXTHeader.DEFAULT_VALUE;
    private String objId = EXTHeader.DEFAULT_VALUE;
    private String major_genre = EXTHeader.DEFAULT_VALUE;
    private String composerLastname = EXTHeader.DEFAULT_VALUE;
    private String composerFirstname = EXTHeader.DEFAULT_VALUE;
    private String dateComposed = EXTHeader.DEFAULT_VALUE;
    private String releasedDate = EXTHeader.DEFAULT_VALUE;
    private String publishedBy = EXTHeader.DEFAULT_VALUE;
    private String producer = EXTHeader.DEFAULT_VALUE;
    private String dctitle = EXTHeader.DEFAULT_VALUE;
    private String stationTitle = EXTHeader.DEFAULT_VALUE;
    private String radioStationURL = EXTHeader.DEFAULT_VALUE;
    private String bitrate = EXTHeader.DEFAULT_VALUE;
    private String album = EXTHeader.DEFAULT_VALUE;
    private String albumart = EXTHeader.DEFAULT_VALUE;
    private String artist = EXTHeader.DEFAULT_VALUE;
    private String additionalArtists = EXTHeader.DEFAULT_VALUE;
    private String country = EXTHeader.DEFAULT_VALUE;
    private String genre = EXTHeader.DEFAULT_VALUE;
    private String work = EXTHeader.DEFAULT_VALUE;
    private String period = EXTHeader.DEFAULT_VALUE;
    private String featuredInstruments = EXTHeader.DEFAULT_VALUE;
    private String originalPerformer = EXTHeader.DEFAULT_VALUE;
    private String orchestra = EXTHeader.DEFAULT_VALUE;
    private String chorus = EXTHeader.DEFAULT_VALUE;
    private String myRating = EXTHeader.DEFAULT_VALUE;
    private String originalRecording = EXTHeader.DEFAULT_VALUE;
    private String dateOfRecording = EXTHeader.DEFAULT_VALUE;
    private String conductorFirstname = EXTHeader.DEFAULT_VALUE;
    private String conductorLastname = EXTHeader.DEFAULT_VALUE;

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getVolume() {
        return this.volume;
    }

    public void setVolVal(String volVal) {
        this.volVal = volVal;
    }

    public String getVolVal() {
        return this.volVal;
    }

    public void setTransportState(String transportState) {
        this.transportState = transportState;
    }

    public String getTransportState() {
        return this.transportState;
    }

    public void setCurrentPlayMode(String currentPlayMode) {
        this.currentPlayMode = currentPlayMode;
    }

    public String getCurrentPlayMode() {
        return this.currentPlayMode;
    }

    public void setPlayType(String playType) {
        this.PlayType = playType;
    }

    public String getPlayType() {
        return this.PlayType;
    }

    public void setNumberOfTracks(String numberOfTracks) {
        this.numberOfTracks = numberOfTracks;
    }

    public String getNumberOfTracks() {
        return this.numberOfTracks;
    }

    public void setCurrentTrack(String currentTrack) {
        this.currentTrack = currentTrack;
    }

    public String getCurrentTrack() {
        return this.currentTrack;
    }

    public void setCurrentTrackDuration(String currentTrackDuration) {
        this.currentTrackDuration = currentTrackDuration;
    }

    public String getCurrentTrackDuration() {
        return this.currentTrackDuration;
    }

    public void setMajor_genre(String major_genre) {
        this.major_genre = major_genre;
    }

    public String getMajor_genre() {
        return this.major_genre;
    }

    public void setComposerLastname(String composerLastname) {
        this.composerLastname = composerLastname;
    }

    public String getComposerLastname() {
        return this.composerLastname;
    }

    public void setComposerFirstname(String composerFirstname) {
        this.composerFirstname = composerFirstname;
    }

    public String getComposerFirstname() {
        return this.composerFirstname;
    }

    public void setDateComposed(String dateComposed) {
        this.dateComposed = dateComposed;
    }

    public String getDateComposed() {
        return this.dateComposed;
    }

    public void setReleasedDate(String releasedDate) {
        this.releasedDate = releasedDate;
    }

    public String getReleasedDate() {
        return this.releasedDate;
    }

    public void setDctitle(String dctitle) {
        this.dctitle = dctitle;
    }

    public String getDctitle() {
        return this.dctitle;
    }

    public void setStationTitle(String stationTitle) {
        this.stationTitle = stationTitle;
    }

    public String getStationTitle() {
        return this.stationTitle;
    }

    public void setStationUrl(String url) {
        this.radioStationURL = url;
    }

    public String getStationUrl() {
        return this.radioStationURL;
    }

    public void setBitrate(String bitrate) {
        this.bitrate = bitrate;
    }

    public String getBitrate() {
        return this.bitrate;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getAlbum() {
        return this.album;
    }

    public void setAlbumart(String albumart) {
        this.albumart = albumart;
    }

    public String getAlbumart() {
        return this.albumart;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getArtist() {
        return this.artist;
    }

    public void setAdditionalArtists(String additionalArtists) {
        this.additionalArtists = additionalArtists;
    }

    public String getAdditionalArtists() {
        return this.additionalArtists;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return this.country;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return this.genre;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getWork() {
        return this.work;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setFeaturedInstruments(String featuredInstruments) {
        this.featuredInstruments = featuredInstruments;
    }

    public String getFeaturedInstruments() {
        return this.featuredInstruments;
    }

    public void setOriginalPerformer(String originalPerformer) {
        this.originalPerformer = originalPerformer;
    }

    public String getOriginalPerformer() {
        return this.originalPerformer;
    }

    public void setOrchestra(String orchestra) {
        this.orchestra = orchestra;
    }

    public String getOrchestra() {
        return this.orchestra;
    }

    public void setChorus(String chorus) {
        this.chorus = chorus;
    }

    public String getChorus() {
        return this.chorus;
    }

    public void setMyRating(String myRating) {
        this.myRating = myRating;
    }

    public String getMyRating() {
        return this.myRating;
    }

    public void setOriginalRecording(String originalRecording) {
        this.originalRecording = originalRecording;
    }

    public String getOriginalRecording() {
        return this.originalRecording;
    }

    public void setDateOfRecording(String dateOfRecording) {
        this.dateOfRecording = dateOfRecording;
    }

    public String getDateOfRecording() {
        return this.dateOfRecording;
    }

    public void setConductorFirstname(String conductorFirstname) {
        this.conductorFirstname = conductorFirstname;
    }

    public String getConductorFirstname() {
        return this.conductorFirstname;
    }

    public void setConductorLastname(String conductorLastname) {
        this.conductorLastname = conductorLastname;
    }

    public String getConductorLastname() {
        return this.conductorLastname;
    }

    public void setPublishedBy(String publishedBy) {
        this.publishedBy = publishedBy;
    }

    public String getPublishedBy() {
        return this.publishedBy;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getProducer() {
        return this.producer;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public String getObjId() {
        return this.objId;
    }

    public void setRelativeTimePosition(String relativeTimePosition) {
        this.relativeTimePosition = relativeTimePosition;
    }

    public String getRelativeTimePosition() {
        return this.relativeTimePosition;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getProgress() {
        return this.progress;
    }
}
