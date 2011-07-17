package ru.yole.conflued.vfs;

import com.intellij.openapi.vfs.CharsetToolkit;
import com.intellij.openapi.vfs.DeprecatedVirtualFile;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileSystem;
import org.jetbrains.annotations.NotNull;
import ru.yole.conflued.client.ConfluenceClient;
import ru.yole.conflued.model.ConfPage;
import ru.yole.conflued.model.PageContentStore;

import java.io.*;
import java.nio.charset.Charset;

/**
 * @author yole
 */
public class ConfluenceVirtualFile extends DeprecatedVirtualFile {
    private final ConfPage myPage;

    public ConfluenceVirtualFile(ConfPage page) {
        myPage = page;
    }

    @NotNull
    @Override
    public String getName() {
        return myPage.getTitle() + ".txt";
    }

    @NotNull
    @Override
    public VirtualFileSystem getFileSystem() {
        return ConfluenceVirtualFileSystem.getInstance();
    }

    @Override
    public String getPath() {
        return getName();
    }

    @Override
    public boolean isWritable() {
        return true;
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public VirtualFile getParent() {
        return null;
    }

    @Override
    public VirtualFile[] getChildren() {
        return VirtualFile.EMPTY_ARRAY;
    }

    @Override
    public long getModificationStamp() {
        return myPage.getVersion();
    }

    @NotNull
    @Override
    public OutputStream getOutputStream(Object requestor, long newModificationStamp, long newTimeStamp) throws IOException {
        return new ByteArrayOutputStream() {
            @Override
            public void close() throws IOException {
                myPage.setLocallyModified(true);
                String content = toString(CharsetToolkit.UTF8);
                PageContentStore.getInstance().storeLocallyModifiedContent(myPage.getId(), content);
                ConfluenceClient.getInstance().updatePage(myPage, content).doWhenDone(new Runnable() {
                    public void run() {
                        myPage.setLocallyModified(false);
                    }
                });
            }
        };
    }

    @NotNull
    @Override
    public byte[] contentsToByteArray() throws IOException {
        PageContentStore contentStore = PageContentStore.getInstance();
        String content = myPage.isLocallyModified()
                ? contentStore.loadLocallyModifiedContent(myPage.getId())
                : (contentStore.hasContent(myPage.getId(), myPage.getVersion())
                  ? contentStore.loadContent(myPage.getId(), myPage.getVersion())
                  : "Loading...");
        return content.getBytes(CharsetToolkit.UTF8_CHARSET);
    }

    @Override
    public long getTimeStamp() {
        return myPage.getVersion();
    }

    @Override
    public long getLength() {
        String content;
        try {
            content = PageContentStore.getInstance().loadContent(myPage.getId(), myPage.getVersion());
        } catch (IOException e) {
            return -1;
        }
        return content.getBytes(CharsetToolkit.UTF8_CHARSET).length;
    }

    @Override
    public void refresh(boolean asynchronous, boolean recursive, Runnable postRunnable) {
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(contentsToByteArray());
    }

    @Override
    public Charset getCharset() {
        return CharsetToolkit.UTF8_CHARSET;
    }
}
