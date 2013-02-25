package org.bear.filestore.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.bear.framework.support.hibernate.JSONType;
import org.bear.framework.util.Statusable;
import org.bear.global.type.AccessScope;
import org.bear.global.type.Status;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 12-7-2
 */
@Entity
@Table(name="fs_file")
/*@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)*/
@TypeDef(name = "json", typeClass = JSONType.class)
public class File implements Statusable, Serializable {
    public static final Set<String> IMAGE_POSTFIX = new HashSet<String>(Arrays.asList(new String[]{"gif", "bmp", "jpg", "jpeg", "png"}));
    public static final Set<String> VIDEO_POSTFIX = new HashSet<String>(Arrays.asList(new String[]{"mpg", "mpeg", "mpe", "avi", "mov", "asf", "mp4", "wmv", "flv", "3gp"}));
    private static final long serialVersionUID = -3506689688738825538L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, name="biz_id")
    private int bizId;
    @Column(length = 64, nullable = false)
    private String owner;
    @Column(name="user_id")
    private long userId;
    @Column(length = 128)
    private String name;
    @Column(nullable = false, name="update_at", columnDefinition="timestamp null")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;
    private int size;
    @Column(length = 128)
    private String storage;
    @Type(type = "json")
    @Column(length = 4000)
    private Map<String, Object> data = new HashMap<String, Object>();
    @Enumerated
    private AccessScope scope = AccessScope.ALL;
    @Enumerated
    @Column(nullable = false)
    private Status status = Status.ENABLED;

    @Transient
    private String bizKey;
    @Transient
    private String icon;
    @Transient
    private String encryptedId;
    @Transient
    private String ext;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JSONField(serialize = false)
    public int getBizId() {
        return bizId;
    }

    public void setBizId(int bizId) {
        this.bizId = bizId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        ext = null;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @JSONField(serialize = false)
    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    @JSONField(serialize = false)
    public AccessScope getScope() {
        return scope;
    }

    public void setScope(AccessScope scope) {
        this.scope = scope;
    }

    @JSONField(serialize = false)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getBizKey() {
        return bizKey;
    }

    public void setBizKey(String bizKey) {
        this.bizKey = bizKey;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getEncryptedId() {
        return encryptedId;
    }

    public void setEncryptedId(String encryptedId) {
        this.encryptedId = encryptedId;
    }

    public String toKey() {
        return toKey(id);
    }

    @JSONField(serialize = false)
    public String getExt() {
        if (ext == null) {
            ext = getExt(name);
        }
        return ext;
    }

    @JSONField(serialize = false)
    public String getSimpleName() {
        return getSimpleName(name);
    }

    public boolean isImage() {
        return IMAGE_POSTFIX.contains(getExt());
    }

    @JSONField(serialize = false)
    public boolean isVideo() {
        return VIDEO_POSTFIX.contains(getExt());
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public static String toKey(Long id) {
        if (id == null) {
            throw new NullPointerException("file id can not be null");
        }
        return "fs-" + id;
    }

    public static String getSimpleName(String fileName) {
        if (fileName != null) {
            int index = fileName.lastIndexOf('.');
            return index > -1 ? fileName.substring(0, index) : fileName;
        }
        return null;
    }

    public static String getExt(String fileName) {
        if (fileName != null) {
            int index = fileName.lastIndexOf('.');
            if (index > -1 && (index < fileName.length() - 1)) {
                return fileName.substring(index + 1).toLowerCase();
            }
        }
        return "";
    }
}
