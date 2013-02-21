package org.bear.searcher.model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.bear.searcher.Constants;

public class Index implements Serializable {
    private static final long serialVersionUID = -4191156991215104794L;
    public static final String APP = "app";
    public static final String CATEGORY = "category";
    public static final String MIMETYPE = "mimeType";
    public static final String SCORE = "score";
    public static final String DATE = "date";
    public static final String SORT = "sort";
    public static final Pattern TRIM_TAG_PATTERN = Pattern.compile("<.*?>");

    private String id;
    private int scope = Constants.SCOPE_GENERAL;
    private MimeType mimeType = MimeType.TEXT;
    private String appId;
    private String categoryId;
    private String[] ownerIds;
    private String[] roleIds;
    private String[] tags;
    private String title;
    private String body;
    private Date date;
    private boolean isPublic = true;
    private Map<String, Serializable> fields = new LinkedHashMap<String, Serializable>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    public MimeType getMimeType() {
        return mimeType;
    }

    public void setMimeType(MimeType mimeType) {
        this.mimeType = mimeType;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String[] getOwnerIds() {
        return ownerIds;
    }

    public void setOwnerIds(String[] ownerIds) {
        this.ownerIds = ownerIds;
    }

    public String[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String[] roleIds) {
        this.roleIds = roleIds;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public void appendBody(String text) {
        appendBody(text, -1);
    }

    public void appendBody(String text, int maxLength) {
        if (StringUtils.isNotBlank(text)) {
            StringBuilder sb = new StringBuilder();
            if (StringUtils.isNotEmpty(body)) {
                sb.append(body).append(" ");
            }
            if (maxLength > 0) {
                text = StringUtils.left(trimTag(text), maxLength);
            }
            sb.append(text);
            body = sb.toString();
        }
    }

    public void insertBody(String text) {
        insertBody(text, -1);
    }

    public void insertBody(String text, int maxLength) {
        if (StringUtils.isNotBlank(text)) {
            if (maxLength > 0) {
                text = StringUtils.left(trimTag(text), maxLength);
            }
            StringBuilder sb = new StringBuilder(text);
            if (StringUtils.isNotEmpty(body)) {
                sb.append(" ").append(body);
            }
            body = sb.toString();
        }
    }

    public Map<String, Serializable> getFields() {
        return fields;
    }

    public void addField(String name, Serializable value) {
        if (value != null)
            fields.put(name, value);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + id + "," + title + "," + appId + "," + categoryId + "]";
    }

    public static String trimTag(String s) {
        if (s == null) {
            return null;
        }
        return TRIM_TAG_PATTERN.matcher(s).replaceAll("");
    }
}
