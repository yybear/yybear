package org.bear.filestore.service.impl;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bear.filestore.dao.FileDao;
import org.bear.filestore.ex.FileIOException;
import org.bear.filestore.model.File;
import org.bear.filestore.model.ImageAction;
import org.bear.filestore.service.FileStoreManager;
import org.bear.filestore.fs.StorageStrategy;
import org.bear.filestore.fs.VirtualFile;
import org.bear.filestore.image.ImageTransformFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-22
 */
@Service
public class FileStoreManageImpl implements FileStoreManager {

	
	private static final Logger LOG = LoggerFactory.getLogger(FileStoreManageImpl.class);
	@Autowired
	private FileDao fileDao;
    /*private EncryptService encryptService;
    private TokenService.Iface tokenService;*/
	@Autowired
    private StorageStrategy storageStrategy;
	@Autowired
    private ImageTransformFactory imageTransformFactory;
	
	@Override
	public File saveFile(File file) {
		return fileDao.save(file);
	}

	@Override
	public void removeFile(Long... ids) {
	}

	@Override
	public void removeFiles(String bizKey, String owner) {
		
	}

	@Override
	public VirtualFile getVirtualFile(File file) {
		return storageStrategy.select(file).getVirtualFile(file.toKey());
	}

	@Override
	public int saveFileInputStream(File file, InputStream inputStream)
			throws FileIOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeFile(long id, byte[] bytes, int position)
			throws FileIOException {
		File file = getFile(id);
        VirtualFile vf = getVirtualFile(file);
        vf.write(bytes, position, bytes.length);
        file.setSize(vf.getSize());
        saveFile(file);
		
	}

	@Override
	public byte[] readFile(long id, int position, int count)
			throws FileIOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void transformImage(long id, List<ImageAction> actions) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getEncryptedId(Long id, int ttl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Long, String> getEncryptedIdMap(List<Long> ids, int ttl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getFile(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Long, File> getFileMap(Set<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getFileByEncryptedId(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Long, File> getFileMapByEncryptedId(Set<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getSingleFile(String bizKey, String owner) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<File> getFiles(String bizKey, String owner) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, List<File>> batchGetFiles(String bizKey,
			Set<String> owners) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countUserFiles(String bizKey, String owner, long userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<File> getUserFiles(String bizKey, String owner, long userId,
			int start, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasPermission(String token, Long id, boolean writeable) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasPermission(String token, String bizKey, String owner,
			boolean writeable) {
		// TODO Auto-generated method stub
		return false;
	}

}
