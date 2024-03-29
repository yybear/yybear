/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package org.bear.api.app;  
@SuppressWarnings("all")
/** * 表示一个需要公用服务支持的应用,包含多个业务 */
@org.apache.avro.specific.AvroGenerated
public class App extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"App\",\"namespace\":\"org.bear.api.app\",\"doc\":\"* 表示一个需要公用服务支持的应用,包含多个业务\",\"fields\":[{\"name\":\"id\",\"type\":\"int\",\"doc\":\"* 应用的编号\\r\\n\\t\\t *\\r\\n\\t\\t * @readonly\"},{\"name\":\"key\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"doc\":\"* 应用的key,必须唯一\"},{\"name\":\"name\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"doc\":\"* 应用的名称\"},{\"name\":\"status\",\"type\":{\"type\":\"enum\",\"name\":\"Status\",\"namespace\":\"org.bear.api.type\",\"doc\":\"表示实体对象的状态\",\"symbols\":[\"ENABLED\",\"DISABLED\",\"DELETED\"]},\"doc\":\"* 应用的状态\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  /** * 应用的编号
		 *
		 * @readonly */
  @Deprecated public int id;
  /** * 应用的key,必须唯一 */
  @Deprecated public java.lang.String key;
  /** * 应用的名称 */
  @Deprecated public java.lang.String name;
  /** * 应用的状态 */
  @Deprecated public org.bear.api.type.Status status;

  /**
   * Default constructor.
   */
  public App() {}

  /**
   * All-args constructor.
   */
  public App(java.lang.Integer id, java.lang.String key, java.lang.String name, org.bear.api.type.Status status) {
    this.id = id;
    this.key = key;
    this.name = name;
    this.status = status;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return id;
    case 1: return key;
    case 2: return name;
    case 3: return status;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: id = (java.lang.Integer)value$; break;
    case 1: key = (java.lang.String)value$; break;
    case 2: name = (java.lang.String)value$; break;
    case 3: status = (org.bear.api.type.Status)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'id' field.
   * * 应用的编号
		 *
		 * @readonly   */
  public java.lang.Integer getId() {
    return id;
  }

  /**
   * Sets the value of the 'id' field.
   * * 应用的编号
		 *
		 * @readonly   * @param value the value to set.
   */
  public void setId(java.lang.Integer value) {
    this.id = value;
  }

  /**
   * Gets the value of the 'key' field.
   * * 应用的key,必须唯一   */
  public java.lang.String getKey() {
    return key;
  }

  /**
   * Sets the value of the 'key' field.
   * * 应用的key,必须唯一   * @param value the value to set.
   */
  public void setKey(java.lang.String value) {
    this.key = value;
  }

  /**
   * Gets the value of the 'name' field.
   * * 应用的名称   */
  public java.lang.String getName() {
    return name;
  }

  /**
   * Sets the value of the 'name' field.
   * * 应用的名称   * @param value the value to set.
   */
  public void setName(java.lang.String value) {
    this.name = value;
  }

  /**
   * Gets the value of the 'status' field.
   * * 应用的状态   */
  public org.bear.api.type.Status getStatus() {
    return status;
  }

  /**
   * Sets the value of the 'status' field.
   * * 应用的状态   * @param value the value to set.
   */
  public void setStatus(org.bear.api.type.Status value) {
    this.status = value;
  }

  /** Creates a new App RecordBuilder */
  public static org.bear.api.app.App.Builder newBuilder() {
    return new org.bear.api.app.App.Builder();
  }
  
  /** Creates a new App RecordBuilder by copying an existing Builder */
  public static org.bear.api.app.App.Builder newBuilder(org.bear.api.app.App.Builder other) {
    return new org.bear.api.app.App.Builder(other);
  }
  
  /** Creates a new App RecordBuilder by copying an existing App instance */
  public static org.bear.api.app.App.Builder newBuilder(org.bear.api.app.App other) {
    return new org.bear.api.app.App.Builder(other);
  }
  
  /**
   * RecordBuilder for App instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<App>
    implements org.apache.avro.data.RecordBuilder<App> {

    private int id;
    private java.lang.String key;
    private java.lang.String name;
    private org.bear.api.type.Status status;

    /** Creates a new Builder */
    private Builder() {
      super(org.bear.api.app.App.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(org.bear.api.app.App.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing App instance */
    private Builder(org.bear.api.app.App other) {
            super(org.bear.api.app.App.SCHEMA$);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.key)) {
        this.key = data().deepCopy(fields()[1].schema(), other.key);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.name)) {
        this.name = data().deepCopy(fields()[2].schema(), other.name);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.status)) {
        this.status = data().deepCopy(fields()[3].schema(), other.status);
        fieldSetFlags()[3] = true;
      }
    }

    /** Gets the value of the 'id' field */
    public java.lang.Integer getId() {
      return id;
    }
    
    /** Sets the value of the 'id' field */
    public org.bear.api.app.App.Builder setId(int value) {
      validate(fields()[0], value);
      this.id = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'id' field has been set */
    public boolean hasId() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'id' field */
    public org.bear.api.app.App.Builder clearId() {
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'key' field */
    public java.lang.String getKey() {
      return key;
    }
    
    /** Sets the value of the 'key' field */
    public org.bear.api.app.App.Builder setKey(java.lang.String value) {
      validate(fields()[1], value);
      this.key = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'key' field has been set */
    public boolean hasKey() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'key' field */
    public org.bear.api.app.App.Builder clearKey() {
      key = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'name' field */
    public java.lang.String getName() {
      return name;
    }
    
    /** Sets the value of the 'name' field */
    public org.bear.api.app.App.Builder setName(java.lang.String value) {
      validate(fields()[2], value);
      this.name = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'name' field has been set */
    public boolean hasName() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'name' field */
    public org.bear.api.app.App.Builder clearName() {
      name = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /** Gets the value of the 'status' field */
    public org.bear.api.type.Status getStatus() {
      return status;
    }
    
    /** Sets the value of the 'status' field */
    public org.bear.api.app.App.Builder setStatus(org.bear.api.type.Status value) {
      validate(fields()[3], value);
      this.status = value;
      fieldSetFlags()[3] = true;
      return this; 
    }
    
    /** Checks whether the 'status' field has been set */
    public boolean hasStatus() {
      return fieldSetFlags()[3];
    }
    
    /** Clears the value of the 'status' field */
    public org.bear.api.app.App.Builder clearStatus() {
      status = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    @Override
    public App build() {
      try {
        App record = new App();
        record.id = fieldSetFlags()[0] ? this.id : (java.lang.Integer) defaultValue(fields()[0]);
        record.key = fieldSetFlags()[1] ? this.key : (java.lang.String) defaultValue(fields()[1]);
        record.name = fieldSetFlags()[2] ? this.name : (java.lang.String) defaultValue(fields()[2]);
        record.status = fieldSetFlags()[3] ? this.status : (org.bear.api.type.Status) defaultValue(fields()[3]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
