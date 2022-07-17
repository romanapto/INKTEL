package com.app.persistence.model.es.managementuser;

import org.springframework.data.elasticsearch.annotations.Document;

import com.app.persistence.model.es.DomainObject;

@Document(indexName = "sc-disco")
public class Disco extends DomainObject implements Cloneable {

    private String id;
    private String title;
    private String artist;
    private String composer;
    private String genre;
    private String album;

    
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    @Override
    public Disco clone() {
        try {
            Disco prom = (Disco) super.clone();
            return prom;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

}
