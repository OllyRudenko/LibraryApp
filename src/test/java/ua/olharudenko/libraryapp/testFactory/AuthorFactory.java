package ua.olharudenko.libraryapp.testFactory;

import ua.olharudenko.libraryapp.models.Author;
import ua.olharudenko.libraryapp.models.FileReference;

public class AuthorFactory {

    private static final Long ID = 10L;

    private static final String FULL_NAME = "Name Surname";

    private static final FileReference PHOTO = null;

    public AuthorFactory() {
    }

    public static Author createAuthor() {
        return new Author(ID, FULL_NAME, PHOTO);
    }

    public static Author createAuthorWithoutId() {
        return new Author(FULL_NAME, PHOTO);
    }

    public static Author createAuthor(String fullName) {
        return new Author(ID, fullName, PHOTO);
    }
}
