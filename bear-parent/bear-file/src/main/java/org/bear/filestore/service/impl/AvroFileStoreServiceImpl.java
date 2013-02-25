package org.bear.filestore.service.impl;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

import org.apache.avro.AvroRemoteException;
import org.bear.api.fs.Action;
import org.bear.api.fs.File;
import org.bear.api.fs.FilePage;
import org.bear.api.fs.FileStoreService;
import org.bear.api.type.GlobalException;
import org.bear.filestore.service.FileStoreManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-21
 */
@Service
public class AvroFileStoreServiceImpl implements FileStoreService {
	private static final Logger LOG = LoggerFactory.getLogger(AvroFileStoreServiceImpl.class);
	
	@Autowired
	private FileStoreManager fileStoreManager;
	
	@Override
	public File saveFile(File file) throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void removeFiles(List<Long> ids) throws AvroRemoteException,
			GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void removeFilesByOwner(String bizKey, String owner)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void writeFile(long id, ByteBuffer bytes, int position)
			throws AvroRemoteException, GlobalException {
		fileStoreManager.writeFile(id, bytes.array(), position);
		return null;
	}

	@Override
	public ByteBuffer readFile(long id, int position, int count)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void transformImage(long id, List<Action> actions)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getToken(List<Long> ids, boolean writeable, int ttl)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getBizToken(String bizKey, List<String> owners,
			boolean writeable, int ttl) throws AvroRemoteException,
			GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void bindSession(String sessionId, List<Long> ids)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void unbindSession(String sessionId, List<Long> ids)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getFile(long id) throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, File> getFileMap(List<Long> ids)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getSingleFile(String bizKey, String owner)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<File> getFiles(String bizKey, String owner)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, List<File>> batchGetFiles(String bizKey,
			List<String> owners) throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Map<String, List<File>>> batchGetFilesEx(
			Map<String, List<String>> ownersMap) throws AvroRemoteException,
			GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEncryptedId(long id, int ttl) throws AvroRemoteException,
			GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getEncryptedIds(List<Long> ids, int ttl)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getFileByEncryptedId(String encryptedId)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, File> getFileMapByEncryptedId(List<String> encryptedIds)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FilePage getUserFiles(String bizKey, String owner, long userId,
			int start, int size) throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

}
