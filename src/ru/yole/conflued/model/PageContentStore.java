package ru.yole.conflued.model;

import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.util.io.FileUtil;

import java.io.File;
import java.io.IOException;

/**
 * @author yole
 */
public class PageContentStore {
    private File myPageStorePath;

    public static PageContentStore getInstance() {
        return ServiceManager.getService(PageContentStore.class);
    }

    public PageContentStore() {
        myPageStorePath = new File(PathManager.getSystemPath(), "confluencePages");
    }

    public void storeContent(String pageId, int version, String content) throws IOException {
        FileUtil.writeToFile(pagePath(pageId, version), content);
    }

    private File pagePath(String pageId, int version) {
        return new File(myPageStorePath, pageId + "#" + version + ".txt");
    }

    public String loadContent(String pageId, int version) throws IOException {
        return FileUtil.loadFile(pagePath(pageId, version));
    }
}
