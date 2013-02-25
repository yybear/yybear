package org.bear.api.type;


public class TypeConstants {

  /**
   * 对于这种自定义数据的方式,7: optional map<string, string> data
   * 如果放入一个已JSON常量作为key的值,则表示已这个值反序列后作为data
   */
  public static final String JSON = "_json";

  public static final short TRUE_BOOLEAN = (short)1;

  public static final short FALSE_BOOLEAN = (short)-1;

  /**
   * 表示空的bool
   * 因为Thrift的基本类型不支持null,在做插入操作的时候用来表示主键为空
   */
  public static final short NULL_BOOLEAN = (short)0;

  /**
   * 同上
   */
  public static final short NULL_SHORT = (short)0;

  /**
   * 同上
   */
  public static final int NULL_INT = 0;

  /**
   * 同上
   */
  public static final long NULL_LONG = 0L;

  /**
   * 同上
   */
  public static final double NULL_DOUBLE = (double)0;

  /**
   * 同上
   */
  public static final long NULL_DATE = 0L;

  /**
   * 一分钟的秒数
   */
  public static final int SECONDS_PER_MINUTE = 60;

  /**
   * 一小时的秒数
   */
  public static final int SECONDS_PER_HOUR = 3600;

  /**
   * 一天的秒数
   */
  public static final int SECONDS_PER_DAY = 86400;

  /**
   * 一周的秒数
   */
  public static final int SECONDS_PER_WEEK = 604800;

  /**
   * 一个月的秒数
   */
  public static final int SECONDS_PER_MONTH = 18144000;

}
