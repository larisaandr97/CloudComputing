drop table cart;
-- Insert role
insert into roles(name) values ('ROLE_USER');
insert into roles(name) values ('ROLE_ADMIN');

-- Insert users
insert into users(id,username, enabled, password, role_id) values (1,'user', true, '$2a$08$wgwoMKfYl5AUE9QtP4OjheNkkSDoqDmFGjjPE2XTPLDe9xso/hy7u', 1);
insert into users(id,username, enabled, password, role_id) values (2,'user2', true, '$2a$08$wgwoMKfYl5AUE9QtP4OjheNkkSDoqDmFGjjPE2XTPLDe9xso/hy7u', 1);
insert into users(id,username, enabled, password, role_id) values (3,'admin2021', true, '$2a$08$wgwoMKfYl5AUE9QtP4OjheNkkSDoqDmFGjjPE2XTPLDe9xso/hy7u', 2);

-- Insert bank accounts
insert into bankaccount( accountNumber,balance,cardNumber,user_id) values('4343434',120,'4217737771886809',1);
insert into bankaccount(accountNumber,balance,cardNumber,user_id) values('43445462',750,'4217737771886877',1);
