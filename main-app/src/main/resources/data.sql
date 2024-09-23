INSERT INTO api_users (id, username, password)
VALUES (1, 'admin', '$2a$12$5BW0nakg0ZNhdjqguxRmvOHc95h4MUzxEeNEAv66wdydSOLLXe3p2'),
       (2, 'user', '$2a$12$EzJbcQFikB3qlqF0zaH5PeJXwPO9bjZK4D8vnBNXZt8Fsv059t3cG');

insert into USER_ENTITY_ROLES(USER_ENTITY_ID, ROLES)
values (1, 'ROLE_ADMIN'),
       (2, 'ROLE_USER');