package ru.yole.conflued.ui;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ToggleAction;
import com.intellij.openapi.util.IconLoader;
import ru.yole.conflued.client.ConfluenceClient;

/**
 * @author yole
 */
@SuppressWarnings({"ComponentNotRegistered"})
public class ToggleOfflineAction extends ToggleAction {
    public ToggleOfflineAction() {
        super("Offline Mode", "Toggle offline mode", IconLoader.getIcon("/icons/offlineMode.png"));
    }

    @Override
    public boolean isSelected(AnActionEvent e) {
        return ConfluenceClient.getInstance().isOffline();
    }

    @Override
    public void setSelected(AnActionEvent e, boolean state) {
        ConfluenceClient.getInstance().setOffline(state);
    }
}
