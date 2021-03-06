package eu.wdaqua.dblp.ontology;

public class Mapping {
    private String tag;
    private String propertyUri;
    private Type type;

    public Mapping(String tag, String propertyUri, Type type){
        this.tag = tag;
        this.propertyUri = propertyUri;
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public String getPropertyUri() {
        return propertyUri;
    }

    public Type getType() {
        return type;
    }
}
