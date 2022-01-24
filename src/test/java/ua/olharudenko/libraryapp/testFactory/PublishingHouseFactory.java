package ua.olharudenko.libraryapp.testFactory;

import ua.olharudenko.libraryapp.models.PublishingHouse;

public class PublishingHouseFactory {

    private final static Long ID = 10L;

    private final static String EMAIL = "email@gmail.com";

    private final static String PHONE = "380675559988";

    private final static Long FILE_REFERENCE = null;

    public PublishingHouseFactory() {
    }

    public static PublishingHouse createPublishingHouse(){
        return new PublishingHouse(ID, EMAIL, PHONE);
    }
}
