package ru.yole.conflued.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.pom.NavigatableWithText;
import ru.yole.conflued.model.ConfServer;

/**
 * @author yole
 */
public class ConfServerNavigatable implements NavigatableWithText {
    private final Project myProject;
    private final ConfServer myServer;

    public ConfServerNavigatable(Project project, ConfServer server) {
        myProject = project;
        myServer = server;
    }

    public void navigate(boolean requestFocus) {
        ConfigureServerDialog dialog = new ConfigureServerDialog(myProject);
        dialog.setTitle("Edit Server Settings");
        dialog.updateFromServer(myServer);
        dialog.show();
        if (dialog.getExitCode() != DialogWrapper.OK_EXIT_CODE) {
            return;
        }
        dialog.updateServer(myServer);
        ConfluenceToolWindow.getInstance(myProject).updateTree(myServer);
    }

    public boolean canNavigate() {
        return true;
    }

    public boolean canNavigateToSource() {
        return false;
    }

    public String getNavigateActionText(boolean focusEditor) {
        return "Edit Server Settings";
    }
}
