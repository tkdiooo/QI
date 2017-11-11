package com.qi.bootstrap.breadcrumb;

/**
 * Class Breadcrumb
 *
 * @author 张麒 2017-11-8.
 * @version Description:
 */
public class Breadcrumb {

    private String text;
    private String url;
    private String cls;

    public Breadcrumb() {

    }

    public Breadcrumb(String text, String url, String cls) {
        this.text = text;
        this.url = url;
        this.cls = cls;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCls() {
        return cls;
    }

    public void setCls(String cls) {
        this.cls = cls;
    }
}
