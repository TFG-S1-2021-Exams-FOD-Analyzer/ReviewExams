package gastmappers;

public class MappedMethod {
    /**
     * Estas variables mantienen informacion sobre la instancia del método actual mapeado
     */
    private String className = null;
    private String packageName = null;
    private String signature = null;
    private String methodName = null;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
