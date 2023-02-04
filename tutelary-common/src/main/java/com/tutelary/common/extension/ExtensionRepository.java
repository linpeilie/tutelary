package com.tutelary.common.extension;

import java.util.HashMap;
import java.util.Map;

public class ExtensionRepository {

    private static final Map<ExtensionCoordinate, ExtensionPointI> EXTENSION_REPO = new HashMap<>();

    public static ExtensionPointI put(ExtensionCoordinate extensionCoordinate, ExtensionPointI extensionPoint) {
        return EXTENSION_REPO.put(extensionCoordinate, extensionPoint);
    }

    public static ExtensionPointI get(ExtensionCoordinate extensionCoordinate) {
        return EXTENSION_REPO.get(extensionCoordinate);
    }

}
