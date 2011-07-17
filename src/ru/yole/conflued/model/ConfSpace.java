package ru.yole.conflued.model;

import com.intellij.openapi.util.Comparing;
import com.intellij.util.xmlb.annotations.Transient;
import org.jetbrains.annotations.Nullable;

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

    public List<ConfPage> findPagesWithParent(@Nullable String parentId) {
        List<ConfPage> result = new ArrayList<ConfPage>();
        for (ConfPage page : pages) {
            if (Comparing.equal(page.getParentId(), parentId)) {
                result.add(page);
            }
        }
        return result;
    }

    public ConfPage findPageById(String id) {
        for (ConfPage page : pages) {
            if (page.getId().equals(id)) {
                return page;
            }
        }
        return null;
    }
}
