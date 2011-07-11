package ru.yole.conflued.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

/**
 * @author yole
 */
public class ConfluenceToolWindowFactory implements ToolWindowFactory {
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        ConfluenceToolWindow confluenceToolWindow = new ConfluenceToolWindow(project);
        Content content = ContentFactory.SERVICE.getInstance().createContent(confluenceToolWindow, "", false);
        toolWindow.getContentManager().addContent(content);
    }
}
