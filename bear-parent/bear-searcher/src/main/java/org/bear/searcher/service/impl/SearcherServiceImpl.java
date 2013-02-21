package org.bear.searcher.service.impl;

import org.apache.avro.AvroRemoteException;
import org.bear.api.Query;
import org.bear.api.SearcherService;
import org.bear.api.ShortUrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-19
 */
public class SearcherServiceImpl implements SearcherService{
	private static final Logger LOG = LoggerFactory.getLogger(SearcherServiceImpl.class);
	
	private ShortUrlService shortUrlService;
	
	public void setShortUrlService(ShortUrlService shortUrlService) {
		this.shortUrlService = shortUrlService;
	}

	@Override
	public Void searcher(Query query) throws AvroRemoteException {
		LOG.debug("searcher server, print {}", shortUrlService.generate("sf", 10));
		return null;
	}

}
