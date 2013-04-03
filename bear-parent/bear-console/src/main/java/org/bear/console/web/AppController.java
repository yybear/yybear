package org.bear.console.web;

import org.apache.http.HttpRequest;
import org.bear.api.app.App;
import org.bear.api.app.AppService;
import org.bear.api.type.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;

import javax.servlet.http.HttpServlet;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-3-26
 */
@Controller
@RequestMapping(value = "app")
public class AppController extends BaseController{

    @Autowired
    private AppService appService;

    @ModelAttribute("app")
    public App getFile(@RequestParam(value = "id", required = false) Integer id) throws Exception {
    	
    	return id == null ? new App() : appService.getApp(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) throws Exception {
        model.addAttribute("apps", appService.getApps());
        return "app/index";
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public String delete(@RequestParam(value = "id") int id) throws Exception {
        appService.removeApps(Collections.singletonList(id));
        return "redirect:/app";
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit() throws Exception {
        return "app/edit";
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String save(@ModelAttribute("app") App app, RedirectAttributes ra) throws Exception {
        try {
        	if(app.getStatus() == null)
        		app.setStatus(Status.ENABLED);
            appService.saveApp(app);
        } catch (Exception e) {
            failed(ra, e.getMessage());
        }
        success(ra);
        return "redirect:/app";
    }
}
