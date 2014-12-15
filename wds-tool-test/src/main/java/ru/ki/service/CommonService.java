package ru.ki.service;

import org.springframework.stereotype.Service;
import ru.ki.model.query.SearchParameters;
import ru.ki.service.exception.ExceptionCode;
import ru.ki.service.exception.ExceptionWithCode;
import ru.ki.service.model.EnumItem;
import ru.ki.service.model.IEnumTitle;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ikozar
 * date    23.06.13
 */
@Service
public class CommonService extends AbstractService {

    public Object findAndReturn(String clazz, String pkg, SearchParameters sp) {
        return getDao(clazz, pkg).find(sp);
    }

    public <E extends Enum<E>> List<EnumItem> getEnum(String clazz) {
        try {
            Class<E> enumClass = (Class<E>) getClass().forName("ru.ki.model.enums." + clazz);
            if (!IEnumTitle.class.isAssignableFrom(enumClass)) {
                throw new ExceptionWithCode(ExceptionCode.BAD_URL, "Enum должен наслендовать IEnumTitle ", clazz);
            }
            List<EnumItem> l = new ArrayList<EnumItem>(enumClass.getEnumConstants().length);
            for (E e : enumClass.getEnumConstants()) {
                l.add(new EnumItem(e.name(), ((IEnumTitle) e).getTitle()));
            }
            return l;
        } catch (ClassNotFoundException e) {
            throw new ExceptionWithCode(ExceptionCode.BAD_URL, "Не найден enum ", clazz);
        }
    }
}
