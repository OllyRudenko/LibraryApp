package ua.olharudenko.libraryapp.testFactory;

import ua.olharudenko.libraryapp.enums.Locale;
import ua.olharudenko.libraryapp.models.FileReference;
import ua.olharudenko.libraryapp.models.LocalizedAuthor;

public class LocalizedAuthorFactory {

    private final static Long AUTHOR_ID = 10L;

    private final static String FULL_NAME = "Name Surname";

    private final static Locale LOCAL = Locale.EN;

    private final static String BIOGRAFY = "Biography";

    private final static FileReference PHOTO = null;

    public LocalizedAuthorFactory() {
    }

    public static LocalizedAuthor createLocalizedAuthor() {
        return new LocalizedAuthor(AUTHOR_ID, FULL_NAME, LOCAL, BIOGRAFY, PHOTO);
    }

    public static LocalizedAuthor createLocalizedAuthor(String fullName) {
        return new LocalizedAuthor(AUTHOR_ID, fullName, LOCAL, BIOGRAFY, PHOTO);
    }
}
