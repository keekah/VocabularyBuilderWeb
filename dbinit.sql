-- CREATE DATABASE vocabulary_words;

-- \c vocabulary_words

CREATE TABLE vocabulary_words (id SERIAL PRIMARY KEY, word TEXT NOT NULL, definition TEXT);

INSERT INTO vocabulary_words(word, definition) VALUES ('excoriate', 'to criticize harshly');
INSERT INTO vocabulary_words(word, definition) VALUES ('halcyon', 'denoting a period of time in the past that was idyllically happy and peaceful');
INSERT INTO vocabulary_words(word, definition) VALUES ('posterity', 'all future generations of people');
INSERT INTO vocabulary_words(word, definition) VALUES ('ad hoc', 'when necessary or needed');
INSERT INTO vocabulary_words(word, definition) VALUES ('fatuous', 'silly and pointless');
INSERT INTO vocabulary_words(word, definition) VALUES ('vignette', 'a brief evocative description, account, or episode');
INSERT INTO vocabulary_words(word, definition) VALUES ('hirsute', 'hairy');
INSERT INTO vocabulary_words(word, definition) VALUES ('ethos', 'the characteristic spirit of a culture, era, or community as manifested in its beliefs and aspirations');
INSERT INTO vocabulary_words(word, definition) VALUES ('metathesis', 'the transposition of sounds or letters in a word');
INSERT INTO vocabulary_words(word, definition) VALUES ('aphorism', 'a pithy observation that contains a general truth');
INSERT INTO vocabulary_words(word) VALUES ('nascent');
INSERT INTO vocabulary_words(word) VALUES ('panniers');

CREATE SEQUENCE hibernate_sequence START 1;

SELECT setval('hibernate_sequence', (SELECT max(id) FROM vocabulary_words));


CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    first_name TEXT,
    last_name TEXT,
    display_name TEXT,
    email TEXT NOT NULL,
    is_email_verified BOOLEAN,
    firebase_id TEXT NOT NULL,
    provider_id TEXT,
    account_created_date_time TIMESTAMPTZ NOT NULL,
    last_sign_in_date_time TIMESTAMPTZ NOT NULL
);

INSERT INTO users (email, firebase_id, account_created_date_time, last_sign_in_date_time) VALUES ('kika.wingert@gmail.com', 'firebaseid1', current_timestamp, current_timestamp);

INSERT INTO vocabulary_words (word, user_id) values ('nought', 1);
INSERT INTO vocabulary_words (word, user_id) values ('harpy', 1);