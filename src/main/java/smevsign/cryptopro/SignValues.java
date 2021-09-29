package smevsign.cryptopro;

public class SignValues {
    private String referenceId;
    private String element;
    private String namespace;
    private String className;

    public SignValues() {}
    public SignValues(String referenceId) {
        this.referenceId = referenceId;
    }



    public void setElementNs(String element, String namespace) {
        this.element = element;
        this.namespace = namespace;
    }
    public void setElement(String element) {
        this.element = element;
    }
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public String getElement() {
        return this.element;
    }
    public String getNamespace() {
        return this.namespace;
    }
    public String getReferenceId() {
        return referenceId;
    }
    public String getClassName() {
        return this.className;
    }
}
