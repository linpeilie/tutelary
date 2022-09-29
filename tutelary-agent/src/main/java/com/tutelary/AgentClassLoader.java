package com.tutelary;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class AgentClassLoader extends URLClassLoader {
    public AgentClassLoader(String jar, ClassLoader parent) throws MalformedURLException {
        super(new URL[]{new URL("file:" + jar)}, parent);
    }
}
