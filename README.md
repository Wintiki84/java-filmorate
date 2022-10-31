FILMORATE 

---

Cоциальная сеть.

---

## Модель базы данных

![model](https://github.com/Wintiki84/java-filmorate/blob/add-friends-likes/Diagram.png)

#### Пример запросов:

* Получение всех фильмов

``` SQL
SELECT *
FROM films;
```

* Получение всех пользователей

``` SQL
SELECT *
FROM users;
```

* Получение топ N популярных фильмов

``` SQL
SELECT *
FROM films AS f
LEFT JOIN film_likes AS fl ON f.film_id = fl.film_id
GROUP BY f.name
ORDER BY COUNT(fl.user_id) DESC
LIMIT {N}; 
```

* Получение списка общих друзей с другим пользователем

``` SQL
SELECT *
FROM users 
WHERE EXISTS
    (SELECT *
     FROM status_friends
     WHERE status_friends.user_id = {first_id}
       AND status_friends.status = true) 
```
