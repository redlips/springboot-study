package com.redlips.springboot.features.classload;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author 花落孤忆
 * @create 2018-10-09 14:20
 * @description 自定义类加载器ClassLoader
 */
public class FileClassLoader extends ClassLoader {
    private String rootDir;

    public FileClassLoader(String rootDir) {
        this.rootDir = rootDir;
    }

    /**
     * 编写获取类的字节码并创建class对象的逻辑
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // 获取类的class文件字节数组
        byte[] classData = getClassData(name);
        if(classData == null) {
            throw new ClassNotFoundException();
        } else {
            return defineClass(name, classData, 0, classData.length);
        }
    }

    /**
     * 编写获取class文件并转换为字节码流的逻辑
     * @param className
     * @return
     */
    private byte[] getClassData(String className) {
        // 读取类文件的字节
        String path = classNameToPath(className);

        try {
            InputStream ins = new FileInputStream(path);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int bufferSize = 1024 * 4;
            byte[] buffer = new byte[bufferSize];
            int byteNumRead = 0;
            // 读取类文件的字节码
            while ((byteNumRead = ins.read(buffer)) != -1) {
                baos.write(buffer, 0, byteNumRead);
            }
            return baos.toByteArray();
        } catch (Exception e) {

        }
        return null;
    }

    public String classNameToPath(String className) {
        return rootDir + File.separatorChar + className.replace('.', File.separatorChar).concat(".class");
    }

    @Override
    public String toString() {
        return "file自定义类加载器";
    }
}
