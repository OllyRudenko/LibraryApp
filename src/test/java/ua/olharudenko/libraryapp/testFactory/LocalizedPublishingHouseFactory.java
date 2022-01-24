package ua.olharudenko.libraryapp.testFactory;

import ua.olharudenko.libraryapp.enums.Locale;
import ua.olharudenko.libraryapp.models.LocalizedPublishingHouse;

public class LocalizedPublishingHouseFactory {

    private final static Locale LOCALE = Locale.EN;

    private final static Long PUBLISHING_HOUSE_ID = 10L;

    private final static String CITY = "City";

    private final static String ADRESS = "ADRESS";

    private final static String NAME_HOUSE = "Name House";

    public LocalizedPublishingHouseFactory() {
    }

    public static LocalizedPublishingHouse createLocalizedPublishingHouse() {
        return new LocalizedPublishingHouse(LOCALE, PUBLISHING_HOUSE_ID, CITY, ADRESS, NAME_HOUSE);
    }
}
