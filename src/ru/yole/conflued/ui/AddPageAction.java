package ru.yole.conflued.ui;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.IconLoader;
import ru.yole.conflued.model.ConfObject;
import ru.yole.conflued.model.ConfPage;
import ru.yole.conflued.model.ConfSpace;
import ru.yole.conflued.vfs.ConfluenceVirtualFile;
import ru.yole.conflued.vfs.ConfluenceVirtualFileSystem;

/**
 * @author yole
 */
@SuppressWarnings({"ComponentNotRegistered"})
public class AddPageAction extends AnAction {
    public AddPageAction() {
        super("Add Page", "Create a new Confluence page", IconLoader.getIcon("/icons/docs_16.gif"));
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        String title = Messages.showInputDialog(project, "Enter page title:", "Add Page", Messages.getQuestionIcon());
        if (title == null) {
            return;
        }
        ConfPage page = new ConfPage();
        ConfSpace space = getSpace(e);

        ConfObject selection = getConfObject(e);
        String parentId = selection instanceof ConfPage ? ((ConfPage) selection).getId() : "0";

        page.setSpace(space);
        page.setId(space.getServer().nextNewPageId());
        page.setTitle(title);
        page.setParentId(parentId);
        page.setLocallyModified(true);
        space.pages.add(page);
        ConfluenceVirtualFile vFile = ConfluenceVirtualFileSystem.getInstance().fileForNewPage(page);
        new OpenFileDescriptor(project, vFile).navigate(true);
    }

    @Override
    public void update(AnActionEvent e) {
        e.getPresentation().setEnabled(getSpace(e) != null);
    }

    private ConfSpace getSpace(AnActionEvent e) {
        ConfObject confObject = getConfObject(e);
        if (confObject instanceof ConfSpace) {
            return (ConfSpace) confObject;
        }
        if (confObject instanceof ConfPage) {
            return ((ConfPage) confObject).getSpace();
        }
        return null;
    }

    private ConfObject getConfObject(AnActionEvent e) {
        ConfObject[] confObjects = e.getData(ConfluenceToolWindow.CONF_OBJECTS_DATA_KEY);
        if (confObjects == null || confObjects.length != 1) {
            return null;
        }
        return confObjects[0];
    }
}
