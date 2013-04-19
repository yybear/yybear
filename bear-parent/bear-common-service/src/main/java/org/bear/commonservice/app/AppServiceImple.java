package org.bear.commonservice.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.avro.AvroRemoteException;
import org.bear.api.app.App;
import org.bear.api.app.Biz;
import org.bear.api.app.BizConfig;
import org.bear.api.type.GlobalException;
import org.bear.commonservice.dao.AppDao;
import org.bear.commonservice.dao.BizDao;
import org.bear.commonservice.util.ConvertUtils;
import org.bear.global.type.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.RequestContext;

import com.google.common.collect.Lists;



/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-3-26
 */
public class AppServiceImple implements org.bear.api.app.AppService {
	@Autowired
	private AppDao appDao;
	@Autowired
	private BizDao bizDao;
	@Override
	public App saveApp(App app) throws AvroRemoteException, GlobalException {
		org.bear.commonservice.model.App localApp = ConvertUtils.toApp(null, app);
		return ConvertUtils.toAvroApp(appDao.save(localApp));
	}

	@Override
	public Void removeApps(List<Integer> ids) throws AvroRemoteException,
			GlobalException {
		for(Integer id : ids)
			appDao.delete(id);
		return null;
	}

	@Override
	public App getApp(int id) throws AvroRemoteException, GlobalException {
		return ConvertUtils.toAvroApp(appDao.findOne(id));
	}

	@Override
	public List<App> getApps() throws AvroRemoteException, GlobalException {
		return ConvertUtils.toAppsList(appDao.findAll());
	}

	@Override
	public Biz saveBiz(Biz biz) throws AvroRemoteException, GlobalException {
		return ConvertUtils.toAvroBiz(bizDao.save(ConvertUtils.toBiz(null, biz)));
	}

	@Override
	public Void removeBizs(List<Integer> ids) throws AvroRemoteException,
			GlobalException {
		for(Integer id : ids)
			bizDao.delete(id);
		return null;
	}

	@Override
	public Biz getBiz(int id) throws AvroRemoteException, GlobalException {
		return ConvertUtils.toAvroBiz(bizDao.findOne(id));
	}

	@Override
	public List<Biz> getBizs() throws AvroRemoteException, GlobalException {
		return ConvertUtils.toBizsList(bizDao.findAll());
	}

	@Override
	public List<Biz> getAppBizs(int appId) throws AvroRemoteException,
			GlobalException {
		org.bear.commonservice.model.App localApp = new org.bear.commonservice.model.App();
		localApp.setId(appId);
		return ConvertUtils.toBizsList(bizDao.findByApp(localApp));
	}

	@Override
	public BizConfig getBizConfig(String bizKey, String configKey)
			throws AvroRemoteException, GlobalException {
		org.bear.commonservice.model.Biz biz = bizDao.findByKey(bizKey);
		String configValue = biz.getAttributes().get(configKey);
		
		return toBizConfig(ConvertUtils.toAvroBiz(biz), configKey, configValue);
	}

	@Override
	public BizConfig getBizConfigById(int bizId, String configKey)
			throws AvroRemoteException, GlobalException {
		org.bear.commonservice.model.Biz biz = bizDao.findOne(bizId);
		String configValue = biz.getAttributes().get(configKey);
		
		return toBizConfig(ConvertUtils.toAvroBiz(biz), configKey, configValue);
	}

	@Override
	public Void saveBizConfig(BizConfig bizConfig) throws AvroRemoteException,
			GlobalException {
		org.bear.commonservice.model.Biz biz = bizDao.findByKey(bizConfig.getBizKey());
		if ("__del__".equals(bizConfig.getConfigValue())) {
            biz.getAttributes().remove(bizConfig.getConfigKey());
        } else {
            biz.getAttributes().put(bizConfig.getConfigKey(), bizConfig.getConfigValue());
        }
		bizDao.save(biz);
		return null;
	}

	@Override
	public List<BizConfig> getBizConfigs(String configKey)
			throws AvroRemoteException, GlobalException {
		
		List<BizConfig> configs = Lists.newArrayList();
        for (org.bear.commonservice.model.Biz biz :  bizDao.findAll()) {
            if (biz.getAttributes().containsKey(configKey)) {
                configs.add(toBizConfig(ConvertUtils.toAvroBiz(biz), configKey, biz.getAttributes().get(configKey)));
            }
        }
        return configs;
	}

	private BizConfig toBizConfig(Biz biz, String configKey, String configValue) {
        BizConfig bizConfig = new BizConfig();
        bizConfig.setBizId(biz.getId());
        bizConfig.setBizKey(biz.getKey());
        bizConfig.setBizName(biz.getName());
        bizConfig.setConfigKey(configKey);
        bizConfig.setConfigValue(configValue);
        return bizConfig;
    }
	
	private org.bear.commonservice.model.Biz toBiz(BizConfig config) {
		org.bear.commonservice.model.Biz biz = new org.bear.commonservice.model.Biz();
		biz.setKey(config.getBizKey());
		biz.setName(config.getBizName());
		biz.setStatus(Status.ENABLED);
		Map<String,String> attributes = new HashMap<String, String>();
		attributes.put(config.getConfigKey(), config.getConfigValue());
		biz.setAttributes(attributes);
        return biz;
    }
}
