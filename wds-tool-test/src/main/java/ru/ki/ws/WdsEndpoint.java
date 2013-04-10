package ru.ki.ws;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.ki.dao.EmployeeDao;
import ru.ki.model.FindResult;
import ru.ki.model.EmployeeVO;
import ru.ki.model.WsFindResult;
import ru.ki.model.query.QueryDescriptor;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.Arrays;

/**
 * @author ikozar
 * date    09.04.13
 */
//@Service("documentServiceEndpoint")
@WebService(serviceName = "WebDataService")
public class WdsEndpoint extends SpringBeanAutowiringSupport {

    private static final Logger LOG = LoggerFactory.getLogger(WdsEndpoint.class);

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private EmployeeDao employeeDao;

    @WebMethod
    public WsFindResult findEmployee(
            @WebParam(name = "queryDescriptor") QueryDescriptor queryDescriptor) {
        try {
            Class retClass = Class.forName(queryDescriptor.getReturnType());
            WsFindResult fr = new WsFindResult(
                    employeeDao.find(queryDescriptor.getSearchParameters(), retClass));
            return fr;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

}
