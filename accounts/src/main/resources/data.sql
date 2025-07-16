insert into users(email, name, birth_date, password, is_deleted)
values ('user1@email.net',
        'User 1',
        '2000-01-01 00:00:01',
        '$2a$10$jydBHFniq4mHx.c1X.lTX.NuJB61NZslnERDOL6m8qrD2XemYvI2a',
        false);

insert into accounts(user_id, value, currency_name, is_exists)
values (1,
        100000.0,
        'RUB',
        true);

insert into accounts(user_id, value, currency_name, is_exists)
values (1,
        0.0,
        'USD',
        false);

insert into accounts(user_id, value, currency_name, is_exists)
values (1,
        0.0,
        'CNY',
        false);


