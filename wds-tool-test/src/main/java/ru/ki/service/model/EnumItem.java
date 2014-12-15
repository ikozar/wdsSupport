package ru.ki.service.model;

/**
 * @author ikozar
 * date    15.08.13
 */
public class EnumItem {
    private String id;
    private String title;

    public EnumItem(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
