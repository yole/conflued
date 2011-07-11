package ru.yole.conflued.ui;

import com.intellij.ide.util.treeView.NodeDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Comparing;
import com.intellij.openapi.util.IconLoader;
import ru.yole.conflued.model.ConfObject;
import ru.yole.conflued.model.ConfPage;
import ru.yole.conflued.model.ConfServer;
import ru.yole.conflued.model.ConfSpace;

/**
 * @author yole
 */
public class ConfObjectNode extends NodeDescriptor {
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
    public boolean update() {
        boolean nameChanged = !Comparing.equal(myName, myObject.getDisplayName());
        myName = myObject.getDisplayName();
        return nameChanged;
    }

    @Override
    public Object getElement() {
        return myObject;
    }

}
