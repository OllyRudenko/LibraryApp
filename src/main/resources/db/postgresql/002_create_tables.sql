DROP TABLE IF EXISTS public.users, public.books, public.orders;
DROP TYPE IF EXISTS role, order_status, bill_status;

 GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO admin;
 GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO admin;

CREATE TYPE role AS ENUM ('UNCONFIRMED', 'SUBSCRIPTION', 'READING_ROOM');
CREATE TABLE public.users(
    id BIGSERIAL PRIMARY KEY,
    first_name character varying(100) NOT NULL,
    last_name character varying(100) NOT NULL,
    role role NOT NULL,
    email character varying(50) NOT NULL,
    password character varying(100) NOT NULL,
    phone character varying(12) NOT NULL,
    adress character varying(300) NOT NULL
)
WITH (
    OIDS = FALSE
);

CREATE TABLE public.books
(
    id BIGSERIAL PRIMARY KEY,
    title character varying(200) NOT NULL,
    author character varying(200) NOT NULL,
    issuing_organization character varying(200) NOT NULL,
    issue_date integer NOT NULL,
    items integer NOT NULL
)
WITH (
    OIDS = FALSE
);

CREATE TYPE order_status AS ENUM ('UNCONFIRMED', 'SUBSCRIPTION', 'READING_ROOM');
CREATE TYPE bill_status AS ENUM ('PAID', 'UNPAID');
CREATE TABLE public.orders(
    id BIGSERIAL PRIMARY KEY,
    book_id bigint NOT NULL,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE ON UPDATE CASCADE,
    quantity integer NOT NULL,
    user_id bigint NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
    order_status order_status,
    taked_date DATE NOT NULL,
    return_date DATE NOT NULL,
    bill integer,
    bill_status bill_status
)
WITH (
    OIDS = FALSE
);
-- todo TIMESTAMP
