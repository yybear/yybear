package org.bear.commonservice.spam.dfa;

import java.util.ArrayList;
import java.util.List;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-03-19
 */
class TrieNode {
    public char c;
    public int flag = 0;
    public List<TrieNode> nodes = new ArrayList<TrieNode>();

    public TrieNode(char c, int flag) {
        this.c = c;
        this.flag = flag;
    }

    public TrieNode(char c) {
        this(c, 0);
    }
}
