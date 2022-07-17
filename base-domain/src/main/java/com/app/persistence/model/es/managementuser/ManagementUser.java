package com.app.persistence.model.es.managementuser;

import com.app.persistence.model.es.DomainObject;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "sc-managementuser")
public class ManagementUser extends DomainObject implements Cloneable {

    @Id
    @Field(type = FieldType.Keyword)
    private String id;
    private String name;
    @Field(type = FieldType.Text)
    private String lastName;
    private String beginDate;
    private String endDate;
    private String lastModified;
    private Boolean isActive = true;
    @Field(type = FieldType.Nested)
    private List<UserItem> albums;
    @Field(type = FieldType.Nested)
    private List<UserItem> photos;
    @Field(type = FieldType.Nested)
    private List<UserItem> post;
    @Field(type = FieldType.Nested)
    private List<UserItem> coments;
    @Field(type = FieldType.Keyword)
    private String type; // regular
    @Field(type = FieldType.Keyword)
    private String userType; // ADMIN - SELLER

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<UserItem> getAlbums() {
        if (albums == null) {
            albums = new ArrayList<>();
        }
        return albums;
    }

    public void setAlbums(List<UserItem> albums) {
        this.albums = albums;
    }

    public List<UserItem> getPhotos() {
        if (photos == null) {
            photos = new ArrayList<>();
        }
        return photos;
    }

    public void setPhotos(List<UserItem> photos) {
        this.photos = photos;
    }

    public List<UserItem> getPost() {
        if (post == null) {
            post = new ArrayList<>();
        }
        return post;
    }

    public void setPost(List<UserItem> post) {
        this.post = post;
    }

    public List<UserItem> getComents() {
        if (coments == null) {
            coments = new ArrayList<>();
        }
        return coments;
    }

    public void setComents(List<UserItem> coments) {
        this.coments = coments;
    }

    @Override
    public ManagementUser clone() {
        try {
            ManagementUser prom = (ManagementUser) super.clone();
            return prom;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

}
