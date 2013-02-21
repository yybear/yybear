package org.bear.searcher.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:ganqing@any123.com">gan qing</a>
 * @version V1.0, 2013-2-4
 */
@Controller
public class IndexController {
	
	@RequestMapping(value = "index.do")
    public String index(@RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "aid", required = false) String aid,
            @RequestParam(value = "cid", required = false) String cid,
            @RequestParam(value = "date", required = false) String date,
            @RequestParam(value = "mt", required = false) Integer mimetype,
            @RequestParam(value = "index", required = false) Integer index,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", required = false) String sort,
            Model model) {
		return "index";
	}
	@RequestMapping(value = "solr.do")
	public String console() {
		return "solr";
	}
}
