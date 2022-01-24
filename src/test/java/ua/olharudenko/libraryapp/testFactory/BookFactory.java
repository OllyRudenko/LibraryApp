package ua.olharudenko.libraryapp.testFactory;

import ua.olharudenko.libraryapp.enums.Locale;
import ua.olharudenko.libraryapp.models.Book;
import ua.olharudenko.libraryapp.models.LocalizedAuthor;

public class BookFactory {

    private static final Long ID = 10L;

    private static final String TITLE = "Title";

    private static final LocalizedAuthor AUTHOR = LocalizedAuthorFactory.createLocalizedAuthor();

    private static final String DESCRIPTION = "Description";

    private static final Locale PUBLISHING_LOCALE = Locale.EN;

    private static final Long PUBLISHING_HOUSE_ID = 10L;

    private static final int ISSUE_DATE = 2022;

    private static final int ITEMS =20;

    public BookFactory() {
    }

    public static Book createBook(){
        return new Book(ID, TITLE, AUTHOR, DESCRIPTION, PUBLISHING_LOCALE, PUBLISHING_HOUSE_ID, ISSUE_DATE, ITEMS);
    }
}
