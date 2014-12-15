package ru.ki.controller;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ikozar
 * date    23.06.13
 */
public class ExceptionResolver implements HandlerExceptionResolver, Ordered {
    public int getOrder() {
        return Integer.MIN_VALUE;
    }

    public ModelAndView resolveException(
            HttpServletRequest aReq, HttpServletResponse aRes,
            Object aHandler, Exception e) {
        e.printStackTrace();
        return AbstractController.createErrorResponse(e);
    }
}
