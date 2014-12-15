package ru.ki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ki.model.query.SearchParameters;
import ru.ki.service.CommonService;

/**
 * @author ikozar
 * date    23.06.13
 */

@Controller
@RequestMapping(value = "/")
public class CommonController extends AbstractController {

    @Autowired
    private CommonService commonService;

    @RequestMapping(value = "/{pkg}/{clazz}/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ModelAndView getById(@PathVariable String pkg,
        @PathVariable String clazz, @PathVariable Long id) throws Exception {
        return createModelAndView(commonService.getEntity(clazz, pkg, id));
    }

    @RequestMapping(value = "/{pkg}/{clazz}/find", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView findCollection(@PathVariable String pkg,
        @PathVariable String clazz,
        @RequestParam(required = false) java.util.Map<String, Object> filterMap
    ) throws Exception {
        SearchParameters sp = filterMap.containsKey(PNAME)
                ? customJacksonObjectMapper.readValue((String) filterMap.get(PNAME), SearchParameters.class)
                : SearchParameters.GET_ALL;
        return findAndReturn(clazz, pkg, sp);
    }

    @RequestMapping(value = "/{pkg}/{clazz}/find1", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView findCollectionFromBody(@PathVariable String pkg,
        @PathVariable String clazz, @RequestBody final SearchParameters sp) throws Exception {
        return findAndReturn(clazz, pkg, sp);
    }

    @RequestMapping(value = "/{pkg}/{clazz}/{id}", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView replace(@PathVariable String pkg,
        @PathVariable String clazz, @PathVariable Long id,
        @RequestParam(required = true) String data
//        @RequestParam(required = false) java.util.Map<String, Object> params
    ) throws Exception {
//        String data = (String) params.get(PNAME);
        return createModelAndView(commonService.updateEntity(clazz, pkg, id, data));
    }

    /**
     * Сервиз изменения сущности
     */
    @RequestMapping(value = "/{pkg}/{clazz}/{id}", method = RequestMethod.PUT)
    public @ResponseBody
    ModelAndView replacePUT(@PathVariable String pkg,
        @PathVariable String clazz, @PathVariable Long id,
//        @RequestParam(required = true) String data
        @RequestParam(required = false) java.util.Map<String, Object> params
    ) throws Exception {
        String data = (String) params.get(PNAME);
        return createModelAndView(commonService.updateEntity(clazz, pkg, id, data));
    }

    /**
     * Сервид добавления скщности
     */
    @RequestMapping(value = "/{pkg}/{clazz}", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView add(@PathVariable String pkg,
        @PathVariable String clazz, @RequestParam(required = true) String data) throws Exception {
        return createModelAndView(commonService.addEntity(clazz, pkg, data));
    }

    @RequestMapping(value = "/{pkg}/{clazz}/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String pkg,
        @PathVariable String clazz, @PathVariable Long id) throws Exception {
        commonService.deleteEntity(clazz, pkg, id);
    }

    @RequestMapping(value = "/types/{clazz}", method = RequestMethod.GET)
    public ModelAndView getEnum(@PathVariable String clazz) throws Exception {
        return createModelAndView(commonService.getEnum(clazz));
    }

    protected ModelAndView findAndReturn(String clazz, String pkg, SearchParameters sp) {
        return createModelAndView(commonService.findAndReturn(clazz, pkg, sp));
    }

}
