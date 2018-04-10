package com.qi.bootstrap.breadcrumb;

import java.util.HashMap;
import java.util.Map;

/**
 * Class Breadcrumb
 *
 * @author 张麒 2017-11-8.
 * @version Description:
 */
public class Breadcrumb {

    private String text;
    private String url;
    private Map<String, String> params = new HashMap<>();
    private String cls;

    public Breadcrumb() {

    }

    public Breadcrumb(String text, String cls) {
        this.text = text;
        this.cls = cls;
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

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public void addParams(String key, String value) {
        this.params.put(key, value);
    }

    public String getCls() {
        return cls;
    }

    public void setCls(String cls) {
        this.cls = cls;
    }
}
