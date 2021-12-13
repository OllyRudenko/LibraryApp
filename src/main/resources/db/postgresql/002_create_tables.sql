DROP TABLE IF EXISTS public.users, public.books, public.orders, public.penalties, public.orders_users, public.penalties_users;
DROP TYPE IF EXISTS role, order_status;

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
    ordered BOOLEAN NOT NULL
)
WITH (
    OIDS = FALSE
);

CREATE TYPE order_status AS ENUM ('UNCONFIRMED', 'SUBSCRIPTION', 'READING_ROOM');
CREATE TABLE public.orders(
    id BIGSERIAL PRIMARY KEY,
    book_id bigint NOT NULL,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE ON UPDATE CASCADE,
    user_id bigint NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
    order_status order_status,
    taked_date DATE NOT NULL,
    return_date DATE NOT NULL,
    amount_days integer NOT NULL

)
WITH (
    OIDS = FALSE
);
-- todo TIMESTAMP

CREATE TABLE public.penalties(
    id BIGSERIAL PRIMARY KEY,
    user_id bigint NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
    order_id bigint NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE ON UPDATE CASCADE,
    summ  DOUBLE PRECISION NOT NULL
)
WITH (
    OIDS = FALSE
);

ALTER TABLE public.orders
ADD COLUMN penalty_id bigint,
ADD CONSTRAINT fk_orders_penalties FOREIGN KEY (penalty_id)
REFERENCES penalties (id) ON DELETE CASCADE;

CREATE TABLE public.orders_users(
    order_id bigint NOT NULL,
    user_id bigint NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE  ON UPDATE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE  ON UPDATE CASCADE,
    UNIQUE (order_id, user_id)
)
WITH (
    OIDS = FALSE
);

CREATE TABLE public.penalties_users(
    penalty_id bigint NOT NULL,
    user_id bigint NOT NULL,
    FOREIGN KEY (penalty_id) REFERENCES penalties(id) ON DELETE CASCADE  ON UPDATE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE  ON UPDATE CASCADE,
    UNIQUE (penalty_id, user_id)
)
WITH (
    OIDS = FALSE
);