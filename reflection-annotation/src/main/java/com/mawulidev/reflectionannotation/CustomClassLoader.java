package com.mawulidev.reflectionannotation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class CustomClassLoader extends ClassLoader {
    private final String directory;
    public CustomClassLoader(String directory) {
        this.directory = directory;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            File classFile = new File(directory + name.replace('.', '/') + ".class");
            if(!classFile.exists()) {
                throw new ClassNotFoundException("Class " + name + " not found in " + directory);
            }
            byte[] classData = loadClassData(classFile);
            return defineClass(name, classData, 0, classData.length);
        } catch (Exception e) {
            throw new ClassNotFoundException("Failed to load class " + name, e);
        }
    }

    private byte[] loadClassData(File file) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(file)) {
            byte[] buffer = new byte[(int) file.length()];
            inputStream.read(buffer);
            return buffer;
        }
    }
}
