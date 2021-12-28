drop TABLE IF EXISTS    public.users, public.file_references, public.authors, public.locales,
                        public.localized_author, public.publishing_houses, localized_publishing_houses,
                        public.books, public.orders;
--drop TYPE IF EXISTS role, order_status, bill_status, locale;

 GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO admin;
 GRANT USAGE, select ON ALL SEQUENCES IN SCHEMA public TO admin;

--create TYPE role AS ENUM ('ADMIN', 'LIBRARIAN', 'VISITOR');
create table public.users(
    id bigserial primary key,
    first_name character varying(100) not null,
    last_name character varying(100) not null,
--    role role not null,
    role character varying(9) not null,
    email character varying(50) not null unique,
    password character varying(100) not null,
    phone character varying(12) not null,
    adress character varying(300) not null
)
with (
    OIDS = FALSE
);

create table public.file_references(
    id bigserial primary key,
    file_name character varying(100) not null
)
with (
    OIDS = FALSE
);

create table public.authors(
    id bigserial primary key,
    full_name character varying(100) not null,
    file_reference_id bigint,
    foreign key (file_reference_id) references file_references(id) on delete cascade on update cascade
)
with (
    OIDS = FALSE
);

--create TYPE locale AS ENUM ('UK', 'EN', 'RU');
create table public.locales(
--    locale locale primary key unique,
    locale character varying(3) primary key unique,
    full_name character varying(200) not null
)
with (
    OIDS = FALSE
);

create table public.localized_authors(
--    locale locale not null,
    locale character varying not null,
    author_id bigint not null,
    foreign key (locale) references locales(locale) on delete cascade on update cascade,
    foreign key (author_id) references authors(id) on delete cascade on update cascade,
    primary key (locale, author_id),
    full_name character varying(200) not null,
    biografy character varying(1200) not null
)
with (
    OIDS = FALSE
);

create table public.publishing_houses(
    id bigserial primary key,
    file_reference_id bigint,
    foreign key (file_reference_id) references file_references(id) on delete cascade on update cascade,
    email character varying(50) NOT NULL,
    phone character varying(12) NOT NULL
)
with (
    OIDS = FALSE
);

create table public.localized_publishing_houses(
--    locale locale not null,
    locale character varying not null,
    publishing_house_id bigint not null,
    foreign key (locale) references locales(locale) on delete cascade on update cascade,
    foreign key (publishing_house_id) references publishing_houses(id) on delete cascade on update cascade,
    primary key (locale, publishing_house_id),
    city character varying(200) not null,
    adress character varying(300) NOT NULL,
    name_house character varying(200) not null
)
with (
    OIDS = FALSE
);

create table public.books
(
    id bigserial primary key,
    title character varying(200) not null,
    author_id bigint not null,
    foreign key (author_id) references authors(id) on delete cascade on update cascade,
    description character varying(2000) not null,
--    publish_locale locale NOT NULL,
    publish_locale character varying NOT NULL,
    publish_house_id bigint not null,
    FOREIGN KEY (publish_locale, publish_house_id) REFERENCES localized_publishing_houses
      (locale, publishing_house_id),
    issue_date integer not null,
    items integer not null
)
with (
    OIDS = FALSE
);

--create TYPE order_status AS ENUM ('UNCONFIRMED', 'SUBSCRIPTION', 'READING_ROOM');
--create TYPE bill_status AS ENUM ('PAID', 'UNPAID');
create table public.orders(
    id bigserial primary key,
    book_id bigint not null,
    foreign key (book_id) references books(id) on delete cascade on update cascade,
    quantity integer not null,
    user_id bigint not null,
    foreign key (user_id) references users(id) on delete cascade on update cascade,
--    order_status order_status,
    order_status character varying(12) not null,
    taked_date date not null,
    return_date date not null,
    bill integer,
--    bill_status bill_status
    bill_status character varying(6) not null
)
with (
    OIDS = FALSE
);