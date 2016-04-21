package com.helloarron.tpandroid;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void split_poetry_content2line() throws Exception {
        String content = "床前明月光，疑是地上霜。举头望明月，低头思故乡。";
        System.out.println(content);
        List<String> contents = new ArrayList<>();

        String[] lines = content.split("[\\u3002]+");
        for (int i = 0; i < lines.length; i++) {
            System.out.println(i+":"+lines[i]);
            String[] items = lines[i].split("[\\uFF0C]+");
            for (int j = 0; j < items.length; j++) {
                if (items.length-1 == j){
                    contents.add(items[j]+"。");
                }else{
                    contents.add(items[j]+"，");
                }
            }
        }

        for (String item: contents) {
            System.out.println(":"+item);
        }
    }
}