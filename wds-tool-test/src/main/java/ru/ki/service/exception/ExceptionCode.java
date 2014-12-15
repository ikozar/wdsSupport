package ru.ki.service.exception;

/**
 * @author ikozar
 * date    23.06.13
 */
public enum ExceptionCode {
    UNDEFINED("Ошибка сервера"),
    BAD_URL("Не корректный URL запроса"),
    BAD_PARAMS("Не корректные параметры запроса");

    private String title;

    private ExceptionCode(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
