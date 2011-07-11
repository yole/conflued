package ru.yole.conflued.model;

import com.intellij.util.xmlb.annotations.Transient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yole
 */
public class ConfSpace implements ConfObject {
    private ConfServer myServer;
    private String myKey;
    private String myName;
    public List<ConfPage> pages = new ArrayList<ConfPage>();

    @Transient
    public ConfServer getServer() {
        return myServer;
    }

    public void loaded(ConfServer server) {
        myServer = server;
        for (ConfPage page : pages) {
            page.loaded(this);
        }
    }

    public String getKey() {
        return myKey;
    }

    public void setKey(String key) {
        myKey = key;
    }

    public String getName() {
        return myName;
    }

    public void setName(String name) {
        myName = name;
    }

    @Transient
    public ConfObject getParent() {
        return myServer;
    }

    @Transient
    public String getDisplayName() {
        return myName;
    }
}
