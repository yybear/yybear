package org.bear.commonservice.shorturl;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.avro.AvroRemoteException;
import org.bear.api.shorturl.ShortUrlService;
import org.bear.api.type.GlobalException;
import org.bear.framework.cache.Cache;
import org.bear.framework.cache.CacheManager;
import org.bear.framework.ex.ErrorCode;
import org.bear.framework.util.Codecs;
import org.springframework.beans.factory.InitializingBean;

public class ShortUrlServiceImpl implements ShortUrlService, InitializingBean{
	private String domainUrl;
	private CacheManager cacheManager;
	private Cache shortUrlCache;
	
	private String cacheName = "SURL";
	
	public void setDomainUrl(String domainUrl) {
        this.domainUrl = domainUrl;
    }

    public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}


	public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
    
	@Override
	public void afterPropertiesSet() throws Exception {
		shortUrlCache = cacheManager.getCache(cacheName+":A");
	}

	@Override
	public String generate(String url, int ttl) throws AvroRemoteException {
		try {
            new URL(url);
        } catch (MalformedURLException e) {
            throw new GlobalException(ErrorCode.ILLEGAL_PARAM, e);
        }
        String code = Codecs.hash(url, 6);
        if (ttl <= 0) {
        	shortUrlCache.put(code, url);
        } else {
        	shortUrlCache.put(code, url, ttl, -1);
        }
        return domainUrl + "/" + code;
	}

	@Override
	public String getUrl(String code) throws AvroRemoteException {
		return shortUrlCache.get(code);
	}

	@Override
	public int getAccessCount(String code) throws AvroRemoteException {
		throw new UnsupportedOperationException();
	}
}
