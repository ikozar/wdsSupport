package ru.ki.service.model;

/**
 * @author ikozar
 * date    22.06.13
 */
public interface ICommonEntity {
    public static final String NFLD_ID = "id";

    Long getId();
    void setId(Long id);
    String getName();
    void setName(String name);
}
