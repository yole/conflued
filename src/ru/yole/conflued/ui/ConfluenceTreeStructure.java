package ru.yole.conflued.ui;

import com.intellij.ide.util.treeView.AbstractTreeStructure;
import com.intellij.ide.util.treeView.NodeDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.util.ArrayUtil;
import org.jetbrains.annotations.NotNull;
import ru.yole.conflued.model.*;

/**
 * @author yole
 */
public class ConfluenceTreeStructure extends AbstractTreeStructure {
    private final Project myProject;

    protected ConfluenceTreeStructure(Project project) {
        myProject = project;
    }

    @Override
    public Object getRootElement() {
        return ConfServers.getInstance();
    }

    @Override
    public void commit() {
    }

    @Override
    public boolean hasSomethingToCommit() {
        return false;
    }

    @Override
    public Object[] getChildElements(Object element) {
        if (element instanceof ConfServers) {
            return ConfServers.getInstance().servers.toArray();
        }
        if (element instanceof ConfServer) {
            return ((ConfServer) element).spaces.toArray();
        }
        if (element instanceof ConfSpace) {
            return ((ConfSpace) element).pages.toArray();
        }
        return ArrayUtil.EMPTY_OBJECT_ARRAY;
    }

    @Override
    public Object getParentElement(Object o) {
        if (o instanceof ConfObject) {
            return ((ConfObject) o).getParent();
        }
        return null;
    }

    @NotNull
    @Override
    public NodeDescriptor createDescriptor(Object o, NodeDescriptor nodeDescriptor) {
        if (o instanceof ConfObject) {
            return new ConfObjectNode(myProject, nodeDescriptor, (ConfObject) o);
        }
        throw new UnsupportedOperationException("unknown object " + o);
    }
}
