package ua.olharudenko.libraryapp.models;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ua.olharudenko.libraryapp.enums.Locale;

import java.util.Objects;

public class LocalizedPublishingHouse {

    private Locale locale;

    private Long publishingHouseId;

    private String city;

    private String adress;

    private String nameHouse;

    public LocalizedPublishingHouse() {
    }

    public LocalizedPublishingHouse(Locale locale, String city, String adress, String nameHouse) {
        this.locale = locale;
        this.city = city;
        this.adress = adress;
        this.nameHouse = nameHouse;
    }

    public LocalizedPublishingHouse(Locale locale, Long publishingHouseId, String city, String adress, String nameHouse) {
        this.locale = locale;
        this.publishingHouseId = publishingHouseId;
        this.city = city;
        this.adress = adress;
        this.nameHouse = nameHouse;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Long getPublishingHouseId() {
        return publishingHouseId;
    }

    public void setPublishingHouseId(Long publishingHouseId) {
        this.publishingHouseId = publishingHouseId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getNameHouse() {
        return nameHouse;
    }

    public void setNameHouse(String nameHouse) {
        this.nameHouse = nameHouse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocalizedPublishingHouse that = (LocalizedPublishingHouse) o;
        return locale == that.locale && Objects.equals(publishingHouseId, that.publishingHouseId) && Objects.equals(city, that.city) && Objects.equals(adress, that.adress) && Objects.equals(nameHouse, that.nameHouse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locale, publishingHouseId, city, adress, nameHouse);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("locale", locale)
                .append("publishingHouseId", publishingHouseId)
                .append("city", city)
                .append("adress", adress)
                .append("nameHouse", nameHouse)
                .toString();
    }
}
