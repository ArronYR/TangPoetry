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
            String row = lines[i] + "。";
            if (row.contains("？")) {
                String[] rowArr = row.split("[\\uFF1F]+");
                for (int j = 0; j < rowArr.length - 1; j++) {
                    rows.add(rowArr[j] + "？");
                }
                rows.add(rowArr[rowArr.length - 1]);
            } else {
                rows.add(row);
            }
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
