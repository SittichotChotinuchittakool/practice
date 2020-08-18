package com.example.wongnai.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public abstract class InvertedIndex<K, V, Q> {
    public static InvertedIndex getInstance() {
        return null;
    }

    public abstract Map<K, List<V>> getHashMap();

    public abstract void createHashMap();

    public abstract void start();

    public abstract void addQueue(Q q);

    public abstract Queue<Q> getQueue();

    public List<V> getIndexByKey(K key) {
        if (!getHashMap().containsKey(key)) {
            return new ArrayList<>();
        }
        return getHashMap().get(key);
    }

    public void setIndexByKey(K key, V index) {
        List<V> indexes;
        if (!getHashMap().containsKey(key)) {
            indexes = new ArrayList<>();
        } else {
            indexes = getHashMap().get(key);
        }
        indexes.add(index);
        getHashMap().put(key, indexes);
    }

    public void setIndexByKey(K key, List<V> index) {
        List<V> indexes;
        if (!getHashMap().containsKey(key)) {
            indexes = new ArrayList<>();
        } else {
            indexes = getHashMap().get(key);
        }
        indexes = index;
        getHashMap().put(key, indexes);
    }
}
