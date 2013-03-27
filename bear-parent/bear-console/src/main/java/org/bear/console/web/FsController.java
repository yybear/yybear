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
@RequestMapping(value = "fs")
public class FsController {
    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        return "fs/index";
    }

    @RequestMapping(value = "video", method = RequestMethod.GET)
    public String video() {
        return "fs/video";
    }

    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String test(Model model) {
        return "fs/test";
    }
}
