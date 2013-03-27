package org.bear.console.web;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-3-26
 */
public abstract class BaseController {
    protected void success(RedirectAttributes ra) {
        ra.addFlashAttribute("ret", true);
    }

    protected void failed(RedirectAttributes ra, String msg) {
        ra.addFlashAttribute("msg", msg);
        ra.addFlashAttribute("ret", false);
    }
}
