package org.bear.searcher.model;

public enum MimeType {
    /**
     * 普通文本
     */
    TEXT("普通"),
    /**
     * 普通文件
     */
    BIN("文件"),
    /**
     * 文档文件
     */
    DOC("文档"),
    /**
     * 图片文件
     */
    IMG("图片"),
    /**
     * 媒体文件
     */
    MIDEA("媒体");
    private String title;

    private MimeType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
