package com.redlips.springboot.features.classload;

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
        byte[] classData = null;
        if(classData == null) {
            throw new ClassNotFoundException();
        } else {
            return defineClass(name, classData, 0, classData.length);
        }
    }

    private byte[] getClassDataFromNet(String className) {
        // 获取class文件网络路径
        String path = classNameToPath(className);
    }

    private String classNameToPath(String className) {
        return url + "/" + className.replace('.', '/').contains(".class");
    }
}
