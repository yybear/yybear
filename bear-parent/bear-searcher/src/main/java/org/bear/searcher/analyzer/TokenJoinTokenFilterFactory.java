package org.bear.searcher.analyzer;

import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:ganqing@any123.com">gan qing</a>
 * @version V1.0, 2013-1-28
 */
public class TokenJoinTokenFilterFactory extends TokenFilterFactory {
    private int minSize;
    private int maxSize;

    @Override
    public void init(Map<String, String> args) {
        super.init(args);
        String minArg = args.get("minSize");
        minSize = (minArg != null ? Integer.parseInt(minArg) : 2);
        String maxArg = args.get("maxSize");
        maxSize = (maxArg != null ? Integer.parseInt(maxArg) : 3);
    }

    public TokenJoinTokenFilter create(TokenStream input) {
        return new TokenJoinTokenFilter(input, minSize, maxSize);
    }
}
