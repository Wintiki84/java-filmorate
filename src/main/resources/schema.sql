CREATE TABLE IF NOT EXISTS genre
(
    genre_id INTEGER NOT NULL,
    name     varchar(50),
    PRIMARY KEY (genre_id)
);

CREATE TABLE IF NOT EXISTS film
(
    film_id       INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    film_name     VARCHAR(50) NOT NULL,
    description   VARCHAR(1000),
    release_date  DATE,
    duration_film INTEGER,
    rate          INTEGER,
    mpa_id        INTEGER
);

CREATE TABLE IF NOT EXISTS mpa
(
    mpa_id   INTEGER NOT NULL,
    mpa_name VARCHAR(50),
    PRIMARY KEY (mpa_id)
);

create table if not exists genre_and_film
(
    film_id  int references film (film_id) ON DELETE CASCADE,
    genre_id int references genre (genre_id) ON DELETE CASCADE,
    PRIMARY KEY (film_id, genre_id)
);

CREATE TABLE IF NOT EXISTS users
(
    user_id    INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    email      VARCHAR(50) NOT NULL,
    login      VARCHAR(50) NOT NULL,
    user_name  VARCHAR(50),
    birth_date DATE
);

CREATE TABLE IF NOT EXISTS likes
(
    film_id INTEGER REFERENCES film (film_id) ON DELETE CASCADE,
    user_id INTEGER REFERENCES users (user_id) ON DELETE CASCADE,
    PRIMARY KEY (film_id, user_id)
);

CREATE TABLE IF NOT EXISTS friends
(
    user_id   INTEGER REFERENCES users (user_id) ON DELETE CASCADE,
    friend_id INTEGER REFERENCES users (user_id) ON DELETE CASCADE,
    approval  INTEGER,
    PRIMARY KEY (user_id, friend_id)
);
