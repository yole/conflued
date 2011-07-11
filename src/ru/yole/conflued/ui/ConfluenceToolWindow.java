package ru.yole.conflued.ui;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.pom.Navigatable;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.treeStructure.Tree;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.Nullable;
import ru.yole.conflued.model.ConfObject;
import ru.yole.conflued.model.ConfPage;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author yole
 */
public class ConfluenceToolWindow extends SimpleToolWindowPanel {
    private Tree myTree;
    private ConfluenceTreeBuilder myTreeBuilder;

    public ConfluenceToolWindow(Project project) {
        super(true);
        final DefaultTreeModel treeModel = new DefaultTreeModel(new DefaultMutableTreeNode());
        myTree = new Tree(treeModel);
        myTree.setRootVisible(false);
        myTree.setShowsRootHandles(true);
        myTreeBuilder = new ConfluenceTreeBuilder(project, myTree, treeModel);
        setContent(ScrollPaneFactory.createScrollPane(myTree));
        setToolbar(createToolbarPanel());
    }

    private JComponent createToolbarPanel() {
        final DefaultActionGroup group = new DefaultActionGroup();
        group.add(new AddServerAction());
        group.add(new RefreshAction());
        final ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar("Confluence", group, true);
        return toolbar.getComponent();
    }

    public void updateTree(@Nullable Object changedObject) {
        myTreeBuilder.queueUpdateFrom(changedObject, false, true);
    }

    @Override
    public Object getData(@NonNls String dataId) {
        if (TOOL_WINDOW_DATA_ID.equals(dataId)) {
            return this;
        }
        else if (CONF_OBJECTS_DATA_ID.equals(dataId)) {
            Set<ConfObject> selection = myTreeBuilder.getSelectedElements(ConfObject.class);
            return selection.toArray(new ConfObject[selection.size()]);
        }
        else if (DataConstants.NAVIGATABLE_ARRAY.equals(dataId)) {
            List<Navigatable> navigatables = new ArrayList<Navigatable>();
            for (ConfPage page : myTreeBuilder.getSelectedElements(ConfPage.class)) {
                navigatables.add(new ConfPageNavigatable(page));
            }
            return navigatables.toArray(new Navigatable[navigatables.size()]);
        }
        return super.getData(dataId);
    }

    public static final String TOOL_WINDOW_DATA_ID = "ru.yole.conflued.ConfluenceToolWindow";
    public static final DataKey<ConfluenceToolWindow> TOOL_WINDOW_DATA_KEY = DataKey.create(TOOL_WINDOW_DATA_ID);

    public static final String CONF_OBJECTS_DATA_ID = "ru.yole.conflued.ConfObjects";
    public static final DataKey<ConfObject[]> CONF_OBJECTS_DATA_KEY = DataKey.create(CONF_OBJECTS_DATA_ID);

    private static class ConfPageNavigatable implements Navigatable {
        private final ConfPage myPage;

        public ConfPageNavigatable(ConfPage page) {
            myPage = page;
        }

        public void navigate(boolean requestFocus) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        public boolean canNavigate() {
            return false;
        }

        public boolean canNavigateToSource() {
            return false;
        }
    }
}
