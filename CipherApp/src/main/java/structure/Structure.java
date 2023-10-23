package structure;

public class Structure {
    private String name;
    private String[] models;
    private String[] paddings;

    public Structure(String name, String[] models, String[] paddings) {
        this.name = name;
        this.models = models;
        this.paddings = paddings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getModels() {
        return models;
    }

    public void setModels(String[] models) {
        this.models = models;
    }

    public String[] getPaddings() {
        return paddings;
    }

    public void setPaddings(String[] paddings) {
        this.paddings = paddings;
    }
}
