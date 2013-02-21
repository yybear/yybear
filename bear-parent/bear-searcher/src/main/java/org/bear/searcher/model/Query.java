package org.bear.searcher.model;

import java.util.List;
import java.util.Map;

import org.bear.searcher.Constants;

public class Query {
    private String keyword;
    private int scope = Constants.SCOPE_GENERAL;
    private String bizKey;
    private String categoryId;
    private MimeType mimeType;
    private String date;
    private List<String> tags;
    private Map<String, String> filter;
    private String sort;
    private boolean returnExInfo;
    private int fragmentSize = 30;
    private int titleClipSize = fragmentSize;
    private int bodyClipSize = fragmentSize;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    public String getBizKey() {
        return bizKey;
    }

    public void setBizKey(String bizKey) {
        this.bizKey = bizKey;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public MimeType getMimeType() {
        return mimeType;
    }

    public void setMimeType(MimeType mimeType) {
        this.mimeType = mimeType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Map<String, String> getFilter() {
        return filter;
    }

    public void setFilter(Map<String, String> filter) {
        this.filter = filter;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public boolean isReturnExInfo() {
        return returnExInfo;
    }

    public void setReturnExInfo(boolean returnExInfo) {
        this.returnExInfo = returnExInfo;
    }

	public int getFragmentSize() {
		return fragmentSize;
	}

	public void setFragmentSize(int fragmentSize) {
		if(fragmentSize>0){
			this.fragmentSize = fragmentSize;
		}
	}

	public int getTitleClipSize() {
		return titleClipSize;
	}

	public void setTitleClipSize(int titleClipSize) {
		if(titleClipSize>0){
			this.titleClipSize = titleClipSize;
		}
	}

	public int getBodyClipSize() {
		return bodyClipSize;
	}

	public void setBodyClipSize(int bodyClipSize) {
		if(bodyClipSize>0){
			this.bodyClipSize = bodyClipSize;
		}
	}
    
}
