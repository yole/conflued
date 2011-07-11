package ru.yole.conflued.ui;

import com.intellij.ide.util.treeView.AbstractTreeBuilder;
import com.intellij.ide.util.treeView.IndexComparator;
import com.intellij.openapi.project.Project;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;

/**
 * @author yole
 */
public class ConfluenceTreeBuilder extends AbstractTreeBuilder {
    public ConfluenceTreeBuilder(Project project, JTree tree, DefaultTreeModel treeModel) {
        super(tree, treeModel, new ConfluenceTreeStructure(project), IndexComparator.INSTANCE);
        initRootNode();
    }

}
