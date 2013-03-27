package org.bear.console.web;

import org.bear.api.app.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-3-26
 */
@Controller
@RequestMapping(value = "message")
public class MessageController{
	
	
    /*@Autowired
    private MessageService messageService;

    @Autowired
    private AppService appService;
    
    @InitBinder
    protected void initBinder(ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }

    @RequestMapping(value = "history", method = RequestMethod.GET)
    public String index(Model model,
                        @RequestParam(value = "bizKey", required = false) String bizKey,
                        @RequestParam(value = "type", required = false) MessageType type,
                        @RequestParam(value = "succeed", required = false) Boolean succeed,
                        @RequestParam(value = "startDate", required = false) Date startDate,
                        @RequestParam(value = "endDate", required = false) Date endDate,
                        @RequestParam(value = "index", defaultValue = "1") int index,
                        @RequestParam(value = "size", defaultValue = "20") int size) throws Exception {
        MessagePage mp = messageService.getMessageHistories(bizKey, type, ThriftUtils.toBooleanValue(succeed), ThriftUtils.toDateValue(startDate), ThriftUtils.toDateValue(endDate), Page.toStart(index, size), size);
        Page<Message> page = new Page<Message>(index, size);
        page.setItems(mp.getItems());
        page.setTotal(mp.getTotal());
        model.addAttribute("page", page);
        model.addAttribute("cfgs", appService.getBizConfigs("message"));
        model.addAttribute("size", size);
        return "message/history";
    }
    
    
    @RequestMapping(value = "gateway", method = RequestMethod.GET)
    public String gateway() {
        return "message/gateway";
    }*/
    
}
