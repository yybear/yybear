package org.bear.framework.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bear.api.type.TypeConstants;

import com.alibaba.fastjson.JSON;

/**
 * 本地代码和暴露服务之间的java类型转化工具类.
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-25
 */
public class TypeUtils {
	public static <T extends Enum<T>> T convertEnum(Enum in, Class<T> clazz) {
		return in==null?null:Enum.valueOf(clazz, in.name());
	}
	
	public static Map<String, String> toStringMap(Map<String, Object> in) {
		if(in == null)
			return null;
		Map<String, String> map = new HashMap<String, String>(in.size());
        for (Map.Entry<String, Object> entry : in.entrySet()) {
            Object value = entry.getValue();
            map.put(entry.getKey(), value == null ? "" : value.toString());
        }
        return map;
	}
	
	public static Map<String, Object> toMap(Map<String, String> data) {
        return data == null ? null : (data.containsKey(TypeConstants.JSON) ? JSON.parseObject(data
                .get(TypeConstants.JSON)) : (Map) data);
    }
	
	public static <V> Map<String, V> toStringKey (Map<Long, V> data) {
		if(data == null)
			return null;
		Map<String, V> map = new HashMap<String, V>();
		
		for(Entry<Long, V> entry : data.entrySet()) {
			map.put(String.valueOf(entry.getKey()), entry.getValue());
		}
		
		return map;
	}
	
	public static <V> Map<Long, V> toLongKey (Map<String, V> data) {
		if(data == null)
			return null;
		Map<Long, V> map = new HashMap<Long, V>();
		
		for(Entry<String, V> entry : data.entrySet()) {
			map.put(Long.valueOf(entry.getKey()), entry.getValue());
		}
		
		return map;
	}
	
	public static <V> Set<V> toSet(List<V> data) {
		if(data == null)
			return null;
		Set<V> set = new HashSet<V>();
		for(V value : data) {
			set.add(value);
		}
		
		return set;
	}
	
	public static <V> List<V> toList(Set<V> data) {
		if(data == null)
			return null;
		List<V> list = new ArrayList<V>();
		for(V value : data) {
			list.add(value);
		}
		
		return list;
	}
}
