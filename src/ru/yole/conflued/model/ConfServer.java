package ru.yole.conflued.model;

import com.intellij.util.xmlb.annotations.Transient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yole
 */
public class ConfServer implements ConfObject {
    private String myURL;
    private String myUserName;
    private String myPassword;

    public List<ConfSpace> spaces = new ArrayList<ConfSpace>();

    public String getURL() {
        return myURL;
    }

    public void setURL(String URL) {
        myURL = URL;
    }

    public String getUserName() {
        return myUserName;
    }

    public void setUserName(String userName) {
        myUserName = userName;
    }

    public String getPassword() {
        return myPassword;
    }

    public void setPassword(String password) {
        myPassword = password;
    }

    public void loaded() {
        for (ConfSpace space : spaces) {
            space.loaded(this);
        }
    }

    @Transient
    public ConfObject getParent() {
        return ConfServers.getInstance();
    }

    @Transient
    public String getDisplayName() {
        return myURL;
    }
}
