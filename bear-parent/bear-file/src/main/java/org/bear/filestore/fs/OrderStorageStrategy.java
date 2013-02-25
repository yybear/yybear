package org.bear.filestore.fs;

import org.bear.filestore.ex.FileStoreException;
import org.bear.filestore.model.File;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-22
 */
public class OrderStorageStrategy extends AbstractStorageStrategy {
    @Override
    public Storage select(File file) {
        String storageIds = file.getStorage();
        if (storageIds == null) {
            for (Storage storage : storages) {
                if (storage.isAvailable()) {
                    return storage;
                }
            }
            throw new FileStoreException("No storage available");
        } else {
            return getAvailableStorage(storageIds);
        }
    }
}
