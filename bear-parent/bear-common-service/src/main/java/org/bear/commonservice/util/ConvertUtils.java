package org.bear.commonservice.util;

import java.util.List;

import org.bear.api.app.App;
import org.bear.api.app.Biz;
import org.bear.api.app.BizConfig;
import org.bear.global.type.Status;

import com.google.common.collect.Lists;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-3-27
 */
public class ConvertUtils {
	public static org.bear.commonservice.model.App toApp(org.bear.commonservice.model.App app, App ta) {
        if (app == null) {
            app = new org.bear.commonservice.model.App();
        }
        app.setKey(ta.getKey());
        app.setName(ta.getName());
        if (ta.getStatus() != null) {
            app.setStatus(Status.values()[ta.getStatus().ordinal()]);
        }
        return app;
    }

    public static App toAvroApp(org.bear.commonservice.model.App app) {
        App ta = new App();
        ta.setId(app.getId());
        ta.setKey(app.getKey());
        ta.setName(app.getName());
        ta.setStatus(org.bear.api.type.Status.values()[app.getStatus().ordinal()]);
        return ta;
    }

    public static org.bear.commonservice.model.Biz toBiz(org.bear.commonservice.model.Biz biz, Biz tb) {
        if (biz == null) {
            biz = new org.bear.commonservice.model.Biz();
        }
        biz.setKey(tb.getKey());
        biz.setName(tb.getName());
        if (tb.getStatus() != null) {
            biz.setStatus(Status.values()[tb.getStatus().ordinal()]);
        }
        return biz;
    }

    public static Biz toAvroBiz(org.bear.commonservice.model.Biz biz) {
        Biz tb = new Biz();
        tb.setId(biz.getId());
        if (biz.getApp() != null) {
            tb.setAppId(biz.getApp().getId());
        }
        tb.setKey(biz.getKey());
        tb.setName(biz.getName());
        tb.setStatus(org.bear.api.type.Status.values()[biz.getStatus().ordinal()]);
        return tb;
    }


    public static org.bear.framework.config.BizConfig toBizConfig(BizConfig tbc) {
        org.bear.framework.config.BizConfig bc = new org.bear.framework.config.BizConfig();
        bc.setBizId(tbc.getBizId());
        bc.setBizKey(tbc.getBizKey());
        bc.setBizName(tbc.getBizName());
        bc.setConfigKey(tbc.getConfigKey());
        bc.setConfigValue(tbc.getConfigValue());
        return bc;
    }

    public static BizConfig toAvroBizConfig(org.bear.framework.config.BizConfig bc) {
        BizConfig tbc = new BizConfig();
        tbc.setBizId(bc.getBizId());
        tbc.setBizKey(bc.getBizKey());
        tbc.setBizName(bc.getBizName());
        tbc.setConfigKey(bc.getConfigKey());
        tbc.setConfigValue(bc.getConfigValue());
        return tbc;
    }

    public static List<App> toAppsList(List<org.bear.commonservice.model.App> apps) {
        List<App> list = Lists.newArrayListWithCapacity(apps.size());
        for (org.bear.commonservice.model.App biz : apps) {
            list.add(toAvroApp(biz));
        }
        return list;
    }

    public static List<Biz> toBizsList(List<org.bear.commonservice.model.Biz> bizs) {
        List<Biz> list = Lists.newArrayListWithCapacity(bizs.size());
        for (org.bear.commonservice.model.Biz biz : bizs) {
            list.add(toAvroBiz(biz));
        }
        return list;
    }

    public static List<BizConfig> toBizConfigsList(List<org.bear.framework.config.BizConfig> bizConfigs) {
        List<BizConfig> list = Lists.newArrayListWithCapacity(bizConfigs.size());
        for (org.bear.framework.config.BizConfig biz : bizConfigs) {
            list.add(toAvroBizConfig(biz));
        }
        return list;
    }
}
