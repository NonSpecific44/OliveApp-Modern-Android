package olive.app;

import java.util.ArrayList;

/* loaded from: classes.dex */
public class Items {
    private String itemsReturned;
    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<String> artist = new ArrayList<>();
    private ArrayList<String> artwork = new ArrayList<>();
    private ArrayList<String> objid = new ArrayList<>();
    private ArrayList<String> isDir = new ArrayList<>();
    private String totalItems = new String();
    private ArrayList<String> uuid = new ArrayList<>();
    private ArrayList<String> ip = new ArrayList<>();
    private ArrayList<String> genre = new ArrayList<>();
    private ArrayList<String> action = new ArrayList<>();
    private String error = new String();

    public Items addItems(Items items) {
        this.title.addAll(items.title);
        this.artist.addAll(items.artist);
        this.artwork.addAll(items.artwork);
        this.objid.addAll(items.objid);
        this.isDir.addAll(items.isDir);
        this.totalItems = items.totalItems;
        this.uuid.addAll(items.uuid);
        this.ip.addAll(items.ip);
        this.genre.addAll(items.genre);
        this.action.addAll(items.action);
        return this;
    }

    public ArrayList<String> getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title.add(title);
    }

    public ArrayList<String> getArtist() {
        return this.artist;
    }

    public void setArtist(String artist) {
        this.artist.add(artist);
    }

    public ArrayList<String> getArtwork() {
        return this.artwork;
    }

    public void setArtwork(String artwork) {
        this.artwork.add(artwork);
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    public String getTotalItems() {
        return this.totalItems;
    }

    public void setUuid(String uuid) {
        this.uuid.add(uuid);
    }

    public ArrayList<String> getUuid() {
        return this.uuid;
    }

    public void setIp(String ip) {
        this.ip.add(ip);
    }

    public ArrayList<String> getIp() {
        return this.ip;
    }

    public void setGenre(String genre) {
        this.genre.add(genre);
    }

    public ArrayList<String> getGenre() {
        return this.genre;
    }

    public void setAction(String action) {
        this.action.add(action);
    }

    public ArrayList<String> getAction() {
        return this.action;
    }

    public ArrayList<String> getObjId() {
        return this.objid;
    }

    public void setObjId(String objid) {
        this.objid.add(objid);
    }

    public ArrayList<String> getIsDir() {
        return this.isDir;
    }

    public void setIsDir(String isDir) {
        this.isDir.add(isDir);
    }

    public void setError(String message) {
        this.error = message;
    }

    public String getError() {
        return this.error;
    }

    public void setItemsReturned(String itemsReturned) {
        this.itemsReturned = itemsReturned;
    }

    public String getItemsReturned() {
        return this.itemsReturned;
    }
}
