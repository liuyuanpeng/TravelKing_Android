package com.cy.travelking.entity;

public class Upload {

    private String id;
    private long create_time;
    private long update_time;
    private String create_user;
    private String update_user;
    private String upload_type;
    private String type;
    private String name;
    private long size;
    private String ext;
    private String local_path;
    private String path;
    private String thumb_image_path;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    public String getCreate_user() {
        return create_user;
    }

    public void setCreate_user(String create_user) {
        this.create_user = create_user;
    }

    public String getUpdate_user() {
        return update_user;
    }

    public void setUpdate_user(String update_user) {
        this.update_user = update_user;
    }

    public String getUpload_type() {
        return upload_type;
    }

    public void setUpload_type(String upload_type) {
        this.upload_type = upload_type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getLocal_path() {
        return local_path;
    }

    public void setLocal_path(String local_path) {
        this.local_path = local_path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getThumb_image_path() {
        return thumb_image_path;
    }

    public void setThumb_image_path(String thumb_image_path) {
        this.thumb_image_path = thumb_image_path;
    }
}
