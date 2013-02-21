package org.apache.avro.ipc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Proxy;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.List;

import org.apache.avro.ipc.HttpTransceiver;
/**
 * .
 * <p/>
 *
 * @author <a href="mailto:ganqing@any123.com">gan qing</a>
 * @version V1.0, 13-2-19
 */
public class AvroHttpTransceiver extends HttpTransceiver {
	public static final String CONTENT_TYPE = HttpTransceiver.CONTENT_TYPE;

	public AvroHttpTransceiver(URL url) {
		super(url);
	}

	public AvroHttpTransceiver(URL url, Proxy proxy) {
		super(url, proxy);
	}
	
    public static List<ByteBuffer> readBuffers(InputStream in)
    	    throws IOException {
    	return HttpTransceiver.readBuffers(in);
    }
    
    public static void writeBuffers(List<ByteBuffer> buffers, OutputStream out)
    	    throws IOException {
    	HttpTransceiver.writeBuffers(buffers, out);
    }
    
    public static int getLength(List<ByteBuffer> buffers) {
    	return HttpTransceiver.getLength(buffers);
    }
    
}
