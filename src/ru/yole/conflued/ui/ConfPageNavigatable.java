package ru.yole.conflued.ui;

import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.pom.Navigatable;
import ru.yole.conflued.client.ConfluenceClient;
import ru.yole.conflued.model.ConfPage;
import ru.yole.conflued.model.PageContentStore;
import ru.yole.conflued.vfs.ConfluenceVirtualFile;
import ru.yole.conflued.vfs.ConfluenceVirtualFileSystem;

/**
* @author yole
*/
class ConfPageNavigatable implements Navigatable {
    private final Project myProject;
    private final ConfPage myPage;

    public ConfPageNavigatable(Project project, ConfPage page) {
        myProject = project;
        myPage = page;
    }

    public void navigate(boolean requestFocus) {
        ConfluenceVirtualFile vFile = ConfluenceVirtualFileSystem.getInstance().getVFile(myPage);
        if (!PageContentStore.getInstance().hasContent(myPage.getId(), myPage.getVersion())) {
            RefreshAction.refreshPage(myPage);
        }
        new OpenFileDescriptor(myProject, vFile).navigate(requestFocus);
    }

    public boolean canNavigate() {
        return !ConfluenceClient.getInstance().isOffline() ||
                PageContentStore.getInstance().hasContent(myPage.getId(), myPage.getVersion());
    }

    public boolean canNavigateToSource() {
        return false;
    }
}
