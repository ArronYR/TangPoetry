package com.helloarron.tpandroid.bean;

/**
 * Created by arron on 16/10/17.
 */

public class PoetBean {

    /**
     * id : 155
     * name : 白居易
     * created_at : 2014-06-02T03:49:11.000Z
     * updated_at : 2014-06-02T03:49:11.000Z
     */

    private int id;
    private String name;
    private String created_at;
    private String updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
