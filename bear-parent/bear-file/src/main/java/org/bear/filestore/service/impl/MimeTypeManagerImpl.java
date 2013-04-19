package org.bear.filestore.service.impl;

import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.bear.filestore.ex.FileStoreException;
import org.bear.filestore.service.MimeTypeManager;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-21
 */
public class MimeTypeManagerImpl implements MimeTypeManager {

    public static final String DEFAULT_CONFIG = "mimetype-map.xml";

    private Map<String, String> extensionsByMimetype;
    private Map<String, String> mimeTypesByExtension;
    private Map<String, String> iconsByExtension;

    public MimeTypeManagerImpl() {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(getClass().getResourceAsStream(DEFAULT_CONFIG));
            List list = DomUtils.getChildElementsByTagName(doc.getDocumentElement(), "mime-mapping");
            extensionsByMimetype = new HashMap<String, String>(list.size());
            mimeTypesByExtension = new HashMap<String, String>(list.size());
            iconsByExtension = new HashMap<String, String>(list.size());
            for (Object o : list) {
                Element ele = (Element) o;
                String extension = DomUtils.getChildElementValueByTagName(ele, "extension");
                String mimeType = DomUtils.getChildElementValueByTagName(ele, "mime-type");
                String icon = DomUtils.getChildElementValueByTagName(ele, "icon");
                extensionsByMimetype.put(mimeType, extension);
                mimeTypesByExtension.put(extension, mimeType);
                if (icon != null)
                    iconsByExtension.put(extension, icon);
            }
        } catch (Exception e) {
            throw new FileStoreException("parse mimeType file: " + DEFAULT_CONFIG + " error", e);
        }
    }

    public String getExtension(String mimeType) {
        return extensionsByMimetype.get(mimeType);
    }

    public String getIcon(String fileName) {
        return getIconByExt(getExt(fileName));
    }

    public String getMimeType(String fileName) {
        return getMimeTypeByExt(getExt(fileName));
    }

    @Override
    public String getIconByExt(String ext) {
        if (ext != null && iconsByExtension.containsKey(ext)) {
            return iconsByExtension.get(ext);
        }
        return ICON_UNKOWN;
    }

    @Override
    public String getMimeTypeByExt(String ext) {
        if (ext != null && mimeTypesByExtension.containsKey(ext)) {
            return mimeTypesByExtension.get(ext);
        }
        return MIMETYPE_BINARY;
    }

    private static String getExt(String fileName) {
        if (fileName != null) {
            int index = fileName.lastIndexOf('.');
            if (index > -1 && (index < fileName.length() - 1)) {
                return fileName.substring(index + 1).toLowerCase();
            }
        }
        return null;
    }
}
