/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package org.bear.api.identity;  
@SuppressWarnings("all")
/** * 密码保护 */
@org.apache.avro.specific.AvroGenerated
public class PasswordProtection extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"PasswordProtection\",\"namespace\":\"org.bear.api.identity\",\"doc\":\"* 密码保护\",\"fields\":[{\"name\":\"uid\",\"type\":\"long\",\"doc\":\"* 用户ID（所属用户）\"},{\"name\":\"question\",\"type\":{\"type\":\"enum\",\"name\":\"ProtectionQuestion\",\"doc\":\"* 我的爱人名字？\",\"symbols\":[\"FAV_FILM\",\"FAV_MUSIC\",\"FATHER_NAME\",\"MOTHER_NAME\",\"LOVER_NAME\"]},\"doc\":\"* 问题\"},{\"name\":\"answer\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"doc\":\"* 问题答案\\r\\n\\t\\t *\\r\\n\\t\\t * @size min=0, max=200\"}]}");
  /** * 用户ID（所属用户） */
  @Deprecated public long uid;
  /** * 问题 */
  @Deprecated public org.bear.api.identity.ProtectionQuestion question;
  /** * 问题答案
		 *
		 * @size min=0, max=200 */
  @Deprecated public java.lang.String answer;

  /**
   * Default constructor.
   */
  public PasswordProtection() {}

  /**
   * All-args constructor.
   */
  public PasswordProtection(java.lang.Long uid, org.bear.api.identity.ProtectionQuestion question, java.lang.String answer) {
    this.uid = uid;
    this.question = question;
    this.answer = answer;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return uid;
    case 1: return question;
    case 2: return answer;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: uid = (java.lang.Long)value$; break;
    case 1: question = (org.bear.api.identity.ProtectionQuestion)value$; break;
    case 2: answer = (java.lang.String)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'uid' field.
   * * 用户ID（所属用户）   */
  public java.lang.Long getUid() {
    return uid;
  }

  /**
   * Sets the value of the 'uid' field.
   * * 用户ID（所属用户）   * @param value the value to set.
   */
  public void setUid(java.lang.Long value) {
    this.uid = value;
  }

  /**
   * Gets the value of the 'question' field.
   * * 问题   */
  public org.bear.api.identity.ProtectionQuestion getQuestion() {
    return question;
  }

  /**
   * Sets the value of the 'question' field.
   * * 问题   * @param value the value to set.
   */
  public void setQuestion(org.bear.api.identity.ProtectionQuestion value) {
    this.question = value;
  }

  /**
   * Gets the value of the 'answer' field.
   * * 问题答案
		 *
		 * @size min=0, max=200   */
  public java.lang.String getAnswer() {
    return answer;
  }

  /**
   * Sets the value of the 'answer' field.
   * * 问题答案
		 *
		 * @size min=0, max=200   * @param value the value to set.
   */
  public void setAnswer(java.lang.String value) {
    this.answer = value;
  }

  /** Creates a new PasswordProtection RecordBuilder */
  public static org.bear.api.identity.PasswordProtection.Builder newBuilder() {
    return new org.bear.api.identity.PasswordProtection.Builder();
  }
  
  /** Creates a new PasswordProtection RecordBuilder by copying an existing Builder */
  public static org.bear.api.identity.PasswordProtection.Builder newBuilder(org.bear.api.identity.PasswordProtection.Builder other) {
    return new org.bear.api.identity.PasswordProtection.Builder(other);
  }
  
  /** Creates a new PasswordProtection RecordBuilder by copying an existing PasswordProtection instance */
  public static org.bear.api.identity.PasswordProtection.Builder newBuilder(org.bear.api.identity.PasswordProtection other) {
    return new org.bear.api.identity.PasswordProtection.Builder(other);
  }
  
  /**
   * RecordBuilder for PasswordProtection instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<PasswordProtection>
    implements org.apache.avro.data.RecordBuilder<PasswordProtection> {

    private long uid;
    private org.bear.api.identity.ProtectionQuestion question;
    private java.lang.String answer;

    /** Creates a new Builder */
    private Builder() {
      super(org.bear.api.identity.PasswordProtection.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(org.bear.api.identity.PasswordProtection.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing PasswordProtection instance */
    private Builder(org.bear.api.identity.PasswordProtection other) {
            super(org.bear.api.identity.PasswordProtection.SCHEMA$);
      if (isValidValue(fields()[0], other.uid)) {
        this.uid = data().deepCopy(fields()[0].schema(), other.uid);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.question)) {
        this.question = data().deepCopy(fields()[1].schema(), other.question);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.answer)) {
        this.answer = data().deepCopy(fields()[2].schema(), other.answer);
        fieldSetFlags()[2] = true;
      }
    }

    /** Gets the value of the 'uid' field */
    public java.lang.Long getUid() {
      return uid;
    }
    
    /** Sets the value of the 'uid' field */
    public org.bear.api.identity.PasswordProtection.Builder setUid(long value) {
      validate(fields()[0], value);
      this.uid = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'uid' field has been set */
    public boolean hasUid() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'uid' field */
    public org.bear.api.identity.PasswordProtection.Builder clearUid() {
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'question' field */
    public org.bear.api.identity.ProtectionQuestion getQuestion() {
      return question;
    }
    
    /** Sets the value of the 'question' field */
    public org.bear.api.identity.PasswordProtection.Builder setQuestion(org.bear.api.identity.ProtectionQuestion value) {
      validate(fields()[1], value);
      this.question = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'question' field has been set */
    public boolean hasQuestion() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'question' field */
    public org.bear.api.identity.PasswordProtection.Builder clearQuestion() {
      question = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'answer' field */
    public java.lang.String getAnswer() {
      return answer;
    }
    
    /** Sets the value of the 'answer' field */
    public org.bear.api.identity.PasswordProtection.Builder setAnswer(java.lang.String value) {
      validate(fields()[2], value);
      this.answer = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'answer' field has been set */
    public boolean hasAnswer() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'answer' field */
    public org.bear.api.identity.PasswordProtection.Builder clearAnswer() {
      answer = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    public PasswordProtection build() {
      try {
        PasswordProtection record = new PasswordProtection();
        record.uid = fieldSetFlags()[0] ? this.uid : (java.lang.Long) defaultValue(fields()[0]);
        record.question = fieldSetFlags()[1] ? this.question : (org.bear.api.identity.ProtectionQuestion) defaultValue(fields()[1]);
        record.answer = fieldSetFlags()[2] ? this.answer : (java.lang.String) defaultValue(fields()[2]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}