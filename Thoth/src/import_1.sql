--
-- Zrzut danych tabeli `customer`
--

INSERT INTO `customer` (`CustomerId`, `FirstName`, `LastName`, `PhoneNumber`) VALUES(1, 'Marta', 'Biedron', 293821331);

--
-- Zrzut danych tabeli `indent`
--

INSERT INTO `indent` (`IndentId`, `DateOfOrder`, `isComplex`, `CustomerId`, `ParentId`, `ShopId_delivery`, `ShopId_need`) VALUES(1, '2019-05-28 00:50:55.000000', NULL, 1, NULL, NULL, 1);
INSERT INTO `indent` (`IndentId`, `DateOfOrder`, `isComplex`, `CustomerId`, `ParentId`, `ShopId_delivery`, `ShopId_need`) VALUES(2, '2019-05-28 00:53:01.000000', b'0', NULL, NULL, 2, 1);

--
-- Zrzut danych tabeli `indent_product`
--

INSERT INTO `indent_product` (`Id`, `Amount`, `IndentId`, `ProductId`) VALUES(1, 1, 1, 9);
INSERT INTO `indent_product` (`Id`, `Amount`, `IndentId`, `ProductId`) VALUES(2, 1, 2, 9);

--
-- Zrzut danych tabeli `product`
--

INSERT INTO `product` (`ProductId`, `Discount`, `Name`, `Price`) VALUES(1, 0, 'Honor 20 Lite', '1299.00');
INSERT INTO `product` (`ProductId`, `Discount`, `Name`, `Price`) VALUES(2, 0, 'Motorola One Vision 4', '1299.00');
INSERT INTO `product` (`ProductId`, `Discount`, `Name`, `Price`) VALUES(3, 0, 'Honor 8X', '1199.00');
INSERT INTO `product` (`ProductId`, `Discount`, `Name`, `Price`) VALUES(4, 0, 'Samsung Galaxy A70', '1799.00');
INSERT INTO `product` (`ProductId`, `Discount`, `Name`, `Price`) VALUES(5, 0, 'Samsung Galaxy A20E', '849.00');
INSERT INTO `product` (`ProductId`, `Discount`, `Name`, `Price`) VALUES(6, 0, 'Xiaomi Redmi 7', '649.00');
INSERT INTO `product` (`ProductId`, `Discount`, `Name`, `Price`) VALUES(7, 0, 'Xiaomi Redmi Note 7', '899.00');
INSERT INTO `product` (`ProductId`, `Discount`, `Name`, `Price`) VALUES(8, 0, 'Samsung Galaxy S10', '3949.00');
INSERT INTO `product` (`ProductId`, `Discount`, `Name`, `Price`) VALUES(9, 0, 'Samsung Galaxy A9', '1649.00');
INSERT INTO `product` (`ProductId`, `Discount`, `Name`, `Price`) VALUES(10, 0, 'Huawei Y6', '499.00');

--
-- Zrzut danych tabeli `product_receipt`
--

INSERT INTO `product_receipt` (`Id`, `Amount`, `Price`, `ProductId`, `ReceiptId`) VALUES(1, 6, '30564.00', 5, 1);
INSERT INTO `product_receipt` (`Id`, `Amount`, `Price`, `ProductId`, `ReceiptId`) VALUES(2, 1, '1199.00', 3, 2);
INSERT INTO `product_receipt` (`Id`, `Amount`, `Price`, `ProductId`, `ReceiptId`) VALUES(3, 1, '849.00', 5, 2);
INSERT INTO `product_receipt` (`Id`, `Amount`, `Price`, `ProductId`, `ReceiptId`) VALUES(4, 1, '649.00', 6, 2);

--
-- Zrzut danych tabeli `receipt`
--

INSERT INTO `receipt` (`ReceiptId`, `Date`, `TotalValue`, `ShopId`, `UserId`) VALUES(1, '2019-05-28 00:44:14.000000', '5094.00', 1, 3);
INSERT INTO `receipt` (`ReceiptId`, `Date`, `TotalValue`, `ShopId`, `UserId`) VALUES(2, '2019-05-28 00:46:35.000000', '2697.00', 1, 1);

--
-- Zrzut danych tabeli `role`
--

INSERT INTO `role` (`RoleId`, `Position`) VALUES(1, 'Admin');
INSERT INTO `role` (`RoleId`, `Position`) VALUES(2, 'Magazynier');
INSERT INTO `role` (`RoleId`, `Position`) VALUES(3, 'Analityk');
INSERT INTO `role` (`RoleId`, `Position`) VALUES(4, 'Sprzedawca');
INSERT INTO `role` (`RoleId`, `Position`) VALUES(5, 'Logistyk');

--
-- Zrzut danych tabeli `shop`
--

INSERT INTO `shop` (`ShopId`, `City`, `IsShop`, `Street`, `ZipCode`) VALUES(1, 'Rzeszow', b'1', 'Staroniwska 61', '35-101');
INSERT INTO `shop` (`ShopId`, `City`, `IsShop`, `Street`, `ZipCode`) VALUES(2, 'Krakow', b'1', 'Wielicka 122', '30-663');
INSERT INTO `shop` (`ShopId`, `City`, `IsShop`, `Street`, `ZipCode`) VALUES(3, 'Warszawa', b'1', 'Mokotowska 34', '00-541');
INSERT INTO `shop` (`ShopId`, `City`, `IsShop`, `Street`, `ZipCode`) VALUES(4, 'Gorzow Wielkopolski', b'1', 'Warszawska 28', '66-400');
INSERT INTO `shop` (`ShopId`, `City`, `IsShop`, `Street`, `ZipCode`) VALUES(5, 'Poznan', b'1', ' Lodowa 9', '60-227');

--
-- Zrzut danych tabeli `state`
--

INSERT INTO `state` (`StateId`, `Name`) VALUES(1, 'W realizacji');
INSERT INTO `state` (`StateId`, `Name`) VALUES(2, 'Oczekuje na transport');
INSERT INTO `state` (`StateId`, `Name`) VALUES(3, 'W transporcie');
INSERT INTO `state` (`StateId`, `Name`) VALUES(4, 'Oczekuje na potwierdzenie odbioru');
INSERT INTO `state` (`StateId`, `Name`) VALUES(5, 'Zrealizowane');
INSERT INTO `state` (`StateId`, `Name`) VALUES(64, 'Niedostepny');
INSERT INTO `state` (`StateId`, `Name`) VALUES(65, 'Mozliwy do wydania');
INSERT INTO `state` (`StateId`, `Name`) VALUES(66, 'Odebrane przez klienta');

--
-- Zrzut danych tabeli `state_of_indent`
--

INSERT INTO `state_of_indent` (`Id`, `IndentId`, `StateId`, `UserId`) VALUES(1, 1, 64, 3);
INSERT INTO `state_of_indent` (`Id`, `IndentId`, `StateId`, `UserId`) VALUES(2, 2, 4, 1);

--
-- Zrzut danych tabeli `state_on_shop`
--

INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(1, 4, 0, 1, 1);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(2, 0, 0, 1, 2);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(3, 0, 0, 1, 3);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(4, 0, 0, 1, 4);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(5, 0, 0, 1, 5);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(6, 7, 0, 2, 1);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(7, 0, 0, 2, 2);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(8, 0, 0, 2, 3);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(9, 0, 0, 2, 4);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(10, 0, 0, 2, 5);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(11, 20, 0, 3, 1);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(12, 0, 0, 3, 2);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(13, 0, 0, 3, 3);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(14, 0, 0, 3, 4);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(15, 0, 0, 3, 5);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(16, 7, 0, 4, 1);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(17, 0, 0, 4, 2);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(18, 0, 0, 4, 3);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(19, 0, 0, 4, 4);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(20, 0, 0, 4, 5);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(21, 75, 0, 5, 1);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(22, 0, 0, 5, 2);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(23, 0, 0, 5, 3);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(24, 0, 0, 5, 4);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(25, 0, 0, 5, 5);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(26, 51, 0, 6, 1);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(27, 0, 0, 6, 2);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(28, 0, 0, 6, 3);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(29, 0, 0, 6, 4);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(30, 0, 0, 6, 5);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(31, 38, 0, 7, 1);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(32, 0, 0, 7, 2);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(33, 0, 0, 7, 3);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(34, 0, 0, 7, 4);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(35, 0, 0, 7, 5);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(36, 63, 0, 8, 1);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(37, 0, 0, 8, 2);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(38, 0, 0, 8, 3);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(39, 0, 0, 8, 4);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(40, 0, 0, 8, 5);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(41, 0, 0, 9, 1);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(42, 200, 1, 9, 2);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(43, 0, 0, 9, 3);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(44, 0, 0, 9, 4);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(45, 0, 0, 9, 5);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(46, 0, 0, 10, 1);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(47, 32, 0, 10, 2);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(48, 0, 0, 10, 3);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(49, 0, 0, 10, 4);
INSERT INTO `state_on_shop` (`Id`, `Amount`, `Locked`, `ProductId`, `ShopId`) VALUES(50, 0, 0, 10, 5);

--
-- Zrzut danych tabeli `user`
--

INSERT INTO `user` (`UserId`, `FirstName`, `LastName`, `Login`, `Password`, `State`, `RoleId`) VALUES(1, 'MAIN', 'ADMIN', 'admin', 'admin', 1, 1);
INSERT INTO `user` (`UserId`, `FirstName`, `LastName`, `Login`, `Password`, `State`, `RoleId`) VALUES(2, 'Marek', 'Mostowiak', 'Mmostowiak', 'sklep', 1, 4);
INSERT INTO `user` (`UserId`, `FirstName`, `LastName`, `Login`, `Password`, `State`, `RoleId`) VALUES(3, 'Jan', 'Kowalski', 'Jkowalski', 'sklep', 1, 4);
INSERT INTO `user` (`UserId`, `FirstName`, `LastName`, `Login`, `Password`, `State`, `RoleId`) VALUES(4, 'Jacek', 'Balcerzak', 'jBalcerzak', 'sklep', 1, 4);
INSERT INTO `user` (`UserId`, `FirstName`, `LastName`, `Login`, `Password`, `State`, `RoleId`) VALUES(5, 'Nikola', 'Pamula', 'npamula', 'sklep', 1, 4);
INSERT INTO `user` (`UserId`, `FirstName`, `LastName`, `Login`, `Password`, `State`, `RoleId`) VALUES(6, 'Miroslaw', 'Bitner', 'mbitner', 'magazyn', 1, 2);
INSERT INTO `user` (`UserId`, `FirstName`, `LastName`, `Login`, `Password`, `State`, `RoleId`) VALUES(7, 'Rafal', 'Radomski', 'rradomski', 'magazyn', 1, 2);
INSERT INTO `user` (`UserId`, `FirstName`, `LastName`, `Login`, `Password`, `State`, `RoleId`) VALUES(8, 'Kamil', 'Kania', 'kkania', 'magazyn', 1, 2);
INSERT INTO `user` (`UserId`, `FirstName`, `LastName`, `Login`, `Password`, `State`, `RoleId`) VALUES(9, 'Pawel', 'Leszko', 'analityk', 'analityk', 1, 3);
INSERT INTO `user` (`UserId`, `FirstName`, `LastName`, `Login`, `Password`, `State`, `RoleId`) VALUES(10, 'Jakub', 'Jaszczuk', 'logistyk', 'logistyk', 1, 5);
INSERT INTO `user` (`UserId`, `FirstName`, `LastName`, `Login`, `Password`, `State`, `RoleId`) VALUES(11, 'Tomasz', 'Jaszczuk', 'sklep', 'sklep', 1, 4);
INSERT INTO `user` (`UserId`, `FirstName`, `LastName`, `Login`, `Password`, `State`, `RoleId`) VALUES(12, 'Kamil', 'Radomski', 'kradomski', 'sklep', 1, 4);
INSERT INTO `user` (`UserId`, `FirstName`, `LastName`, `Login`, `Password`, `State`, `RoleId`) VALUES(13, 'Janusz', 'Witczak', 'jwitczak', 'magazyn', 1, 2);
INSERT INTO `user` (`UserId`, `FirstName`, `LastName`, `Login`, `Password`, `State`, `RoleId`) VALUES(14, 'Mateusz', 'Knitter', 'mkniter', 'magazyn', 1, 2);
INSERT INTO `user` (`UserId`, `FirstName`, `LastName`, `Login`, `Password`, `State`, `RoleId`) VALUES(15, 'Justyna', 'Kepka', 'jkepka', 'logistyk', 1, 5);
INSERT INTO `user` (`UserId`, `FirstName`, `LastName`, `Login`, `Password`, `State`, `RoleId`) VALUES(16, 'Kamila', 'Knitter', 'kknitter', 'sklep', 1, 4);
INSERT INTO `user` (`UserId`, `FirstName`, `LastName`, `Login`, `Password`, `State`, `RoleId`) VALUES(17, 'Katarzyna', 'Ochman', 'kochman', 'sklep', 1, 4);
INSERT INTO `user` (`UserId`, `FirstName`, `LastName`, `Login`, `Password`, `State`, `RoleId`) VALUES(18, 'Zofia', 'Walczak', 'zwalczak', 'magazyn', 1, 2);
INSERT INTO `user` (`UserId`, `FirstName`, `LastName`, `Login`, `Password`, `State`, `RoleId`) VALUES(19, 'Miroslawa', 'Jakubowicz', 'mjakubowicz', 'logistyk', 1, 5);
INSERT INTO `user` (`UserId`, `FirstName`, `LastName`, `Login`, `Password`, `State`, `RoleId`) VALUES(20, 'Michal', 'Wisniewski', 'mwisniewski', 'sklep', 1, 4);
INSERT INTO `user` (`UserId`, `FirstName`, `LastName`, `Login`, `Password`, `State`, `RoleId`) VALUES(21, 'Wojciech', 'Chlidzinski', 'wchludzinski', 'magazyn', 1, 2);
INSERT INTO `user` (`UserId`, `FirstName`, `LastName`, `Login`, `Password`, `State`, `RoleId`) VALUES(22, 'Wiktoria', 'Witczak', 'wwitczak', 'magazyn', 1, 2);
INSERT INTO `user` (`UserId`, `FirstName`, `LastName`, `Login`, `Password`, `State`, `RoleId`) VALUES(23, 'Witold', 'Pasternak', 'wpasternak', 'logistyk', 1, 5);

--
-- Zrzut danych tabeli `user_shop`
--

INSERT INTO `user_shop` (`Id`, `ShopId`, `UserId`) VALUES(1, 1, 1);
INSERT INTO `user_shop` (`Id`, `ShopId`, `UserId`) VALUES(2, 1, 2);
INSERT INTO `user_shop` (`Id`, `ShopId`, `UserId`) VALUES(3, 1, 3);
INSERT INTO `user_shop` (`Id`, `ShopId`, `UserId`) VALUES(4, 1, 4);
INSERT INTO `user_shop` (`Id`, `ShopId`, `UserId`) VALUES(5, 1, 5);
INSERT INTO `user_shop` (`Id`, `ShopId`, `UserId`) VALUES(6, 1, 6);
INSERT INTO `user_shop` (`Id`, `ShopId`, `UserId`) VALUES(7, 1, 7);
INSERT INTO `user_shop` (`Id`, `ShopId`, `UserId`) VALUES(8, 1, 8);
INSERT INTO `user_shop` (`Id`, `ShopId`, `UserId`) VALUES(9, 1, 9);
INSERT INTO `user_shop` (`Id`, `ShopId`, `UserId`) VALUES(10, 1, 10);
INSERT INTO `user_shop` (`Id`, `ShopId`, `UserId`) VALUES(11, 2, 11);
INSERT INTO `user_shop` (`Id`, `ShopId`, `UserId`) VALUES(12, 2, 12);
INSERT INTO `user_shop` (`Id`, `ShopId`, `UserId`) VALUES(13, 2, 13);
INSERT INTO `user_shop` (`Id`, `ShopId`, `UserId`) VALUES(14, 2, 14);
INSERT INTO `user_shop` (`Id`, `ShopId`, `UserId`) VALUES(15, 2, 15);
INSERT INTO `user_shop` (`Id`, `ShopId`, `UserId`) VALUES(16, 3, 16);
INSERT INTO `user_shop` (`Id`, `ShopId`, `UserId`) VALUES(17, 3, 17);
INSERT INTO `user_shop` (`Id`, `ShopId`, `UserId`) VALUES(18, 3, 18);
INSERT INTO `user_shop` (`Id`, `ShopId`, `UserId`) VALUES(19, 3, 19);
INSERT INTO `user_shop` (`Id`, `ShopId`, `UserId`) VALUES(20, 4, 20);
INSERT INTO `user_shop` (`Id`, `ShopId`, `UserId`) VALUES(21, 4, 21);
INSERT INTO `user_shop` (`Id`, `ShopId`, `UserId`) VALUES(22, 4, 22);
INSERT INTO `user_shop` (`Id`, `ShopId`, `UserId`) VALUES(23, 4, 23);
