package com.ngdb.web.pages.article.hardware;

import com.ngdb.BarcodeUtil;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.Hardware;
import com.ngdb.entities.article.element.Note;
import com.ngdb.entities.article.element.Review;
import com.ngdb.entities.article.element.Tag;
import com.ngdb.web.Filter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

public class HardwareView {

    @Persist("entity")
    private Hardware hardware;

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

    public void onActivate(Hardware hardware) {
        this.hardware = hardware;
    }

    public Hardware onPassivate() {
        return hardware;
    }

    public void setHardware(Hardware hardware) {
        this.hardware = hardware;
    }

    public Hardware getHardware() {
        return hardware;
    }

    public String getByNgh() {
        return Filter.byNgh.name();
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
        return BarcodeUtil.toBarcodeBase64Image(hardware.getUpc());
    }

}
