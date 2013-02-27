package org.bear.framework.dao;
/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-26
 */
public interface EntityDao<E> {
	public static final long DEFAULT_ITERATOR_BATCH_SIZE = 200;

	public interface Processor<E> {
		void process(E entity);
	}

	/**
	 * 创建
	 * 
	 * @param entity
	 * @return 新增实体的自增主键
	 */
	long create(E entity);

	E get(long id);

	boolean exists(long id);

	void update(E entity);

	void delete(long id);

	void deleteAll();

	long[] batchCreate(E[] entities);

	E[] batchGet(long[] ids);

	void batchUpdate(E[] entities);

	void batchDelete(long[] ids);

	long countAll();

	/**
	 * 获取指定范围的实体（按主键排序）
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	E[] list(long start, long limit);

	/**
	 * 获取指定范围的实体主键（按主键排序）
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	long[] listIds(long start, long limit);

	/**
	 * 获取所有ID
	 * 
	 * @return
	 */
	long[] getAllIds();

	/**
	 * 迭代处理
	 * 
	 * @param processor
	 * @param batchSize
	 *            批次大小，若<=0则使用默认值200
	 */
	void iterate(Processor<E> processor, long batchSize);
}
