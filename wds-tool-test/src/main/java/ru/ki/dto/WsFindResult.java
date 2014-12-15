package ru.ki.dto;

import ru.ki.model.FindResult;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;

/**
 * @author ikozar
 * date    10.04.13
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlSeeAlso({EmployeeVO.class, EmployeeVO1.class})
public class WsFindResult {
    private FindResult fr;

    public WsFindResult() {
    }

    public WsFindResult(FindResult fr) {
        this.fr = fr;
    }

    @XmlElement
    public Long getCount() {
        return fr.getCount();
    }

    @XmlElement
    public List getResultList() {
        return fr.getResultList();
    }
}
