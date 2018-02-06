package com.mcgwinds.middleware.cache.core.cachekey;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by mcg on 2018/2/4.
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
                value = "Nil";
            }else {
                value = value == this ? "(this Map)" : value;
            }
            sb.append(value);
            if (!i.hasNext())
                return sb.append('}').toString();
            sb.append('_');
        }
    }
}

