package org.bear.commonservice.spam.dfa;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 基于前缀树的高效字符串查找算法，支持干扰字符的过滤
 * <p/>
 * 
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-03-19
 */
public class TrieTree {
	private static final char[] MOLEST_CHARS = { ' ', '　', '*', '-', '_', '|',
			'[', ':', '(', ')' };
	private TrieNode rootNode = new TrieNode('\0');
	private Set<Character> molestChars = new HashSet<Character>();

	public TrieTree(String... words) {
		this(null, words);
	}

	public TrieTree(char[] molestChars, String... words) {
		for (char c : MOLEST_CHARS) {
			this.molestChars.add(c);
		}
		if (molestChars != null) {
			for (char c : molestChars) {
				this.molestChars.add(c);
			}
		}
		if (words != null) {
			for (String word : words) {
				addWord(word);
			}
		}
	}

	public void addWord(String word) {
		if (StringUtils.isBlank(word))
			return;
		TrieNode node = rootNode;
		TrieNode childNode;
		char[] words = word.trim().toCharArray();
		for (int i = 0; i < words.length; i++) {
			char c = words[i];
			childNode = findNode(node, c);
			if (childNode == null) {
				childNode = new TrieNode(c, i == word.length() - 1 ? 1 : 0);
				node.nodes.add(childNode);
			}
			node = childNode;
		}
	}

	public boolean contains(String content) {
		return !search(content, true).isEmpty();
	}

	public String replace(String content) {
		return replace(content, '*');
	}

	public String replace(String content, char mask) {
		if (content == null || "".equals(content)) {
			return content;
		}
		char[] chars = content.toCharArray();
		for (Result r : search(content, false)) {
			for (int i = r.start; i <= r.end; i++) {
				chars[i] = mask;
			}
		}
		return new String(chars);
	}

	private List<Result> search(String content, boolean breakOnMatch) {
		List<Result> result = new ArrayList<Result>();
		char[] chars = content.toCharArray();
		TrieNode node = rootNode;
		TrieNode childNode;
		int start = -1, end;
		/** 记录空格个数*/
		int space = 0;
		int subSpace = 0;
		for (int i = 0, len = chars.length; i < len; i++) {
			childNode = findNode(node, chars[i]);
			if (childNode != null) {
				if (start == -1)
					start = i;
				node = childNode;
				subSpace = 0;
			} else {
				if (molestChars.contains(chars[i])) {
					space += 1; subSpace+=1;
					continue;
				}
				if (node.flag == 1 && start != -1) {
					/** 减去空格个数*/
					//end = i - 1 - space;
					end = i - 1 - subSpace;
					result.add(new Result(start, end));
					/** 减去空格归0*/
					space = 0;subSpace=0;
					if (breakOnMatch)
						return result;
				}
				TrieNode tmpNode = findNode(rootNode, chars[i]);
				if (tmpNode != null) {
					start = i;
					node = tmpNode;
				} else {
					node = rootNode;
					start = -1;
				}
			}
		}
		if (node.flag == 1 && start != -1) {
			/** 减去空格个数*/
			end = chars.length - 1 - space;
			result.add(new Result(start, end));
		}
		return result;
	}

	private TrieNode findNode(TrieNode node, char c) {
		List<TrieNode> nodes = node.nodes;
		for (TrieNode n : nodes)
			if (n.c == c)
				return n;
		return null;
	}

	public static void main(String[] args) throws Exception {
		String[] keywords = new String[] { "我草", "你妈", "XX", "我操", "gcd",
				"共产党", "tg", "土共" };
		TrieTree trieTree = new TrieTree(keywords);
		System.out.println(trieTree.replace("傻"));
	}
}
