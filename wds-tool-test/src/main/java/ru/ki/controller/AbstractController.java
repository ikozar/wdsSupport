package ru.ki.controller;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import ru.ki.model.FindResult;
import ru.ki.service.CustomJacksonObjectMapper;
import ru.ki.service.exception.ExceptionCode;
import ru.ki.service.exception.ExceptionWithCode;

/**
 * @author ikozar
 * date    23.06.13
 */
public abstract class AbstractController {

    private Logger log;
    public static final String PNAME = "data";

    @Autowired
    protected CustomJacksonObjectMapper customJacksonObjectMapper;

    protected AbstractController() {
        this.log = LoggerFactory.getLogger(getClass());
    }

    public Logger getLog() {
        return log;
    }

    protected ModelAndView createModelAndView(Object object) {
        if (object != null) {
            return createModelAndView("item", object);
        }
        return null;
    }

    protected ModelAndView createModelAndView(FindResult findResult) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("total", findResult.getCount());
        mav.addObject("list", findResult.getResultList());
        return mav;
    }

    protected ModelAndView createModelAndView(String property, Object object) {
        ModelAndView mav = new ModelAndView();
        mav.addObject(property, object);
        return mav;
    }

    public static ModelAndView createErrorResponse(Exception e) {
        return createErrorResponse(ExceptionCode.UNDEFINED.name(), ExceptionUtils.getRootCauseMessage(e));
    }

    public static ModelAndView createErrorResponse(ExceptionWithCode e) {
        return createErrorResponse(e.getExceptionCode().name(), e.getMessage());
    }

    public static ModelAndView createErrorResponse(String code, String message) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("errorCode", code);
        mav.addObject("errorDescription", message);
        return mav;
    }

}
