package com.helloarron.tpandroid.bean;

/**
 * Created by arron on 16/10/17.
 */

public class PoetryBean {

    private PoetBean poet;
    /**
     * poet : {}
     * id : 20694
     * title : 偶作
     * content : 张翰一杯酒，荣期三乐歌。聪明伤混沌，烦恼污头陀。簟冷秋生早，阶闲日上多。近来门更静，无雀可张罗。
     * created_at : 2014-06-02T04:15:00.000Z
     * updated_at : 2014-06-02T04:15:00.000Z
     */

    private String id;
    private String title;
    private String content;
    private String createdAt;

    public PoetBean getPoet() {
        return poet;
    }

    public void setPoet(PoetBean poet) {
        this.poet = poet;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String created_at) {
        this.createdAt = created_at;
    }

}
