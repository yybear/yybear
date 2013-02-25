package org.bear.filestore.fs;

import org.apache.commons.lang.StringUtils;
import org.bear.filestore.ex.FileStoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-22
 */
public abstract class AbstractStorageStrategy implements StorageStrategy {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractStorageStrategy.class);
    private static final char SEPARATOR = ',';
    protected List<Storage> storages = new ArrayList<Storage>();
    protected Map<String, Storage> storageMap = new HashMap<String, Storage>();

    public void setStorages(List<Storage> storages) {
        this.storages.addAll(storages);
    }

    public synchronized void init() {
        Iterator<Storage> it = storages.iterator();
        while (it.hasNext()) {
            Storage st = it.next();
            st.init();
            if (!st.isAvailable()) {
                LOG.info("remove not available storage [{}]", st);
                it.remove();
            } else {
                storageMap.put(st.getId(), st);
            }
        }
        if (storages.isEmpty()) {
            throw new FileStoreException("No storage available");
        }
        Collections.sort(storages);
    }

    protected Storage getAvailableStorage(String idString) {
        String[] ss = StringUtils.split(idString, SEPARATOR);
        for (String s : ss) {
            Storage st = storageMap.get(s);
            if (st != null) {
                return st;
            }
        }
        throw new FileStoreException("Storage for [" + idString + "] not found");
    }
}
