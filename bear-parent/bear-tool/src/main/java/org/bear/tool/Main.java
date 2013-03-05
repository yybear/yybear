package org.bear.tool;
/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-3-5
 */
public class Main {
	public static void main(String[] args) {
		if("dl2pr".equals(args[0]))
			Idl2ProtocolUtil.convert(args[1], args[2]);

	}

}
