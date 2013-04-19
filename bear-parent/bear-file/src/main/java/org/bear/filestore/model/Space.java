package org.bear.filestore.model;

import com.google.common.collect.Sets;

import org.bear.filestore.enums.SpaceType;
import org.bear.framework.config.BizConfig;
import org.bear.global.type.AccessScope;
import org.bear.global.type.Status;

import java.util.Set;

/**
 * 存储空间配置.
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-22
 */
public class Space extends BizConfig {
    private static final long serialVersionUID = -6333544217601289524L;
    private long quota = -1;
    private long used = 0;
    private long userQuita = -1;
    private int maxSize = -1;
    private Set<String> allowExts = Sets.newHashSet();
    private short accessType = -1;
    private SpaceType type = SpaceType.BIN;
    private AccessScope readScope = AccessScope.ALL;
    private AccessScope writeScope = AccessScope.SELF;
    private Status status = Status.ENABLED;
    private boolean saveMetadata = false;

    private long[] defaultIds;

    private Dimension[] zooms;
    private Dimension initResize;
    private Rectangle initCrop;

    private int cacheSeconds;
    private int xsendfileLimitRate;

    public long getQuota() {
        return quota;
    }

    public void setQuota(long quota) {
        this.quota = quota;
    }

    public long getUsed() {
        return used;
    }

    public void setUsed(long used) {
        this.used = used;
    }

    public long getUserQuita() {
        return userQuita;
    }

    public void setUserQuita(long userQuita) {
        this.userQuita = userQuita;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public Set<String> getAllowExts() {
        return allowExts;
    }

    public void setAllowExts(Set<String> allowExts) {
        this.allowExts = allowExts;
    }

    public short getAccessType() {
        return accessType;
    }

    public void setAccessType(short accessType) {
        this.accessType = accessType;
    }

    public SpaceType getType() {
        return type;
    }

    public void setType(SpaceType type) {
        this.type = type;
    }

    public AccessScope getReadScope() {
        return readScope;
    }

    public void setReadScope(AccessScope readScope) {
        this.readScope = readScope;
    }

    public AccessScope getWriteScope() {
        return writeScope;
    }

    public void setWriteScope(AccessScope writeScope) {
        this.writeScope = writeScope;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isSaveMetadata() {
        return saveMetadata;
    }

    public void setSaveMetadata(boolean saveMetadata) {
        this.saveMetadata = saveMetadata;
    }

    public long[] getDefaultIds() {
        return defaultIds;
    }

    public void setDefaultIds(long[] defaultIds) {
        this.defaultIds = defaultIds;
    }

    public Dimension[] getZooms() {
        return zooms;
    }

    public void setZooms(Dimension[] zooms) {
        this.zooms = zooms;
    }

    public Dimension getInitResize() {
        return initResize;
    }

    public void setInitResize(Dimension initResize) {
        this.initResize = initResize;
    }

    public Rectangle getInitCrop() {
        return initCrop;
    }

    public void setInitCrop(Rectangle initCrop) {
        this.initCrop = initCrop;
    }

    public int getCacheSeconds() {
        return cacheSeconds;
    }

    public void setCacheSeconds(int cacheSeconds) {
        this.cacheSeconds = cacheSeconds;
    }

    public int getXsendfileLimitRate() {
        return xsendfileLimitRate;
    }

    public void setXsendfileLimitRate(int xsendfileLimitRate) {
        this.xsendfileLimitRate = xsendfileLimitRate;
    }

    public boolean isExtAllow(String ext) {
        return allowExts.isEmpty() || allowExts.contains(ext);
    }
}
