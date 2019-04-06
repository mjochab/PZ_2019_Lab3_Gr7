
insert into role (Position) values ('Admin');
insert into role (Position) values ('Magazynier');
insert into role (Position) values ('Analityk');
insert into role (Position) values ('Sprzedawca');
insert into role (Position) values ('Logistyk');

insert into object (zipcode, city, street, isshop) values ('35-311', 'Rzeszów', 'Powstańców Warszawy', '1');
insert into object (zipcode, city, street, isshop) values ('32-336', 'Kraków', 'Warszawska', '1');
insert into object (zipcode, city, street, isshop) values ('36-006', 'Warszawa', 'Krakowska', '0');
insert into object (zipcode, city, street, isshop) values ('34-056', 'Poznań', 'Aleja Wolności', '1');



insert into user (FirstName, LastName, Login, Password, RoleId, State, ObjectId) values ('Adam', 'Kowalski', 'test1', 'test1', '1', '1', '1');

insert into user (FirstName, LastName, Login, Password, RoleId, State, ObjectId) values ('Bartosz', 'Nowacki', 'test2', 'test2', '2', '1', '2');

insert into user (FirstName, LastName, Login, Password, RoleId, State, ObjectId) values ('Tadeusz', 'Malinowski', 'test3', 'test3', '3', '1', '3');

insert into user (FirstName, LastName, Login, Password, RoleId, State, ObjectId) values ('Patryk', 'Nowakowski', 'test4', 'test4', '4', '1', '4');

insert into user (FirstName, LastName, Login, Password, RoleId, State, ObjectId) values ('Artur', 'Dębski', 'test5', 'test5', '5', '5', '4');

insert into user (FirstName, LastName, Login, Password, RoleId, State, ObjectId) values ('Mateusz', 'Dąbrowski', 'test6', 'test6', '5', '1', '3');


insert into user_object (ObjectId, UserId)  values ('2','1');
insert into user_object (ObjectId, UserId)  values ('3','2');
insert into user_object (ObjectId, UserId)  values ('4','3');
insert into user_object (ObjectId, UserId)  values ('4','4');
insert into user_object (ObjectId, UserId)  values ('1','5');
