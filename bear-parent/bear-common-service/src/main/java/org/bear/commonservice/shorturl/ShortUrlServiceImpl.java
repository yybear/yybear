package org.bear.commonservice.shorturl;

import org.apache.avro.AvroRemoteException;
import org.bear.api.ShortUrlService;

public class ShortUrlServiceImpl implements ShortUrlService{

	@Override
	public String generate(String url, int ttl) throws AvroRemoteException {
		return "xxx";
	}

	@Override
	public String getUrl(String code) throws AvroRemoteException {
		return "any123.com";
	}

	@Override
	public int getAccessCount(String code) throws AvroRemoteException {
		return 10;
	}

}
