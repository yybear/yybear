package org.bear.framework.support.sitemesh;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.sitemesh.content.Content;
import org.sitemesh.content.ContentProperty;
import org.sitemesh.content.memory.InMemoryContent;
import org.sitemesh.webapp.WebAppContext;
/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-3-26
 */
public class SitemeshHelper {
	public static void extractMeta(PageContext context) {
        Content content = (Content) context.getRequest().getAttribute(WebAppContext.CONTENT_KEY);
        if (content == null) {
            content = new InMemoryContent();
        }
        ContentProperty cp = content.getExtractedProperties();
        context.setAttribute("_body", cp.getChild("body").getValue());
        context.setAttribute("_title", cp.getChild("title").getValue());
        context.setAttribute("_head", cp.getChild("head").getValue());
        Map<String, String> metaMap = new HashMap<String, String>();
        for (ContentProperty cp1 : cp.getChild("meta").getChildren()) {
            metaMap.put(cp1.getName(), cp1.getValue());
        }
        context.setAttribute("_meta", metaMap);

    }
}
