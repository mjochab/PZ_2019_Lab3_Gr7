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

insert into shop (ZipCode, City, Street, IsShop) values ('35-311', 'Rzeszow', 'Powstancow Warszawy', 1);
insert into shop (ZipCode, City, Street, IsShop) values ('32-336', 'Krakow', 'Warszawska', 1);
insert into shop (ZipCode, City, Street, IsShop) values ('36-006', 'Warszawa', 'Krakowska', 0);
insert into shop (ZipCode, City, Street, IsShop) values ('34-056', 'Poznan', 'Aleja Wolnosci', 1);

INSERT INTO product (ProductId, Name, Price, Discount) VALUES (null, 'Motorola Moto Z3 Play', '1000', '0');
INSERT INTO product (ProductId, Name, Price, Discount) VALUES (null, 'Huawei P9 Lite', '1100', '0');
INSERT INTO product (ProductId, Name, Price, Discount) VALUES (null, 'Samsung Galaxy S10', '4500', '0');
INSERT INTO product (ProductId, Name, Price, Discount) VALUES (null, 'Xiaomi MI9', '500', '0');
INSERT INTO product (ProductId, Name, Price, Discount) VALUES (null, 'Sony Xperia 10', '2000', '0');
INSERT INTO product (ProductId, Name, Price, Discount) VALUES (null, 'Samsung Wave 3', '150', '0');

INSERT INTO customer (CustomerId, FirstName, LastName, PhoneNumber) VALUES (null, 'Jan', 'Nosacz', '555444333');
INSERT INTO customer (CustomerId, FirstName, LastName, PhoneNumber) VALUES (null, 'Halina', 'Nosaczowa', '444555333');

insert into user (FirstName, LastName, Login, Password, RoleId, State) values ('Adam', 'Kowalski', 'test1', 'test1', '1', '1');
insert into user (FirstName, LastName, Login, Password, RoleId, State) values ('Bartosz', 'Nowacki', 'test2', 'test2', '2', '1');
insert into user (FirstName, LastName, Login, Password, RoleId, State) values ('Tadeusz', 'Malinowski', 'test3', 'test3', '3', '1');
insert into user (FirstName, LastName, Login, Password, RoleId, State) values ('Patryk', 'Nowakowski', 'test4', 'test4', '4', '1');
insert into user (FirstName, LastName, Login, Password, RoleId, State) values ('Artur', 'Krasicki', 'test5', 'test5', '5', '1');


--sprzedawcy
insert into user (FirstName, LastName, Login, Password, RoleId, State) values ('Adam', 'Kowalski', 'sk1', 'sk1', '4', '1');
insert into user (FirstName, LastName, Login, Password, RoleId, State) values ('Bartosz', 'Nowacki', 'sk2', 'sk2', '4', '1');
insert into user (FirstName, LastName, Login, Password, RoleId, State) values ('Tadeusz', 'Malinowski', 'sk3', 'sk3', '4', '1');
insert into user (FirstName, LastName, Login, Password, RoleId, State) values ('Patryk', 'Nowakowski', 'sk4', 'sk4', '4', '1');
insert into user (FirstName, LastName, Login, Password, RoleId, State) values ('Artur', 'Krasicki', 'sk5', 'sk5', '4', '1');


insert into user_shop (ShopId, UserId)  values ('1','1');
insert into user_shop (ShopId, UserId)  values ('2','2');
insert into user_shop (ShopId, UserId)  values ('1','3');
insert into user_shop (ShopId, UserId)  values ('4','4');
insert into user_shop (ShopId, UserId)  values ('4','5');

--sprzedawcy
insert into user_shop (ShopId, UserId)  values ('1','6');
insert into user_shop (ShopId, UserId)  values ('1','7');
insert into user_shop (ShopId, UserId)  values ('1','8');
insert into user_shop (ShopId, UserId)  values ('2','9');
insert into user_shop (ShopId, UserId)  values ('2','10');


INSERT INTO state_on_shop (ProductId, ShopId, Amount) VALUES ('1', '1', '999');
INSERT INTO state_on_shop (ProductId, ShopId, Amount) VALUES ('2', '1', '999');
INSERT INTO state_on_shop (ProductId, ShopId, Amount) VALUES ('3', '1', '999');
INSERT INTO state_on_shop (ProductId, ShopId, Amount) VALUES ('4', '1', '999');
INSERT INTO state_on_shop (ProductId, ShopId, Amount) VALUES ('5', '1', '999');
INSERT INTO state_on_shop (ProductId, ShopId, Amount) VALUES ('6', '1', '999');
INSERT INTO state_on_shop (ProductId, ShopId, Amount) VALUES ('1', '2', '999');
INSERT INTO state_on_shop (ProductId, ShopId, Amount) VALUES ('2', '2', '999');
INSERT INTO state_on_shop (ProductId, ShopId, Amount) VALUES ('3', '2', '999');
INSERT INTO state_on_shop (ProductId, ShopId, Amount) VALUES ('4', '2', '999');


INSERT INTO receipt (ReceiptId, ShopId, TotalValue, UserId, Date) VALUES (null, '1', '0', '6', '2019-04-06');
INSERT INTO receipt (ReceiptId, ShopId, TotalValue, UserId, Date) VALUES (null, '1', '0', '6', '2019-04-06');
INSERT INTO receipt (ReceiptId, ShopId, TotalValue, UserId, Date) VALUES (null, '1', '0', '7', '2019-04-06');
INSERT INTO receipt (ReceiptId, ShopId, TotalValue, UserId, Date) VALUES (null, '1', '0', '8', '2019-04-06');
INSERT INTO receipt (ReceiptId, ShopId, TotalValue, UserId, Date) VALUES (null, '2', '0', '9', '2019-04-06');
INSERT INTO receipt (ReceiptId, ShopId, TotalValue, UserId, Date) VALUES (null, '2', '0', '10', '2019-04-06');


INSERT INTO `product_receipt`(ProductId, `ReceiptId`, `Amount`, `Price`) VALUES ('1', '1', '2', (SELECT Price FROM product WHERE ProductId = 1));
INSERT INTO `product_receipt`(ProductId, `ReceiptId`, `Amount`, `Price`) VALUES ('2', '1', '1', (SELECT Price FROM product WHERE ProductId = 2));
INSERT INTO `product_receipt`(ProductId, `ReceiptId`, `Amount`, `Price`) VALUES ('3', '1', '1', (SELECT Price FROM product WHERE ProductId = 3));
INSERT INTO `product_receipt`(ProductId, `ReceiptId`, `Amount`, `Price`) VALUES ('4', '1', '1', (SELECT Price FROM product WHERE ProductId = 4));
INSERT INTO `product_receipt`(ProductId, `ReceiptId`, `Amount`, `Price`) VALUES ('5', '1', '1', (SELECT Price FROM product WHERE ProductId = 5));
INSERT INTO `product_receipt`(ProductId, `ReceiptId`, `Amount`, `Price`) VALUES ('6', '2', '1', (SELECT Price FROM product WHERE ProductId = 6));
INSERT INTO `product_receipt`(ProductId, `ReceiptId`, `Amount`, `Price`) VALUES ('5', '2', '1', (SELECT Price FROM product WHERE ProductId = 5));
INSERT INTO `product_receipt`(ProductId, `ReceiptId`, `Amount`, `Price`) VALUES ('4', '2', '1', (SELECT Price FROM product WHERE ProductId = 4));
INSERT INTO `product_receipt`(ProductId, `ReceiptId`, `Amount`, `Price`) VALUES ('1', '3', '1', (SELECT Price FROM product WHERE ProductId = 1));
INSERT INTO `product_receipt`(ProductId, `ReceiptId`, `Amount`, `Price`) VALUES ('1', '4', '1', (SELECT Price FROM product WHERE ProductId = 1));
INSERT INTO `product_receipt`(ProductId, `ReceiptId`, `Amount`, `Price`) VALUES ('2', '5', '1', (SELECT Price FROM product WHERE ProductId = 2));
INSERT INTO `product_receipt`(ProductId, `ReceiptId`, `Amount`, `Price`) VALUES ('1', '6', '1', (SELECT Price FROM product WHERE ProductId = 1));


UPDATE `receipt` SET `TotalValue`= (SELECT SUM(Price) FROM product_receipt WHERE ReceiptId = 1) WHERE ReceiptId = 1;
UPDATE `receipt` SET `TotalValue`= (SELECT SUM(Price) FROM product_receipt WHERE ReceiptId = 2) WHERE ReceiptId = 2;

INSERT INTO Indent (IndentId, ShopId_need, CustomerId, ShopId_delivery, DateOfOrder, ParentId) VALUES (null, '1', null, '3', '2019-04-07', null);
INSERT INTO Indent (IndentId, ShopId_need, CustomerId, ShopId_delivery, DateOfOrder, ParentId) VALUES (null, '2', null, '3', '2019-04-07', null);
INSERT INTO Indent (IndentId, ShopId_need, CustomerId, ShopId_delivery, DateOfOrder, ParentId) VALUES (null, '1', null, '2', '2019-04-25', null);
INSERT INTO Indent (IndentId, ShopId_need, CustomerId, ShopId_delivery, DateOfOrder, ParentId) VALUES (null, '1', null, '2', '2019-04-29', null);
INSERT INTO Indent (IndentId, ShopId_need, CustomerId, ShopId_delivery, DateOfOrder, ParentId) VALUES (null, '1', null, '2', '2019-04-29', '4');
INSERT INTO Indent (IndentId, ShopId_need, CustomerId, ShopId_delivery, DateOfOrder, ParentId) VALUES (null, '1', null, '3', '2019-04-29', '4');

INSERT INTO indent_product (Id, IndentId, ProductId, Amount) VALUES (null, '1', '1', '3');
INSERT INTO indent_product (Id, IndentId, ProductId, Amount) VALUES (null, '1', '2', '1');
INSERT INTO indent_product (Id, IndentId, ProductId, Amount) VALUES (null, '2', '3', '2');
INSERT INTO indent_product (Id, IndentId, ProductId, Amount) VALUES (null, '2', '4', '2');
INSERT INTO indent_product (Id, IndentId, ProductId, Amount) VALUES (null, '3', '2', '1');
INSERT INTO indent_product (Id, IndentId, ProductId, Amount) VALUES (null, '5', '2', '1');
INSERT INTO indent_product (Id, IndentId, ProductId, Amount) VALUES (null, '6', '3', '1');

INSERT INTO state_of_indent (Id, UserId, IndentId, StateId) VALUES (null, 2, 1, 1);
INSERT INTO state_of_indent (Id, UserId, IndentId, StateId) VALUES (null, 2, 2, 2);
INSERT INTO state_of_indent (Id, UserId, IndentId, StateId) VALUES (null, 2, 3, 2);
INSERT INTO state_of_indent (Id, UserId, IndentId, StateId) VALUES (null, 2, 4, 2);
INSERT INTO state_of_indent (Id, UserId, IndentId, StateId) VALUES (null, 2, 5, 2);
INSERT INTO state_of_indent (Id, UserId, IndentId, StateId) VALUES (null, 2, 6, 2);

