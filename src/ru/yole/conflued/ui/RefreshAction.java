package ru.yole.conflued.ui;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.util.ActionCallback;
import com.intellij.openapi.util.Pair;
import com.intellij.util.Consumer;
import com.intellij.util.Icons;
import ru.yole.conflued.client.ConfluenceClient;
import ru.yole.conflued.model.*;
import ru.yole.conflued.vfs.ConfluenceVirtualFileSystem;

import java.io.IOException;

/**
 * @author yole
 */
@SuppressWarnings({"ComponentNotRegistered"})
public class RefreshAction extends AnAction implements DumbAware {
    public RefreshAction() {
        super("Refresh", "Refresh the server, space or page selected in the tree", Icons.SYNCHRONIZE_ICON);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        final ConfluenceToolWindow toolWindow = e.getData(ConfluenceToolWindow.TOOL_WINDOW_DATA_KEY);
        final ConfObject[] selectedObjects = e.getData(ConfluenceToolWindow.CONF_OBJECTS_DATA_KEY);
        if (toolWindow == null || selectedObjects == null) return;
        for (final ConfObject object : selectedObjects) {
            refreshObject(object).doWhenDone(new Runnable() {
                public void run() {
                    toolWindow.updateTree(object);
                }
            });
        }
    }

    public static ActionCallback refreshObject(ConfObject object) {
        final ConfluenceClient client = ConfluenceClient.getInstance();
        if (object instanceof ConfServer) {
            return client.updateSpaces((ConfServer) object);
        }
        else if (object instanceof ConfSpace) {
            return client.updatePages((ConfSpace) object);
        }
        else if (object instanceof ConfPage) {
            return refreshPage((ConfPage) object);
        }
        return new ActionCallback.Done();
    }

    public static ActionCallback refreshPage(ConfPage object) {
        return ConfluenceClient.getInstance().refreshPage(object, new Consumer<Pair<ConfPage, String>>() {
            public void consume(Pair<ConfPage, String> confPageStringPair) {
                ConfPage page = confPageStringPair.first;
                try {
                    PageContentStore.getInstance().storeContent(page.getId(), page.getVersion(), confPageStringPair.second);
                    ConfluenceVirtualFileSystem.getInstance().pageUpdated(page);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void update(AnActionEvent e) {
        final ConfObject[] selectedObjects = e.getData(ConfluenceToolWindow.CONF_OBJECTS_DATA_KEY);
        e.getPresentation().setEnabled(selectedObjects != null && selectedObjects.length > 0 &&
                !ConfluenceClient.getInstance().isOffline());
    }
}
