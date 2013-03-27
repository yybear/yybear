package org.bear.console.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-3-26
 */
@Controller
public class PageController {
    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String search() {
        return "search/index";
    }
    
    @RequestMapping(value = "event", method = RequestMethod.GET)
    public String event() {
        return "event/index";
    }
    
    @RequestMapping(value = "cache", method = RequestMethod.GET)
    public String cache() {
        return "cache/index";
    }
    
    @RequestMapping(value = "spam", method = RequestMethod.GET)
    public String spam() {
        return "spam/index";
    }
}
