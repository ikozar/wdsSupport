package ru.ki.model.query;

/**
 * @author ikozar
 */
public class FieldAdequacy {
    private String field1;
    private String field2;
    private boolean reverse = false;

    public FieldAdequacy(String field1, String field2) {
        this.field1 = field1;
        this.field2 = field2;
    }

    public String getFieldSrc() {
        return reverse ? field2 : field1;
    }

    public String getFieldDest() {
        return reverse ? field1 : field2;
    }

    public boolean isReverse() {
        return reverse;
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }
}
