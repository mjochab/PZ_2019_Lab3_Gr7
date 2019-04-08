
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


insert into user_object (ObjectId, UserId)  values ('1','1');
insert into user_object (ObjectId, UserId)  values ('2','1');
insert into user_object (ObjectId, UserId)  values ('3','2');
insert into user_object (ObjectId, UserId)  values ('4','3');
insert into user_object (ObjectId, UserId)  values ('4','4');
insert into user_object (ObjectId, UserId)  values ('1','5');


INSERT INTO `product`(`ProductId`, `Name`, `Price`, `Discount`) VALUES (null, 'Motorola Moto Z3 Play', '1000', '0');
INSERT INTO `product`(`ProductId`, `Name`, `Price`, `Discount`) VALUES (null, 'Huawei P9 Lite', '1100', '0');
INSERT INTO `product`(`ProductId`, `Name`, `Price`, `Discount`) VALUES (null, 'Samsung Galaxy S10', '4500', '0');
INSERT INTO `product`(`ProductId`, `Name`, `Price`, `Discount`) VALUES (null, 'Xiaomi MI9', '500', '0');
INSERT INTO `product`(`ProductId`, `Name`, `Price`, `Discount`) VALUES (null, 'Sony Xperia 10', '2000', '0');
INSERT INTO `product`(`ProductId`, `Name`, `Price`, `Discount`) VALUES (null, 'Samsung Wave 3', '150', '0');


INSERT INTO `state_on_object`(`ProductId`, `ObjectId`, `Amount`) VALUES ('1', '1', '3');
INSERT INTO `state_on_object`(`ProductId`, `ObjectId`, `Amount`) VALUES ('2', '1', '4');
INSERT INTO `state_on_object`(`ProductId`, `ObjectId`, `Amount`) VALUES ('3', '2', '1');
INSERT INTO `state_on_object`(`ProductId`, `ObjectId`, `Amount`) VALUES ('1', '2', '2');
INSERT INTO `state_on_object`(`ProductId`, `ObjectId`, `Amount`) VALUES ('5', '2', '5');


INSERT INTO `receipt`(`ReceiptId`, `ObjectId`, `TotalValue`, `UserId`, `Date`) VALUES (null, '1', '0', '1', '2019-04-06');
INSERT INTO `receipt`(`ReceiptId`, `ObjectId`, `TotalValue`, `UserId`, `Date`) VALUES (null, '1', '0', '1', '2019-04-06');


INSERT INTO `product_receipt`(`ProductId`, `ReceiptId`, `Amount`, `Price`) VALUES ('1', '1', '1', (SELECT Price FROM product WHERE ProductId = 1));
INSERT INTO `product_receipt`(`ProductId`, `ReceiptId`, `Amount`, `Price`) VALUES ('2', '1', '1', (SELECT Price FROM product WHERE ProductId = 2));
INSERT INTO `product_receipt`(`ProductId`, `ReceiptId`, `Amount`, `Price`) VALUES ('1', '2', '1', (SELECT Price FROM product WHERE ProductId = 1));


UPDATE `receipt` SET `TotalValue`= (SELECT SUM(Price) FROM product_receipt WHERE ReceiptId = 1) WHERE ReceiptId = 1;
UPDATE `receipt` SET `TotalValue`= (SELECT SUM(Price) FROM product_receipt WHERE ReceiptId = 2) WHERE ReceiptId = 2;


INSERT INTO `customer`(`CustomerId`, `FirstName`, `LastName`, `PhoneNumber`) VALUES (null, 'Jan', 'Nosacz', '555444333');
INSERT INTO `customer`(`CustomerId`, `FirstName`, `LastName`, `PhoneNumber`) VALUES (null, 'Halina', 'Nosaczowa', '444555333');


INSERT INTO `state`(`StateId`, `Name`) VALUES (null, 'W realizacji');
INSERT INTO `state`(`StateId`, `Name`) VALUES (null, 'Oczekuje na transport');
INSERT INTO `state`(`StateId`, `Name`) VALUES (null, 'W transporcie');
INSERT INTO `state`(`StateId`, `Name`) VALUES (null, 'Oczekuje na potwierdzenie odbioru');
INSERT INTO `state`(`StateId`, `Name`) VALUES (null, 'Zrealizowane');


INSERT INTO `order`(`OrderId`, `ObjectId_need`, `CustomerId`, `ObjectId_delivery`, `DateOfOrder`, `ParentId`) VALUES (null, '1', null, '3', '2019-04-07', null);
INSERT INTO `order`(`OrderId`, `ObjectId_need`, `CustomerId`, `ObjectId_delivery`, `DateOfOrder`, `ParentId`) VALUES (null, '2', null, '3', '2019-04-07', null);


INSERT INTO `order_product`(`Id`, `OrderId`, `ProductId`, `Amount`) VALUES (null, '1', '1', '3');
INSERT INTO `order_product`(`Id`, `OrderId`, `ProductId`, `Amount`) VALUES (null, '1', '2', '1');
INSERT INTO `order_product`(`Id`, `OrderId`, `ProductId`, `Amount`) VALUES (null, '2', '3', '2');
INSERT INTO `order_product`(`Id`, `OrderId`, `ProductId`, `Amount`) VALUES (null, '2', '4', '2');


INSERT INTO `state_of_order`(`Id`, `UserId`, `OrderId`, `StateId`) VALUES (null, '2', '1', '1');