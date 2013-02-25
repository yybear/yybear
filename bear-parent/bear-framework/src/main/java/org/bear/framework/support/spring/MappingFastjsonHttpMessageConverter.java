package org.bear.framework.support.spring;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.nio.charset.Charset;
/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-22
 */
public class MappingFastjsonHttpMessageConverter extends AbstractHttpMessageConverter<Object>{
	private String jsonpParameterName = "callback";

    public void setJsonpParameterName(String jsonpParameterName) {
        this.jsonpParameterName = jsonpParameterName;
    }

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    
    public MappingFastjsonHttpMessageConverter() {
        super(new MediaType("application", "json", DEFAULT_CHARSET));
    }
    
	@Override
	protected boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	protected Object readInternal(Class<? extends Object> clazz,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		return JSON.parseObject(FileCopyUtils.copyToByteArray(inputMessage.getBody()), clazz);
	}

	@Override
	protected void writeInternal(Object t, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		SerializeWriter out = new SerializeWriter();
        out.config(SerializerFeature.DisableCircularReferenceDetect, true);
        JSONSerializer serializer = new JSONSerializer(out);
        serializer.write(t);
        String jsonpCallback = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getParameter(jsonpParameterName);
        Writer writer = new OutputStreamWriter(outputMessage.getBody(), DEFAULT_CHARSET);
        try {
            if (StringUtils.isEmpty(jsonpCallback)) {
                out.writeTo(writer);
            } else {
                writer.write(jsonpCallback + "(");
                out.writeTo(writer);
                writer.write(");");
            }
            writer.flush();
        } finally {
            out.close();
        }
		
	}

}
