package org.bear.searcher.analyzer;

import java.io.Reader;
import java.util.Map;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.wltea.analyzer.lucene.IKTokenizer;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:ganqing@any123.com">gan qing</a>
 * @version V1.0, 2013-1-28
 */
public class IKTokenizerFactory extends TokenizerFactory {
	private boolean useSmart = false;

    public void init(Map<String, String> args) {
        String _arg = args.get("useSmart");
        useSmart = Boolean.parseBoolean(_arg);
    }

    public Tokenizer create(Reader reader) {
        return new IKTokenizer(reader, isUseSmart());
    }

    public void setUseSmart(boolean useSmart) {
        this.useSmart = useSmart;
    }

    public boolean isUseSmart() {
        return useSmart;
    }
}
