package com.helloarron.tpandroid.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arron on 16/4/21.
 */
public class ParsePoetry {

    public static List<String> parsePoetryByFullStop(String content) {
        List<String> rows = new ArrayList<>();

        String[] lines = content.split("[\\u3002]+");
        for (int i = 0; i < lines.length; i++) {
            rows.add(lines[i] + "。");
        }
        return rows;
    }

    public static List<String> parsePoetryByComma(String content) {
        List<String> rows = new ArrayList<>();

        String[] lines = content.split("[\\u3002]+");
        for (int i = 0; i < lines.length; i++) {
            String[] items = lines[i].split("[\\uFF0C]+");
            for (int j = 0; j < items.length; j++) {
                if (items.length - 1 == j) {
                    rows.add(items[j] + "。");
                } else {
                    rows.add(items[j] + "，");
                }
            }
        }
        return rows;
    }
}
