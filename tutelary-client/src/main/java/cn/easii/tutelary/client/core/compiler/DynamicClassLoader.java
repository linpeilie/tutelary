package cn.easii.tutelary.client.core.compiler;

import java.util.HashMap;
import java.util.Map;

public class DynamicClassLoader extends ClassLoader {
    private final Map<String, MemoryByteCode> byteCodes = new HashMap<String, MemoryByteCode>();

    public DynamicClassLoader(ClassLoader classLoader) {
        super(classLoader);
    }

    public void registerCompiledSource(MemoryByteCode byteCode) {
        byteCodes.put(byteCode.getClassName(), byteCode);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        MemoryByteCode byteCode = byteCodes.get(name);
        if (byteCode == null) {
            return super.findClass(name);
        }

        return super.defineClass(name, byteCode.getByteCode(), 0, byteCode.getByteCode().length);
    }

    public Map<String, byte[]> getByteCodes() {
        Map<String, byte[]> result = new HashMap<String, byte[]>(byteCodes.size());
        for (Map.Entry<String, MemoryByteCode> entry : byteCodes.entrySet()) {
            result.put(entry.getKey(), entry.getValue().getByteCode());
        }
        return result;
    }
}
