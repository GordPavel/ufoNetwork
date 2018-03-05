﻿Работа с базой данных:

Таблицы данных.

    1.Расы
    Название каждой расы - уникальное значение, одновременно являющееся ключом.
    2. Планеты
    Название каждой планеты - уникальное значение, одновременно являющееся ключом.
    3. Пользователи
    Данная таблица отображает данные о различных пользователям нашей системы. Каждый пользователь имеет уникальный ID, являющийся ключом.
    Никнейм каждого пользователя уникален. 
    Пароли хранятся захешированными алгоритмом SHA-256 с солью по полю даты-времени регистрации. Это делается для того, 
    чтобы уменьшить вероятность коллизий одинаковых паролей и защитить данные пользователей. 
    Пол каждого пользователя ( строка, обозначающая пол, выбирается из списка уже имеющихся или вводится новая пользователем, 
    так как неизвестно, сколько различных полов может быть у других рас ).
    Возраст.
    Раса( внешний ключ на запись из таблицы рас ).
    Планета ( внешний ключ на запись из таблицы планет ).
    Дата-время регистрации в системе в формате dd.MM.yyyy HH:mm. времена из разных часовых поясов приводятся к одному GMT+0.
    Также может хранится png файл фотграфии пользователя.
    4. Группы
	Каждая группа имеет уникальный ID, являющийся ключом.
	У каждой группы имеется категория, выбираемая из списка при создании.
    Все пользователи могут записываться в 0..n разных групп, у каждой группы может быть 0...m различных пользователей. 
    Для реализации связи многие ко многим, введена дополнительная таблица person_group. 
    Ещё группа хранит ссылку на пользователя, который её создал и опционально png медиа файл.
    5. Пользователи могут писать сообщения в общий чат группы, который виден всем остальным участникам группы. 
    Каждое сообщение хранит автоинкрементирующийся целочисленный ключ, ссылку на отправителя, 
    ссылку на группу, в которую письмо было отправлено, дату-время отправки, текст сообщения и опциональный медиа файл.
	6. Категории групп. Каждая категория имеет уникальный ID, являющийся ключом, и текстовое описание/название категории.