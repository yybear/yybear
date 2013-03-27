package org.bear.commonservice.app;

import java.util.List;

import org.apache.avro.AvroRemoteException;
import org.bear.api.app.App;
import org.bear.api.app.Biz;
import org.bear.api.app.BizConfig;
import org.bear.api.type.GlobalException;
import org.bear.commonservice.dao.AppDao;
import org.bear.commonservice.dao.BizDao;
import org.bear.commonservice.util.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;



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
		return ConvertUtils.toAvroApp(appDao.save(ConvertUtils.toApp(null, app)));
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
		
		return null;
	}

	@Override
	public BizConfig getBizConfig(String bizKey, String configKey)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BizConfig getBizConfigById(int bizId, String configKey)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void saveBizConfig(BizConfig bizConfig) throws AvroRemoteException,
			GlobalException {
		return null;
	}

	@Override
	public List<BizConfig> getBizConfigs(String configKey)
			throws AvroRemoteException, GlobalException {
		return null;
	}

}
