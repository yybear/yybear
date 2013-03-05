package org.bear.tool;

import org.apache.avro.Protocol;
import org.apache.avro.compiler.idl.Idl;
import org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.io.PrintStream;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-3-4
 */
public class Idl2ProtocolUtil {
	private static final String AVPR = "avpr";
	private static final String AVSC = "avsc";
	private static final String ECODING = "utf-8";
	public static void convert(String idlDir, String protocolDir) {
		try {
			File idls = new File(idlDir);
			File protocols = new File(protocolDir);
			if(!idls.isDirectory() || !protocols.isDirectory())
				return;
			FileUtils.cleanDirectory(protocols); // 删除老的文件
			File[] idlList = idls.listFiles();
			for(File idl : idlList) {
				System.out.println("convert file is : " + idl.getName());
				String suffix = getSuffix(idl.getName());
				if(AVSC.equals(suffix) || AVPR.equals(suffix)) {
					System.out.println("file width " + suffix + " doesn't need convert");
					// avsc和avpr直接拷贝过去
					File newFile = new File(protocolDir+File.separator+idl.getName());
					FileUtils.copyFile(idl, newFile);
					continue;
				}
				
				// idl转化为avpr
				Idl parser = new Idl(idl);
				File protocol = new File(protocolDir+File.separator+getName(idl.getName())+"."+AVPR);
				Protocol p = parser.CompilationUnit();
				
				PrintStream parseOut = new PrintStream(protocol, ECODING);
				parseOut.print(p.toString(true));
			}
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public static String getSuffix(String fileName) {
		int index = fileName.indexOf(".");
		return fileName.substring(index+1);
	}
	
	public static String getName(String fileName) {
		int index = fileName.indexOf(".");
		return fileName.substring(0, index);
	}
}
