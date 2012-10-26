package com.ngdb.web.pages.article.accessory;

import com.ngdb.Barcoder;
import com.ngdb.entities.article.Accessory;
import com.ngdb.entities.article.element.Note;
import com.ngdb.entities.article.element.Review;
import com.ngdb.entities.article.element.Tag;
import com.ngdb.web.Filter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

public class AccessoryView {

    @Persist("entity")
    private Accessory accessory;

    @Property
    private Note property;

    @Property
    private Tag tag;

    @Property
    private Review review;

    @Property
    private String value;

    @Property
    private Note note;

    @Inject
    private Barcoder barcoder;

    public void onActivate(Accessory accessory) {
        this.accessory = accessory;
    }

    public void onActivate(Accessory accessory, String prefix) {
        onActivate(accessory);
    }

    public Accessory onPassivate() {
        return accessory;
    }

    public void setAccessory(Accessory accessory) {
        this.accessory = accessory;
    }

    public Accessory getAccessory() {
        return accessory;
    }

    public String getByOrigin() {
        return Filter.byOrigin.name();
    }

    public String getByPlatform() {
        return Filter.byPlatform.name();
    }

    public String getByPublisher() {
        return Filter.byPublisher.name();
    }

    public String getByReleaseDate() {
        return Filter.byReleaseDate.name();
    }

    public String getUpc() {
        return barcoder.toBarcodeBase64Image(accessory.getUpc());
    }

    public boolean getShowUpc() {
        return accessory.getUpc() != null && !"0000000000000".equals(accessory.getUpc());
    }

}
