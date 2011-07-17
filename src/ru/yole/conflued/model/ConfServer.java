package ru.yole.conflued.model;

import com.intellij.util.xmlb.annotations.Transient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yole
 */
public class ConfServer implements ConfObject {
    public static final String NEW_PAGE_ID_PREFIX = "#new#";

    private String myURL;
    private String myUserName;
    private String myPassword;
    private int myLastNewPageId;
    private boolean myLoginFailed;

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
        myLoginFailed = false;
    }

    public String getPassword() {
        return myPassword;
    }

    public void setPassword(String password) {
        myPassword = password;
        myLoginFailed = false;
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

    public int getLastNewPageId() {
        return myLastNewPageId;
    }

    public void setLastNewPageId(int lastNewPageId) {
        myLastNewPageId = lastNewPageId;
    }

    public String nextNewPageId() {
        return NEW_PAGE_ID_PREFIX + ++myLastNewPageId;
    }

    public boolean isLoginFailed() {
        return myLoginFailed;
    }

    public void setLoginFailed(boolean loginFailed) {
        myLoginFailed = loginFailed;
    }
}
