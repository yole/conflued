package ru.yole.conflued.ui;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.util.treeView.NodeDescriptor;
import com.intellij.ide.util.treeView.PresentableNodeDescriptor;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.vcs.FileStatus;
import com.intellij.ui.SimpleTextAttributes;
import ru.yole.conflued.model.ConfObject;
import ru.yole.conflued.model.ConfPage;
import ru.yole.conflued.model.ConfServer;
import ru.yole.conflued.model.ConfSpace;

import java.awt.*;

/**
 * @author yole
 */
public class ConfObjectNode extends PresentableNodeDescriptor {
    private final ConfObject myObject;

    public ConfObjectNode(Project project, NodeDescriptor parentDescriptor, ConfObject confObject) {
        super(project, parentDescriptor);
        myObject = confObject;
        if (myObject instanceof ConfServer) {
            myOpenIcon = IconLoader.getIcon("/icons/favicon.png");
        }
        else if (myObject instanceof ConfSpace) {
            myOpenIcon = IconLoader.getIcon("/icons/web_16.gif");
        }
        else if (myObject instanceof ConfPage) {
            myOpenIcon = IconLoader.getIcon("/icons/docs_16.gif");
        }
        myClosedIcon = myOpenIcon;
    }

    @Override
    protected void update(PresentationData presentation) {
        presentation.setIcons(myOpenIcon);
        Color color = null;
        if (myObject instanceof ConfPage) {
            ConfPage page = (ConfPage) myObject;
            if (page.isLocallyModified()) {
                color = EditorColorsManager.getInstance().getGlobalScheme().getColor(FileStatus.MODIFIED.getColorKey());
            }
        }

        presentation.addText(myObject.getDisplayName(),
                color == null ? SimpleTextAttributes.REGULAR_ATTRIBUTES : new SimpleTextAttributes(0, color));
    }

    @Override
    public Object getElement() {
        return myObject;
    }

}
