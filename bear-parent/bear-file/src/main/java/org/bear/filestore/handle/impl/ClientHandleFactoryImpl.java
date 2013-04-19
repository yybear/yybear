package org.bear.filestore.handle.impl;

import com.google.common.collect.Maps;
import org.bear.filestore.handle.ClientHandle;
import org.bear.filestore.handle.ClientHandleFactory;

import java.util.Map;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-4-8
 */
public class ClientHandleFactoryImpl implements ClientHandleFactory {

    private Map<String, ClientHandle> clientHandles = Maps.newHashMap();

    public void setClientHandles(Map<String, ClientHandle> clientHandles) {
        this.clientHandles.putAll(clientHandles);
    }

    @Override
    public ClientHandle getClientHandle(String client) {
        return clientHandles.get(client);
    }
}
