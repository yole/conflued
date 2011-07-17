package ru.yole.conflued.model;

import com.intellij.util.messages.Topic;

/**
 * @author yole
 */
public interface PageListener {
    void pageCreated(ConfPage page);
    void pageUpdated(ConfPage page);

    Topic<PageListener> LISTENER_TOPIC = Topic.create("ru.yole.conflued.PageListener", PageListener.class);
}
