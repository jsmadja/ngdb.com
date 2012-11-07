package com.ngdb;

import java.util.Set;
import java.util.TreeSet;

public class TagSplitter {

    public Set<String> extractTags(String search) {
        Set<String> tags = new TreeSet<String>();
        String[] split = search.split(",");
        for (String tagsSplitByVirgule : split) {
            String[] tagsToAdd = tagsSplitByVirgule.split(";");
            for (String tag : tagsToAdd) {
                tags.add(tag.trim());
            }
        }
        return tags;
    }

}
