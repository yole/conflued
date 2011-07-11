package ru.yole.conflued.model;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yole
 */
@State(name="ConfServers", storages = {@Storage(id="other", file="$APP_CONFIG$/confluence.xml")})
public class ConfServers implements PersistentStateComponent<ConfServers>, ConfObject {
    public List<ConfServer> servers = new ArrayList<ConfServer>();

    public static ConfServers getInstance() {
        return ServiceManager.getService(ConfServers.class);
    }

    public ConfServers getState() {
        return this;
    }

    public void loadState(ConfServers confServers) {
        XmlSerializerUtil.copyBean(confServers, this);
        for (ConfServer server : servers) {
            server.loaded();
        }
    }

    public ConfObject getParent() {
        return null;
    }

    public String getDisplayName() {
        return "";
    }
}
