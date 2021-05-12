INSERT INTO public.book (id, is_available, isbn, number_of_pages, title)
VALUES (1, true, '9789634195764', 538, 'Hyperion');
INSERT INTO public.book (id, is_available, isbn, number_of_pages, title)
VALUES (2, true, '9789634194347', 714, 'Terror');
INSERT INTO public.book (id, is_available, isbn, number_of_pages, title)
VALUES (3, true, '9789634335061', 240, 'Istenek és emberek');


INSERT INTO public.author (id, author_first_name, author_last_name)
VALUES (1, 'Dan', 'Simmons');
INSERT INTO public.author (id, author_first_name, author_last_name)
VALUES (2, 'András', 'Kepes');
INSERT INTO public.author (id, author_first_name, author_last_name)
VALUES (3, 'Margaret Eleanor', 'Atwood');
INSERT INTO public.author (id, author_first_name, author_last_name)
VALUES (4, 'János', 'Háy');
INSERT INTO public.author (id, author_first_name, author_last_name)
VALUES (5, 'Jones', 'Dan');
INSERT INTO public.author (id, author_first_name, author_last_name)
VALUES (6, 'Richard', 'Dawkins');
INSERT INTO public.author (id, author_first_name, author_last_name)
VALUES (7, 'Jo', 'Nesbo');
INSERT INTO public.author (id, author_first_name, author_last_name)
VALUES (8, 'Borisz', 'Paszternak');
INSERT INTO public.author (id, author_first_name, author_last_name)
VALUES (9, 'Milan', 'Kundera');
INSERT INTO public.author (id, author_first_name, author_last_name)
VALUES (10, 'László', 'Mérő');


INSERT INTO public.book_author (book_id, author_id)
VALUES (1, 1);
INSERT INTO public.book_author (book_id, author_id)
VALUES (2, 1);
INSERT INTO public.book_author (book_id, author_id)
VALUES (3, 2);