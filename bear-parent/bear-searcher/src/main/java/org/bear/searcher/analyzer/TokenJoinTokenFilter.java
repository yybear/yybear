package org.bear.searcher.analyzer;

import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;

/**
 * 将token组合成指定范围词长的token,用于自动完成和语法检查.
 * <p/>
 *
 * @author <a href="mailto:ganqing@any123.com">gan qing</a>
 * @version V1.0, 2013-1-28
 */
public class TokenJoinTokenFilter extends TokenFilter {
    private final int minSize;
    private final int maxSize;

    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);

    private LinkedList<Token> tokens = new LinkedList<Token>();
    private int point;

    protected TokenJoinTokenFilter(TokenStream input, int minSize, int maxSize) {
        super(input);
        if (minSize < 2) {
            throw new IllegalArgumentException("minSize must be greater than zero");
        }

        if (minSize > maxSize) {
            throw new IllegalArgumentException("maxSize must not be greater than minSize");
        }
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    @Override
    public boolean incrementToken() throws IOException {
        if (point >= minSize) {
            if (point > maxSize) {
                tokens.removeFirst();
                point = tokens.size();
            }
            StringBuilder sb = new StringBuilder();
            ListIterator<Token> it = tokens.listIterator(tokens.size() - point);
            int beginPosition = -1;
            Token token = null;
            while (it.hasNext()) {
                token = it.next();
                sb.append(token.getBuff());
                if (beginPosition < 0) {
                    beginPosition = token.getStartOffset();
                }
            }
            if (token != null) {
                clearAttributes();
                offsetAtt.setOffset(beginPosition, token.getEndOffset());
                termAtt.append(sb.toString());
                point--;
            }
        } else {
            if (!input.incrementToken())
                return false;
            Token token = new Token(termAtt.toString(), offsetAtt.startOffset(), offsetAtt.endOffset());
            tokens.add(token);
            point = tokens.size();
        }
        return true;
    }

    @Override
    public void reset() throws IOException {
        super.reset();
        tokens.clear();
        point = 0;
    }

    class Token {
        private String buff;
        private int startOffset;
        private int endOffset;

        Token(String buff, int startOffset, int endOffset) {
            this.buff = buff;
            this.endOffset = endOffset;
            this.startOffset = startOffset;
        }

        public String getBuff() {
            return buff;
        }

        public int getStartOffset() {
            return startOffset;
        }

        public int getEndOffset() {
            return endOffset;
        }
    }
}
