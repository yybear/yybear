package org.bear.framework.support.hibernate;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.internal.CoreMessageLogger;
import org.hibernate.usertype.UserType;
import org.jboss.logging.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 使用json形式实现的hibernate类型定义.
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-21
 */
public class JSONType implements Serializable, UserType{
	private static final CoreMessageLogger LOG = Logger.getMessageLogger(CoreMessageLogger.class, JSONType.class.getName());
    private static final long serialVersionUID = 9015678551160115883L;
    private int sqlType = Types.VARCHAR;

	@Override
	public int[] sqlTypes() {
		return new int[]{sqlType};
	}

	@Override
	public Class returnedClass() {
		return Object.class;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		return x == y;
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names,
			SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		Object object = rs.getObject(names[0]);
        if (rs.wasNull()) {
            if (LOG.isTraceEnabled()) LOG.tracev("Returning null as column {0}", names[0]);
            return null;
        } else {
            return JSON.parse((String) object);
        }
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index,
			SessionImplementor session) throws HibernateException, SQLException {
		if (value == null) {
            if (LOG.isTraceEnabled()) LOG.tracev("Binding null to parameter: {0}", index);
            st.setNull(index, sqlType);
        } else {
            st.setObject(index, JSON.toJSONString(value, SerializerFeature.WriteClassName), sqlType);
        }
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	@Override
	public Object assemble(Serializable cached, Object owner)
			throws HibernateException {
		return cached;
	}

	@Override
	public Object replace(Object original, Object target, Object owner)
			throws HibernateException {
		return original;
	}

}
