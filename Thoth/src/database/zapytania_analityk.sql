-- Moduł Analityka:
-- 1. Pobranie sprzedaży: całej i w okresie od - do
select * from receipt;
select * from receipt when Date between "YYYY-MM-DD" and "YYYY-MM-DD";
-- 2. Wyświetlanie produktów (id, nazwa, cena, znizka)
select * from product;
-- 3. Ustawienie zniżki na produkcie
update product set Discount = "" where ProductId = "";
-- 4. Zmiana ceny produktu
update product set Price = "" where ProductId = "";