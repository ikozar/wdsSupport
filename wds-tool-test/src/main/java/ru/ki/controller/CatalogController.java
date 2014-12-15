package ru.ki.controller;

import org.apache.commons.lang3.CharEncoding;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.ki.service.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author ikozar
 * date    23.06.13
 */

@Controller
@RequestMapping(value = "/support")
public class CatalogController extends AbstractController {

    @PersistenceContext
    private EntityManager em;


    @RequestMapping(value = "/entities", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE +
            "; charset=" + CharEncoding.UTF_8)
    public @ResponseBody
    String getEntities() throws Exception {
        StringBuilder sb = new StringBuilder();
        for (EntityType et : em.getEntityManagerFactory().getMetamodel().getEntities()) {
            sb.append(et.getJavaType().getName()).append(" {\n");
            if (et.getJavaType().isAnnotationPresent(Comment.class)) {
                sb.append("// ").append(((Comment) et.getJavaType().getAnnotation(Comment.class)).value());
            }
            for (Attribute att : (Set<Attribute>) et.getAttributes()) {
                sb.append("\n\t").append(att.getJavaType().getSimpleName())
                    .append(' ').append(att.getName()).append(';');
                Annotation[] anns  = att.getJavaMember() instanceof Field ?
                    ((Field) att.getJavaMember()).getDeclaredAnnotations() :
                    ((Method) att.getJavaMember()).getDeclaredAnnotations();
                for (Annotation ann : anns) {
                    if (ann instanceof Comment) {
                        sb.append(" // ").append(((Comment) ann).value());
                    }
                }
            }
            sb.append("\n}\n");
        }
        return sb.toString();
    }

}
