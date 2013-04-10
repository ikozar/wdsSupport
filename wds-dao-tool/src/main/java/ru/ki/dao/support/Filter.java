
package ru.ki.dao.support;

import java.util.ArrayList;
import java.util.List;

public class Filter {
    protected List<FilterElement> filterElement;

    public List<FilterElement> getFilterElement() {
        if (filterElement == null) {
            filterElement = new ArrayList<FilterElement>();
        }
        return this.filterElement;
    }

}
