insert into users(email, name, birth_date, password, is_deleted)
values ('user1@email.net',
        'User 1',
        '2000-01-01 00:00:01',
        '$2a$10$jydBHFniq4mHx.c1X.lTX.NuJB61NZslnERDOL6m8qrD2XemYvI2a',
        false);

insert into accounts(value, currency_title, is_deleted)
values (1.0,
        'Ruble',
        false);

insert into accounts(value, currency_title, is_deleted)
values (2.0,
        'Dollar',
        false);

insert into accounts(value, currency_title, is_deleted)
values (3.0,
        'Yen',
        false);

insert into user_accounts(user_id, account_id, is_deleted)
values (1,
        1,
        false);

insert into user_accounts(user_id, account_id, is_deleted)
values (1,
        2,
        false);

insert into user_accounts(user_id, account_id, is_deleted)
values (1,
        3,
        false);


