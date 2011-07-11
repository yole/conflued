package ru.yole.conflued.model;

import com.intellij.util.xmlb.annotations.Transient;

/**
 * @author yole
 */
public class ConfPage implements ConfObject {
    private ConfSpace mySpace;
    private String myId;
    private String myParentId;
    private String myTitle;
    private int myVersion;

    public void loaded(ConfSpace space) {
        mySpace = space;
    }

    @Transient
    public ConfSpace getSpace() {
        return mySpace;
    }

    @Transient
    public void setSpace(ConfSpace space) {
        mySpace = space;
    }

    public String getId() {
        return myId;
    }

    public void setId(String id) {
        myId = id;
    }

    public String getParentId() {
        return myParentId;
    }

    public void setParentId(String parentId) {
        myParentId = parentId;
    }

    public String getTitle() {
        return myTitle;
    }

    public void setTitle(String title) {
        myTitle = title;
    }

    public int getVersion() {
        return myVersion;
    }

    public void setVersion(int version) {
        myVersion = version;
    }

    @Transient
    public ConfObject getParent() {
        return mySpace;
    }

    @Transient
    public String getDisplayName() {
        return myTitle;
    }
}
