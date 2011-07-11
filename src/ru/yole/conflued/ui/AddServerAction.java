package ru.yole.conflued.ui;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.util.Icons;
import ru.yole.conflued.model.ConfServer;
import ru.yole.conflued.model.ConfServers;

/**
 * @author yole
 */
@SuppressWarnings({"ComponentNotRegistered"})
public class AddServerAction extends AnAction {
    public AddServerAction() {
        super("Add Server", "Configure a new Confluence server", Icons.ADD_ICON);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        ConfigureServerDialog dialog = new ConfigureServerDialog(project);
        dialog.setTitle("Add Server");
        dialog.show();
        if (dialog.getExitCode() != DialogWrapper.OK_EXIT_CODE) {
            return;
        }
        ConfServer server = new ConfServer();
        dialog.updateServer(server);
        ConfServers.getInstance().servers.add(server);

        ConfluenceToolWindow toolWindow = e.getData(ConfluenceToolWindow.TOOL_WINDOW_DATA_KEY);
        if (toolWindow != null) {
            toolWindow.updateTree(null);
        }
    }
}
