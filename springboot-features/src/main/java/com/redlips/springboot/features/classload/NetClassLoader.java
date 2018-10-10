package com.redlips.springboot.features.classload;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * @author 花落孤忆
 * @create 2018-10-09 18:22
 * @description 自定义网络类加载器
 */
public class NetClassLoader extends ClassLoader {
    // Class文件的URL
    private String url;

    public NetClassLoader(String url) {
        this.url = url;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = getClassDataFromNet(name);
        if(classData == null) {
            throw new ClassNotFoundException();
        } else {
            return defineClass(name, classData, 0, classData.length);
        }
    }

    private byte[] getClassDataFromNet(String className) {
        // 获取class文件网络路径
        String path = classNameToPath(className);
        try {
            URL url = new URL(path);
            InputStream is = url.openStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int bufferSize = 1024 * 4;
            byte[] buffer = new byte[bufferSize];
            int byteReadNum = 0;
            while ((byteReadNum = is.read(buffer)) != -1) {
                baos.write(buffer, 0, byteReadNum);
            }
            // ...省略解密过程
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String classNameToPath(String className) {
        return url + "/" + className.replace('.', '/').contains(".class");
    }
}
