package com.example.trailermate.ModelsTrailer;

public class YoutubeIdModel {

    public String kind;
    public String etag;
    public VideoIdModel id;


    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public VideoIdModel getId() {
        return id;
    }

    public void setId(VideoIdModel id) {
        this.id = id;
    }
}
