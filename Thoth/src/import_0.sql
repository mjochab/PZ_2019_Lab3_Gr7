insert into role (Position) values ('Admin');
insert into role (Position) values ('Magazynier');
insert into role (Position) values ('Analityk');
insert into role (Position) values ('Sprzedawca');
insert into role (Position) values ('Logistyk');

INSERT INTO state (StateId, Name) VALUES (null, 'W realizacji');
INSERT INTO state (StateId, Name) VALUES (null, 'Oczekuje na transport');
INSERT INTO state (StateId, Name) VALUES (null, 'W transporcie');
INSERT INTO state (StateId, Name) VALUES (null, 'Oczekuje na potwierdzenie odbioru');
INSERT INTO state (StateId, Name) VALUES (null, 'Zrealizowane');
INSERT INTO state (StateId, Name) VALUES (64, 'Niedostepny');
INSERT INTO state (StateId, Name) VALUES (65, 'Mozliwy do wydania');
INSERT INTO state (StateId, Name) VALUES (66, 'Odebrane przez klienta');

insert into shop (ZipCode, City, Street, IsShop) values ('00-000', 'Default', 'Default shop', 1);

insert into user (FirstName, LastName, Login, Password, RoleId, State) values ('MAIN', 'ADMIN', 'admin', 'admin', '1', '1');

insert into user_shop (ShopId, UserId)  values ('1','1');