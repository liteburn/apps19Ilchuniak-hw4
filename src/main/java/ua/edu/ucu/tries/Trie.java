package ua.edu.ucu.tries;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public interface Trie {

    public void add(Tuple word);

    public boolean contains(String word);

    public boolean delete(String word);

    public Iterable<String> words();

    public Iterable<String> wordsWithPrefix(String pref);
    
    public int size();
}
