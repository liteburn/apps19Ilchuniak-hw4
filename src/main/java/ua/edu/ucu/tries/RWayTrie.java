package ua.edu.ucu.tries;

import ua.edu.ucu.utils.Queue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

public class RWayTrie implements Trie {


    private String str;
    private HashSet<RWayTrie> continues;
    private RWayTrie prev;

    private RWayTrie(String c, RWayTrie previous){
        str = c;
        continues = new HashSet<>();
        prev = previous;
    }

    public RWayTrie() {
        str = "";
        continues = new HashSet<>();
        prev = null;
    }



   private RWayTrie addLetter(String smth){
        RWayTrie toAdd = new RWayTrie(smth, this);
        this.continues.add(toAdd);
        return toAdd;
   }

   private RWayTrie getNext(String elem){

       if (elem.length() == 1 && elem.equals(".")){
           for (RWayTrie rWay: continues){
               if (rWay.str.equals(".")){
                   return rWay;
               }
           }
       }
        for (RWayTrie toCont: continues){
            if ((!toCont.str.equals(".")) &&toCont.str.charAt(elem.length() - 1) == elem.charAt(elem.length() - 1)){

                return toCont;
            }
        }
        return null;
   }
    @Override
    public void add(Tuple t) {
        RWayTrie rWay = this;
        for(int i = 0; i < t.weight; i++){
            String a = t.term.substring(0, i+ 1);
            RWayTrie prevRWay = rWay;
            rWay = rWay.getNext(a);
            if (rWay == null) {
                rWay = prevRWay;
                rWay = rWay.addLetter(a);
            }
        }
        rWay.addLetter(".");
    }

    private RWayTrie contain(String word){
        if (word.length() == 1 && word.equals(".")){
            for (RWayTrie rWay: continues){
                if (rWay.str.equals(".")){
                    return rWay;
                }
            }
        }
        RWayTrie rWay = this;
        for(int i = 0; i < word.length(); i++){
            String a = word.substring(0, i+ 1);
            rWay = rWay.getNext(a);
            if (rWay == null) {
                return null;
            }
        }
        return rWay;
    }
    @Override
    public boolean contains(String word) {
        RWayTrie rWay = contain(word);
        if (rWay == null){
            return false;
        }
        return rWay.contains(".");
    }

    public boolean contain5(String word) {
        RWayTrie rWay = contain(word);
        return rWay != null;
    }

    @Override
    public boolean delete(String word) {
        if (contains(word)){
            RWayTrie rWay = this;
            for(int i = 0; i < word.length(); i++){
                String a = word.substring(0, i+ 1);
                rWay = rWay.getNext(a);
            }
            if (rWay.contains(".")){
                RWayTrie toDel = rWay.getNext(".");
                while (!rWay.str.equals("") ||rWay.continues.size() == 1){
                    toDel = rWay;
                    rWay = rWay.prev;
                }
                rWay.continues.remove(toDel);
            }
            return true;
        }
        return false;
    }

    private void getQueue(RWayTrie tree, Queue queue){
        for (RWayTrie tre: tree.continues){
            if (tre.str.equals(".")){
                queue.enqueue(tree.str);
            }
            else{
                getQueue(tre, queue);
            }
        }
    }
    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {

        Queue queue = new Queue();
        if (contain5(s) || s.equals("")) {
            RWayTrie rWay = this;
            for(int i = 0; i < s.length(); i++){
                String a = s.substring(0, i+ 1);
                rWay = rWay.getNext(a);
            }
            getQueue(rWay, queue);
            Object[] toStr = queue.toArray();
            String[] strings = Arrays.copyOf(toStr, toStr.length, String[].class);
            Arrays.sort(strings, Comparator.comparingInt(String::length));
            return () -> Arrays.stream(strings).iterator();
        }

        else {
            String[] strings = {};
            return () -> Arrays.stream(strings).iterator();
        }
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

}
