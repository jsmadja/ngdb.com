package com.ngdb.entities;

import com.ngdb.services.SentenceTokenizerFactory;
import org.apache.solr.analysis.ASCIIFoldingFilterFactory;
import org.apache.solr.analysis.LowerCaseFilterFactory;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.AnalyzerDefs;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Date;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

@MappedSuperclass
@XmlAccessorType(FIELD)
public abstract class AbstractEntity implements Serializable {

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "modification_date", nullable = false)
    private Date modificationDate;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public AbstractEntity() {
        creationDate = modificationDate = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        modificationDate = new Date();
    }

    public Long getId() {
        return id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

}
