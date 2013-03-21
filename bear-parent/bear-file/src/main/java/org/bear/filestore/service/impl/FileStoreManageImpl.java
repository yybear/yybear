package org.bear.filestore.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.bear.filestore.dao.FileDao;
import org.bear.filestore.ex.FileIOException;
import org.bear.filestore.ex.FileStoreException;
import org.bear.filestore.model.File;
import org.bear.filestore.model.ImageAction;
import org.bear.filestore.service.FileStoreManager;
import org.bear.filestore.fs.Storage;
import org.bear.filestore.fs.VirtualFile;
import org.bear.filestore.image.ImageTransform;
import org.bear.filestore.image.ImageTransformFactory;
import org.bear.framework.encrypt.EncryptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-22
 */
@Transactional
public class FileStoreManageImpl implements FileStoreManager {

	
	private static final Logger LOG = LoggerFactory.getLogger(FileStoreManageImpl.class);
	@Autowired
	private FileDao fileDao;
	@Autowired
    private EncryptService encryptService;
    /*private TokenService.Iface tokenService;*/
	@Autowired
    private Storage storage;
	@Autowired
    private ImageTransformFactory imageTransformFactory;
	
	@Override
	public File saveFile(File file) {
		file.setUpdateAt(new Date());
		if(file.getOwner()==null){
            file.setOwner(StringUtils.EMPTY);
        }
		return fileDao.save(file);
	}

	@Override
	public void removeFile(Long... ids) {
		if(ids == null)
			return;
		for(Long id : ids) {
			fileDao.delete(id);	
		}
	}

	@Override
	public void removeFiles(String bizKey, String owner) {
	}

	@Override
	public VirtualFile getVirtualFile(File file) {
		return storage.getVirtualFile(file.toKey());
	}

	@Override
	public int saveFileInputStream(File file, InputStream inputStream)
			throws FileIOException {
		if(file.getId() == null)
			saveFile(file);
		
		VirtualFile vFile = getVirtualFile(file);
		OutputStream out = vFile.getOutputStream();
		
		int count = 0;
        try {
            count = IOUtils.copy(inputStream, out);
            file.setSize(count);
            saveFile(file);
        } catch (IOException e) {
            LOG.error("save file inputStream [{}] error", file, e);
        }
        return count;
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
		File file = getFile(id);
		VirtualFile vf = getVirtualFile(file);
		byte[] buf = new byte[count];
		vf.read(buf, position, count);
		return buf;
	}

	@Override
	public void transformImage(long id, List<ImageAction> actions) {
		File img = getFile(id);
        VirtualFile vf = getVirtualFile(img);
        if (img.isImage() && CollectionUtils.isNotEmpty(actions)) {
            try {
                ImageTransform it = imageTransformFactory.getImageTransform();
                it.load(vf.getInputStream());
                for (ImageAction action : actions) {
                    Map<String, Object> params = action.getParam();
                    switch (action.getImageOperate()) {
                        case RESIZE:
                            it.resizeWithMax(getInt(params, "width"), getInt(params, "height"));
                            break;
                        case CROP:
                            it.crop(getInt(params, "left"), getInt(params, "top"), getInt(params, "width"), getInt(params, "height"));
                            break;
                        case ROTATE:
                            it.rotateWithMax(getDouble(params, "degree"), getInt(params, "width"), getInt(params, "height"));
                            break;
                    }
                }
                if (it.isModified()) {
                    it.save(vf.getOutputStream(), img.getExt());
                    img.setSize(vf.getSize());
                    saveFile(img);
                }
            } catch (IOException e) {
                throw new FileIOException("transformImage for file [" + id + "] error", e);
            }
        }
		
	}

	
	@Override
	public String getEncryptedId(Long id, int ttl) {
		try {
			return encryptService.encrypt(String.valueOf(id));
		} catch (GeneralSecurityException e) {
			throw new FileStoreException(e);
		}
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
	
	private Integer getInt(Map<String, Object> params, String key) {
        Object v = params.get(key);
        if (v != null) {
            return v instanceof Number ? ((Number) v).intValue() : Integer.valueOf(v.toString());
        }
        return null;
    }

    private Double getDouble(Map<String, Object> params, String key) {
        Object v = params.get(key);
        if (v != null) {
            return v instanceof Number ? ((Number) v).doubleValue() : Double.valueOf(v.toString());
        }
        return null;
    }

}
