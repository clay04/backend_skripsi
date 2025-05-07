package com.sms.uk.skripsi.base.models;

import lombok.Getter;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Getter
public class KeyValuePair<K, V> {
    private final K key;
    private final V value;

    private KeyValuePair(K key, V value) {
        Assert.notNull(key, "Key must not be null");
        Assert.notNull(value, "Value must not be null");
        this.key = key;
        this.value = value;
    }

    public static <K, V> KeyValuePair<K, V> of(K key, V value) {
        return new KeyValuePair<>(key, value);
    }

    public static <K, V> Collector<KeyValuePair<K, V>, ?, Map<K, V>> toMap() {
        return Collectors.toMap(KeyValuePair::getKey, KeyValuePair::getValue);
    }

    public String toString() {
        return String.format("%s->%s", this.key, this.value);
    }
}