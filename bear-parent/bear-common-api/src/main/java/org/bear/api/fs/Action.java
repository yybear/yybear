/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package org.bear.api.fs;  
@SuppressWarnings("all")
/** * 图片变换操作
	 *
	 * @field operate 操作类型
	 *
	 * @field params 附加参数 */
@org.apache.avro.specific.AvroGenerated
public class Action extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Action\",\"namespace\":\"org.bear.api.fs\",\"doc\":\"* 图片变换操作\\r\\n\\t *\\r\\n\\t * @field operate 操作类型\\r\\n\\t *\\r\\n\\t * @field params 附加参数\",\"fields\":[{\"name\":\"operate\",\"type\":{\"type\":\"enum\",\"name\":\"Operate\",\"doc\":\"* 表示支持的图片变换类型\\r\\n\\t *\\r\\n\\t * @field RESIZE 等比缩放\\r\\n\\t *\\r\\n\\t * @field CROP 裁剪\\r\\n\\t *\\r\\n\\t * @field ROTATE 旋转\",\"symbols\":[\"RESIZE\",\"CROP\",\"ROTATE\"]}},{\"name\":\"param\",\"type\":{\"type\":\"map\",\"values\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"avro.java.string\":\"String\"}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public org.bear.api.fs.Operate operate;
  @Deprecated public java.util.Map<java.lang.String,java.lang.String> param;

  /**
   * Default constructor.
   */
  public Action() {}

  /**
   * All-args constructor.
   */
  public Action(org.bear.api.fs.Operate operate, java.util.Map<java.lang.String,java.lang.String> param) {
    this.operate = operate;
    this.param = param;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return operate;
    case 1: return param;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: operate = (org.bear.api.fs.Operate)value$; break;
    case 1: param = (java.util.Map<java.lang.String,java.lang.String>)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'operate' field.
   */
  public org.bear.api.fs.Operate getOperate() {
    return operate;
  }

  /**
   * Sets the value of the 'operate' field.
   * @param value the value to set.
   */
  public void setOperate(org.bear.api.fs.Operate value) {
    this.operate = value;
  }

  /**
   * Gets the value of the 'param' field.
   */
  public java.util.Map<java.lang.String,java.lang.String> getParam() {
    return param;
  }

  /**
   * Sets the value of the 'param' field.
   * @param value the value to set.
   */
  public void setParam(java.util.Map<java.lang.String,java.lang.String> value) {
    this.param = value;
  }

  /** Creates a new Action RecordBuilder */
  public static org.bear.api.fs.Action.Builder newBuilder() {
    return new org.bear.api.fs.Action.Builder();
  }
  
  /** Creates a new Action RecordBuilder by copying an existing Builder */
  public static org.bear.api.fs.Action.Builder newBuilder(org.bear.api.fs.Action.Builder other) {
    return new org.bear.api.fs.Action.Builder(other);
  }
  
  /** Creates a new Action RecordBuilder by copying an existing Action instance */
  public static org.bear.api.fs.Action.Builder newBuilder(org.bear.api.fs.Action other) {
    return new org.bear.api.fs.Action.Builder(other);
  }
  
  /**
   * RecordBuilder for Action instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Action>
    implements org.apache.avro.data.RecordBuilder<Action> {

    private org.bear.api.fs.Operate operate;
    private java.util.Map<java.lang.String,java.lang.String> param;

    /** Creates a new Builder */
    private Builder() {
      super(org.bear.api.fs.Action.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(org.bear.api.fs.Action.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing Action instance */
    private Builder(org.bear.api.fs.Action other) {
            super(org.bear.api.fs.Action.SCHEMA$);
      if (isValidValue(fields()[0], other.operate)) {
        this.operate = data().deepCopy(fields()[0].schema(), other.operate);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.param)) {
        this.param = data().deepCopy(fields()[1].schema(), other.param);
        fieldSetFlags()[1] = true;
      }
    }

    /** Gets the value of the 'operate' field */
    public org.bear.api.fs.Operate getOperate() {
      return operate;
    }
    
    /** Sets the value of the 'operate' field */
    public org.bear.api.fs.Action.Builder setOperate(org.bear.api.fs.Operate value) {
      validate(fields()[0], value);
      this.operate = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'operate' field has been set */
    public boolean hasOperate() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'operate' field */
    public org.bear.api.fs.Action.Builder clearOperate() {
      operate = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'param' field */
    public java.util.Map<java.lang.String,java.lang.String> getParam() {
      return param;
    }
    
    /** Sets the value of the 'param' field */
    public org.bear.api.fs.Action.Builder setParam(java.util.Map<java.lang.String,java.lang.String> value) {
      validate(fields()[1], value);
      this.param = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'param' field has been set */
    public boolean hasParam() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'param' field */
    public org.bear.api.fs.Action.Builder clearParam() {
      param = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    public Action build() {
      try {
        Action record = new Action();
        record.operate = fieldSetFlags()[0] ? this.operate : (org.bear.api.fs.Operate) defaultValue(fields()[0]);
        record.param = fieldSetFlags()[1] ? this.param : (java.util.Map<java.lang.String,java.lang.String>) defaultValue(fields()[1]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
