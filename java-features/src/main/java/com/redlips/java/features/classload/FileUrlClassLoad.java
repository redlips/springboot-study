package com.redlips.java.features.classload;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

/**
 * @author 花落孤忆
 * @create 2018-10-09 17:49
 * @description 自定义File加载器，继承URLClassLoader实现
 */
public class FileUrlClassLoad extends URLClassLoader {
    public FileUrlClassLoad(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public FileUrlClassLoad(URL[] urls) {
        super(urls);
    }

    public FileUrlClassLoad(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }
}
