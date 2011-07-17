package ru.yole.conflued.vfs;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.vfs.DeprecatedVirtualFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.openapi.vfs.newvfs.BulkFileListener;
import com.intellij.openapi.vfs.newvfs.events.VFileContentChangeEvent;
import com.intellij.openapi.vfs.newvfs.events.VFileCreateEvent;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import com.intellij.util.messages.MessageBus;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import ru.yole.conflued.model.ConfPage;
import ru.yole.conflued.model.PageListener;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.WeakHashMap;

/**
 * @author yole
 */
public class ConfluenceVirtualFileSystem extends DeprecatedVirtualFileSystem {
    private final WeakHashMap<ConfPage, ConfluenceVirtualFile> myAllPages = new WeakHashMap<ConfPage, ConfluenceVirtualFile>();
    private final MessageBus myBus;

    public static ConfluenceVirtualFileSystem getInstance() {
        return ApplicationManager.getApplication().getComponent(ConfluenceVirtualFileSystem.class);
    }

    public ConfluenceVirtualFileSystem(MessageBus bus) {
        myBus = bus;
    }

    @NotNull
    @Override
    public String getProtocol() {
        return "confluence";
    }

    @Override
    public VirtualFile findFileByPath(@NotNull @NonNls String path) {
        return null;
    }

    @Override
    public void refresh(boolean asynchronous) {
    }

    @Override
    public VirtualFile refreshAndFindFileByPath(@NotNull String path) {
        return null;
    }

    @Override
    protected void deleteFile(Object requestor, @NotNull VirtualFile vFile) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void moveFile(Object requestor, @NotNull VirtualFile vFile, @NotNull VirtualFile newParent) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void renameFile(Object requestor, @NotNull VirtualFile vFile, @NotNull String newName) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected VirtualFile createChildFile(Object requestor, @NotNull VirtualFile vDir, @NotNull String fileName) throws IOException {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    protected VirtualFile createChildDirectory(Object requestor, @NotNull VirtualFile vDir, @NotNull String dirName) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected VirtualFile copyFile(Object requestor, @NotNull VirtualFile virtualFile, @NotNull VirtualFile newParent, @NotNull String copyName) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    public ConfluenceVirtualFile getVFile(ConfPage page) {
        ConfluenceVirtualFile vFile = myAllPages.get(page);
        if (vFile == null) {
            vFile = new ConfluenceVirtualFile(page);
            myAllPages.put(page, vFile);
        }
        return vFile;
    }

    public void pageUpdated(final ConfPage page) {
        final ConfluenceVirtualFile vFile = myAllPages.get(page);
        if (vFile != null) {
            ApplicationManager.getApplication().invokeLater(new Runnable() {
                public void run() {
                    fireContentsChanged(vFile);
                }
            });
        }
    }

    public ConfluenceVirtualFile fileForNewPage(final ConfPage page) {
        final ConfluenceVirtualFile vFile = getVFile(page);
        myBus.syncPublisher(PageListener.LISTENER_TOPIC).pageCreated(page);
        return vFile;
    }

    private void fireContentsChanged(ConfluenceVirtualFile vFile) {
        ConfPage page = vFile.getPage();
        fireEvent(new VFileContentChangeEvent(this, vFile, page.getVersion(), page.getVersion(), false));
        notifyPageUpdated(page);
    }

    public void notifyPageUpdated(ConfPage page) {
        myBus.syncPublisher(PageListener.LISTENER_TOPIC).pageUpdated(page);
    }

    private void fireEvent(final VFileEvent event) {
        final Application application = ApplicationManager.getApplication();
        application.runWriteAction(new Runnable() {
            public void run() {
                List<VFileEvent> events = Collections.singletonList(event);
                BulkFileListener listener = application.getMessageBus().syncPublisher(VirtualFileManager.VFS_CHANGES);
                listener.before(events);
                listener.after(events);
            }
        });
    }
}
