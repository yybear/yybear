package org.bear.console.web;

import com.alibaba.fastjson.JSON;

import org.apache.commons.lang.StringUtils;
import org.bear.api.app.App;
import org.bear.api.app.AppService;
import org.bear.api.app.Biz;
import org.bear.api.app.BizConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-3-26
 */
@Controller
@RequestMapping(value = "biz")
public class BizController extends BaseController {

    @Autowired
    private AppService appService;

    @ModelAttribute("biz")
    public Biz getFile(@RequestParam(value = "id", required = false) Integer id) throws Exception {
        return id == null ? new Biz() : appService.getBiz(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model, @RequestParam(value = "appId", required = false) Integer appId) throws Exception {
        model.addAttribute("bizs", appId == null ? appService.getBizs() : appService.getAppBizs(appId));
        model.addAttribute("apps", getAppsMap());
        return "biz/index";
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public String delete(@RequestParam(value = "id") int id) throws Exception {
        appService.removeBizs(Collections.singletonList(id));
        return "redirect:/biz";
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(Model model) throws Exception {
        model.addAttribute("apps", getAppsMap());
        return "biz/edit";
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String save(@ModelAttribute("biz") Biz biz, RedirectAttributes ra) throws Exception {
        try {
            appService.saveBiz(biz);
        } catch (Exception e) {
            failed(ra, e.getMessage());
        }
        success(ra);
        return "redirect:/biz";
    }

    @RequestMapping(value = "cfgs", method = RequestMethod.GET)
    public String cfgs(Model model, @RequestParam(value = "configKey") String configKey) throws Exception {
        model.addAttribute("cfgs", appService.getBizConfigs(configKey));
        return "biz/cfgs";
    }

    @RequestMapping(value = "cfg", method = RequestMethod.GET)
    public String cfg(Model model,
                      @RequestParam(value = "configKey") String configKey,
                      @RequestParam(value = "bizKey", required = false) String bizKey) throws Exception {
    	BizConfig bizConfig = bizKey != null ? appService.getBizConfig(bizKey, configKey) : new BizConfig();
        model.addAttribute("config", bizConfig);
        try{
        	model.addAttribute("configJson",JSON.parse(bizConfig.getConfigValue()));
        } catch(Exception e){
        	
        }
        model.addAttribute("bizs", getBizsMap());
        return "biz/cfg";
    }

    @RequestMapping(value = "cfg", method = RequestMethod.POST)
    public String cfg(BizConfig config, RedirectAttributes ra) throws Exception {
        String value = config.getConfigValue();
        try {
        	JSON.parse(value);
            appService.saveBizConfig(config);
            success(ra);
        } catch (Exception e) {
            failed(ra, e.getMessage());
        }
        return "redirect:/biz/cfg?configKey=" + config.getConfigKey() + "&bizKey=" + config.getBizKey();
    }
    
    @RequestMapping(value = "cfg",params="t=true", method = RequestMethod.POST)
    public String cfg(String subject,String body,String vars,BizConfig config, RedirectAttributes ra) throws Exception {
    	Map<String, Object> jsonMap = new HashMap<String,Object>();
    	Map<String,String> tplsMap = new HashMap<String, String>();
    	jsonMap.put("tpls", tplsMap);
    	tplsMap.put("subject", StringUtils.trimToEmpty(subject));
    	tplsMap.put("body", StringUtils.trimToEmpty(body));
    	jsonMap.put("vars", StringUtils.trimToEmpty(vars));
    	String value = JSON.toJSONString(jsonMap);
    	config.setConfigValue(value);
    	return cfg(config,ra);
    }

    @RequestMapping(value = "cfg/delete", method = RequestMethod.GET)
    public String deleteCfg(RedirectAttributes ra,
                            @RequestParam(value = "configKey") String configKey,
                            @RequestParam(value = "bizKey") String bizKey) throws Exception {
        BizConfig bc = new BizConfig();
        bc.setBizKey(bizKey);
        bc.setConfigKey(configKey);
        bc.setConfigValue("__del__");
        appService.saveBizConfig(bc);
        success(ra);
        return "redirect:/biz/cfgs?configKey=" + configKey;
    }

    private Map<String, String> getBizsMap() throws Exception {
        List<Biz> bizs = appService.getBizs();
        Map<String, String> map = new LinkedHashMap<String, String>(bizs.size());
        for (Biz biz : bizs) {
            map.put(biz.getKey(), biz.getName());
        }
        return map;
    }

    private Map<Integer, String> getAppsMap() throws Exception {
        List<App> apps = appService.getApps();
        Map<Integer, String> map = new LinkedHashMap<Integer, String>(apps.size());
        for (App app : apps) {
            map.put(app.getId(), app.getName());
        }
        return map;
    }
}
