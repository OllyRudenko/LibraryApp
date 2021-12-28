INSERT INTO users (id, first_name, last_name, role, email, password, phone, adress)
VALUES (1, 'Tom', 'Soyer', 'ADMIN', 'soyer@gmail.com', 'admiN2022', '380966876555', 'Kharkiv'),
       (2, 'Tamara', 'Gray', 'LIBRARIAN', 'gray@gmail.com', 'libre2022', '380506876500', 'Kharkiv'),
       (3, 'Tonny', 'Barashkov', 'VISITOR', 'barashkov@gmail.com', 'visit2022', '380630076500', 'Kharkiv'),
       (4, 'Кирило', 'Чугай', 'VISITOR', 'chugay@gmail.com', 'chu2022', '380971116500', 'Kyiv');


INSERT INTO locales (locale, full_name)
VALUES ('UK', 'ukrainian'),
       ('EN', 'english'),
       ('RU', 'russian');


INSERT INTO authors (id, full_name, file_reference_id)
VALUES (1, 'O. Henry', null),
       (2, 'Григорій Сковорода', null),
       (3, 'Михаил Булгаков', null),
       (4, 'Джордж Мартин', null);


INSERT INTO localized_authors (locale, author_id, full_name, biografy)
VALUES ('EN', 1, 'O. Henry', 'biografy O. Henry'),
       ('UK', 2, 'Григорій Сковорода', 'Біографія Григория Сковороди'),
       ('RU', 3, 'Михаил Булгаков', 'Биография Михаила Булгакова'),
       ('UK', 4, 'Джордж Мартін', 'Біографія Джорджа Мартіна');


INSERT INTO publishing_houses (id, file_reference_id, email, phone)
VALUES (1, null, 'soyer@gmail.com', '380965550101'),
       (2, null, 'gray@gmail.com', '380506876511'),
       (3, null, 'barashkov@gmail.com', '380630076512'),
       (4, null, 'chugay@gmail.com', '380971116513');


INSERT INTO localized_publishing_houses (locale, publishing_house_id, city, adress, name_house)
VALUES ('EN', 1, 'Kharkiv', 'adress', 'Folio'),
       ('RU', 2, 'Харьков', 'адрес', 'Самиздат'),
       ('UK', 3, 'Харків', 'адреса', 'Харків'),
       ('UK', 4, 'Одеса', 'адреса', 'Морячок');


INSERT INTO books (id, title, author_id, description, publish_locale, publish_house_id, issue_date, items)
VALUES (1, '100 Selected Stories of O. Henry', 1, 'description', 'EN', 1, 2021, 10),
       (2, 'Байки харківські', 2, 'description', 'UK', 3, 2020, 20),
       (3, 'Дни Турбиных', 3, 'description', 'RU', 2, 2021, 8),
       (4, 'Гра престолів', 4, 'description', 'UK', 4, 2021, 50);