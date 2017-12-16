package com.mcgwinds.middleware.cache.cachekey.keymanager;

import com.mcgwinds.middleware.cache.util.Constants;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by mcg on 2017/12/16.
 */
public class KeyMap<K, V> extends TreeMap {
    @Override
    public String toString() {
        Iterator<Map.Entry<K, V>> i = entrySet().iterator();
        if (!i.hasNext())
            return "{}";

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (; ; ) {
            Map.Entry<K, V> e = i.next();
            Object value = e.getValue();
            if (value == null) {
                value = Constants.Nil;
            }
            else {
                value = value == this ? "(this Map)" : value;
            }
            sb.append(value);
            if (!i.hasNext())
                return sb.append('}').toString();
            sb.append('_');
        }
    }
}
