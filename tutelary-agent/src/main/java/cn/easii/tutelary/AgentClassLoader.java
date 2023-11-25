package cn.easii.tutelary;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class AgentClassLoader extends URLClassLoader {
    public AgentClassLoader(String jar, ClassLoader parent) throws MalformedURLException {
        super(new URL[] {new URL("file:" + jar)}, parent);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> loadedClass = findLoadedClass(name);
        if (loadedClass != null) {
            return loadedClass;
        }

        if (name != null && (name.startsWith("sun.") || name.startsWith("java."))) {
            return super.loadClass(name, resolve);
        }
        try {
            Class<?> aClass = findClass(name);
            if (resolve) {
                resolveClass(aClass);
            }
            return aClass;
        } catch (Exception e) {
            // ignore
        }
        return super.loadClass(name, resolve);
    }
}
