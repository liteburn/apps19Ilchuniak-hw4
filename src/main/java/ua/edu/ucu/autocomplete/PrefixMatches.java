package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

import java.util.ArrayList;

/**
 * @author andrii
 */
public class PrefixMatches {

    private Trie trie;

    public PrefixMatches(Trie toTrie) {
        trie = toTrie;
    }

    public int load(String... strings) {
        int added = 0;
        for (String string : strings) {
            String[] str = string.split("\\s+");
            for (String elem : str) {
                trie.add(new Tuple(elem.toLowerCase(), elem.length()));
                added += 1;
            }
        }
        return added;
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    private ArrayList<String> toAdd(String pref, ArrayList<String> result, int k) {
        int cnt = 0;
        int len = 0;
        for (String elem : trie.wordsWithPrefix(pref)) {
            if (len != elem.length()) {
                len = elem.length();
                cnt += 1;
            }
            if (cnt > k && k > 0) {
                break;
            }
            result.add(elem);
        }
        return result;
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        ArrayList<String> result = new ArrayList<>();
        return toAdd(pref, result, 0);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        ArrayList<String> result = new ArrayList<>();
        return toAdd(pref, result, k);
    }

    public int size() {
        return trie.size();
    }
}
