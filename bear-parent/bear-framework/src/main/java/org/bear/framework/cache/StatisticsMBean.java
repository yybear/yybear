package org.bear.framework.cache;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-26
 */
public interface StatisticsMBean {

    int getSize();

    long getHits();

    long getMisses();

    long getPuts();

    long getExpires();

    long getEvictions();

    long getReadBytes();

    long getWriteBytes();
}
