package com.app.filter;

import com.app.persistence.model.es.managementuser.UserItem;
import java.util.List;

public class ManagementUserFilter {

    private String text;
    private String name;
    private String lastName;
    private String userType;
    private Boolean isActive;
    private List<UserItem> albums;
    private List<UserItem> photos;
    private List<UserItem> post;
    private List<UserItem> coments;
    private String beginDate;
    private String endDate;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<UserItem> getAlbums() {
        return albums;
    }

    public void setAlbums(List<UserItem> albums) {
        this.albums = albums;
    }

    public List<UserItem> getPhotos() {
        return photos;
    }

    public void setPhotos(List<UserItem> photos) {
        this.photos = photos;
    }

    public List<UserItem> getPost() {
        return post;
    }

    public void setPost(List<UserItem> post) {
        this.post = post;
    }

    public List<UserItem> getComents() {
        return coments;
    }

    public void setComents(List<UserItem> coments) {
        this.coments = coments;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static class ManagementUserFilterBuilder {

        private ManagementUserFilter filter;

        public ManagementUserFilterBuilder() {
            filter = new ManagementUserFilter();
        }

        public ManagementUserFilterBuilder setText(String text) {
            filter.text = text;
            return this;
        }

        public ManagementUserFilterBuilder setAlbums(List<UserItem> albums) {
            filter.albums = albums;
            return this;
        }

        public ManagementUserFilterBuilder setPhotos(List<UserItem> photos) {
            filter.photos = photos;
            return this;
        }

        public ManagementUserFilterBuilder setPost(List<UserItem> post) {
            filter.post = post;
            return this;
        }

        public ManagementUserFilterBuilder setComents(List<UserItem> coments) {
            filter.coments = coments;
            return this;
        }

        public ManagementUserFilterBuilder setUserType(String userType) {
            filter.userType = userType;
            return this;
        }

        public ManagementUserFilterBuilder setIsActive(Boolean isActive) {
            filter.isActive = isActive;
            return this;
        }

        public ManagementUserFilterBuilder setBeginDate(String beginDate) {
            filter.beginDate = beginDate;
            return this;
        }

        public ManagementUserFilterBuilder setEndDate(String endDate) {
            filter.endDate = endDate;
            return this;
        }

        public ManagementUserFilter build() {
            return filter;
        }
    }

}
