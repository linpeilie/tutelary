package com.tutelary.common.extension;

public class ExtensionCoordinate {

    private String extensionPointName;
    private String uniqueIdentity;

    private Class extensionPointClass;
    private int commandCode;

    public ExtensionCoordinate(Class extPtClass, int commandCode) {
        this.extensionPointClass = extPtClass;
        this.extensionPointName = extPtClass.getName();
        this.commandCode = commandCode;
    }

    public static ExtensionCoordinate valueOf(Class extPtClass, int bizScenario) {
        return new ExtensionCoordinate(extPtClass, bizScenario);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ExtensionCoordinate other = (ExtensionCoordinate) obj;
        if (uniqueIdentity == null) {
            if (other.uniqueIdentity != null) {
                return false;
            }
        } else if (!uniqueIdentity.equals(other.uniqueIdentity)) {
            return false;
        }
        if (extensionPointName == null) {
            if (other.extensionPointName != null) {
                return false;
            }
        } else if (!extensionPointName.equals(other.extensionPointName)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (uniqueIdentity == null ? 0 : uniqueIdentity.hashCode());
        result = prime * result + (extensionPointName == null ? 0 : extensionPointName.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "ExtensionCoordinate [extensionPointName = " + extensionPointName + ", uniqueIdentity = " +
               uniqueIdentity;
    }

    public Class getExtensionPointClass() {
        return this.extensionPointClass;
    }

    public int getCommandCode() {
        return commandCode;
    }
}
