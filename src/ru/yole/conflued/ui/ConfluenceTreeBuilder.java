package ru.yole.conflued.ui;

import com.intellij.ide.util.treeView.AbstractTreeBuilder;
import com.intellij.ide.util.treeView.IndexComparator;
import com.intellij.openapi.project.Project;
import ru.yole.conflued.model.ConfPage;
import ru.yole.conflued.model.PageListener;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;

/**
 * @author yole
 */
public class ConfluenceTreeBuilder extends AbstractTreeBuilder {
    public ConfluenceTreeBuilder(Project project, JTree tree, DefaultTreeModel treeModel) {
        super(tree, treeModel, new ConfluenceTreeStructure(project), IndexComparator.INSTANCE);
        initRootNode();

        project.getMessageBus().connect(this).subscribe(PageListener.LISTENER_TOPIC, new PageListener() {
            public void pageCreated(ConfPage page) {
                queueUpdateFrom(page.getSpace(), false);
            }

            public void pageUpdated(ConfPage page) {
                queueUpdateFrom(page, false);
            }
        });
    }
}
