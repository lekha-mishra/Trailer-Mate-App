package com.example.trailermate.ModelsTrailer;

import java.util.List;

public class YoutubeMainModel {
    public String kind;
    public String etag;
    public String nextPageToken;
    public String regionCode;
    public List<YoutubeIdModel> items;


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

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public List<YoutubeIdModel> getItems() {
        return items;
    }

    public void setItems(List<YoutubeIdModel> items) {
        this.items = items;
    }
}
