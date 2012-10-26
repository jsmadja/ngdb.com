package com.ngdb.web.pages.article.hardware;

import com.ngdb.Barcoder;
import com.ngdb.entities.article.Hardware;
import com.ngdb.entities.article.element.Note;
import com.ngdb.entities.article.element.Review;
import com.ngdb.entities.article.element.Tag;
import com.ngdb.web.Filter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

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

    @Inject
    private Barcoder barcoder;

    public void onActivate(Hardware hardware) {
        this.hardware = hardware;
    }
    public void onActivate(Hardware hardware, String suffix) {
        onActivate(hardware);
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
        return barcoder.toBarcodeBase64Image(hardware.getUpc());
    }

    public boolean getShowUpc() {
        return hardware.getUpc() != null && !"0000000000000".equals(hardware.getUpc());
    }

}
