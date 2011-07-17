package ru.yole.conflued.ui;

import com.intellij.ide.util.treeView.AbstractTreeStructure;
import com.intellij.ide.util.treeView.NodeDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.util.ArrayUtil;
import org.jetbrains.annotations.NotNull;
import ru.yole.conflued.model.*;

import java.util.List;

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
            List<ConfPage> pages = ((ConfSpace) element).findPagesWithParent("0");
            return pages.toArray(new Object[pages.size()]);
        }
        if (element instanceof ConfPage) {
            ConfPage page = (ConfPage) element;
            List<ConfPage> pages = page.getSpace().findPagesWithParent(page.getId());
            return pages.toArray(new Object[pages.size()]);
        }
        return ArrayUtil.EMPTY_OBJECT_ARRAY;
    }

    @Override
    public Object getParentElement(Object o) {
        if (o instanceof ConfPage) {
            ConfPage page = (ConfPage) o;
            if (page.getParentId() != null) {
                return page.getSpace().findPageById(page.getParentId());
            }
            return page.getSpace();
        }
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
