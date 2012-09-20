package com.ngdb.services;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.cn.smart.SentenceTokenizer;
import org.apache.solr.analysis.BaseTokenizerFactory;

import java.io.Reader;
import java.util.Map;

public class SentenceTokenizerFactory extends BaseTokenizerFactory {

    @Override
    public void init(Map<String,String> args) {
        super.init(args);
    }

    public Tokenizer create(Reader input) {
        return new SentenceTokenizer(input);
    }
}