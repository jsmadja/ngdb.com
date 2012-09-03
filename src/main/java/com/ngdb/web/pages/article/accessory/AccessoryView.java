package com.ngdb.web.pages.article.accessory;

import com.ngdb.BarcodeUtil;
import com.ngdb.entities.article.Accessory;
import com.ngdb.entities.article.element.Note;
import com.ngdb.entities.article.element.Review;
import com.ngdb.entities.article.element.Tag;
import com.ngdb.web.Filter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

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

    public void onActivate(Accessory accessory) {
        this.accessory = accessory;
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
        return BarcodeUtil.toBarcodeBase64Image(accessory.getUpc());
    }

}
