/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package org.bear.api.fs;  
@SuppressWarnings("all")
/** * 表示支持的图片变换类型
	 *
	 * @field RESIZE 等比缩放
	 *
	 * @field CROP 裁剪
	 *
	 * @field ROTATE 旋转 */
@org.apache.avro.specific.AvroGenerated
public enum Operate { 
  RESIZE, CROP, ROTATE  ;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"enum\",\"name\":\"Operate\",\"namespace\":\"org.bear.api.fs\",\"doc\":\"* 表示支持的图片变换类型\\r\\n\\t *\\r\\n\\t * @field RESIZE 等比缩放\\r\\n\\t *\\r\\n\\t * @field CROP 裁剪\\r\\n\\t *\\r\\n\\t * @field ROTATE 旋转\",\"symbols\":[\"RESIZE\",\"CROP\",\"ROTATE\"]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
}
