PZ_2019_Lab3_Gr7

# Aplikacja do zarządzania siecią sklepów.

**Oprogramowanie ma służyć do ewidencjonowania sprzedaży w sklepach, kontroli stanu magazynowego, zgłaszania braków w asortymencie oraz analizowania danych sprzedażowych.**

Użytkownicy systemu:
  * Administrator
  * Analityk
  * Pracownik sklepu
  * Pracownik magazynu
  * Pracownik działu logistycznego

Dane gromadzone w systemie:
  * dane dotyczące towaru na magazynie (administrator, pracownik magazynu)
  * dane dotyczące towaru w sklepie (administrator ,pracownik sklepu)
  * dane sprzedażowe (administrator, analityk)
  * dane dotyczące transportu towarów (administrator, pracownik działu logistycznego)
  * dane pracowników (administrator)
  * dane klientów (administrator, pracownik sklepu)

Raproty:
  * raporty dotyczace sprzedaży (administrator, analityk)
  
Funkcjonalności:
* Pracownik sklepu:
  * przeglądanie towarów z magazynu
  * przeglądanie towarów z sklepu
  * zamówienie towaru 
  * wprowadzanie danych klientów do systemu
  * odczytanie danych o klientach
  * tworzenie zamówień dla klientów
* Pracownik magazynu: 
  * odczytanie braków magazynowych 
  * zamówienie towarów z innego magazynu
* Pracownik działu logistycznego:
  * odczytanie zamówienia pracownika magazynu
  * potwierdzenie odbioru oraz dostarczenia towaru
* Analityk: 
  * generowanie raportów
  * zmienianie cen prduktów 
  * przeglądanie informacji o sprzedaży
* Administrator: 
  * wykonywanie wszystkiech wyżej wymienionych czynności 
  * dodawanie oraz usuwanie użytkowników
    
## Diagram przypadków użycia
![Diagram przypadków użycia](https://github.com/mjochab/PZ_2019_Lab3_Gr7/blob/master/diagramy/usecase%20diagramv3.png)
## Diagram stanów
![Diagram stanów](https://github.com/mjochab/PZ_2019_Lab3_Gr7/blob/master/diagramy/state%20diagram.png)
## Diagram sekwencji
![Diagram sekwencji](https://github.com/mjochab/PZ_2019_Lab3_Gr7/blob/master/diagramy/sequence%20diagram.png)
## Diagram klas  
![Diagram klas](https://github.com/mjochab/PZ_2019_Lab3_Gr7/blob/master/diagramy/class%20diagram.png)
## Diagram ERD  
![Diagram ERD](https://github.com/mjochab/PZ_2019_Lab3_Gr7/blob/DEVELOPE/diagramy/erddiag.png)



## Biblioteki

* [JDBC Driver](https://dev.mysql.com/downloads/file/?id=480091)
* [Hibernate](https://sourceforge.net/projects/hibernate/files/hibernate-orm/5.4.2.Final/hibernate-release-5.4.2.Final.zip/download)

## Dodawanie bibliotek w IntelliJ

w oknie projektu wciskamy kombinację
``` CTRL + SHIFT + ALT + S```
przechodzimy do zakładki ```Libraries``` wciskamy ```+``` a nastepnie ```java``` i wybieramy odpowiednie pliki jar:

## Instalacja

1. Powitanie instalatora wciśnij next.

![1 krok](https://github.com/mjochab/PZ_2019_Lab3_Gr7/blob/master/diagramy/1.PNG)

2. Informacja o wymaganej wersji JAVA wciśnij next.

![2 krok](https://github.com/mjochab/PZ_2019_Lab3_Gr7/blob/master/diagramy/2.PNG)

3. Zaakceptowanie warunków licencji jeżeli zgadzasz się na warunki licencji zaznacz I agree.. i wciśnij next.

![3 krok](https://github.com/mjochab/PZ_2019_Lab3_Gr7/blob/master/diagramy/3.PNG)

4. Wybierz lokalizację w której ma zostać zainstalowana aplikacja a nastepnie kliknij next. (!WAŻNEZalecana lokalizacja to folder na pulpicie. W przeciwnym wypadku wymagana jest zmiana ścieżki otwierającej program w pliku thoth.bat)

![4 krok](https://github.com/mjochab/PZ_2019_Lab3_Gr7/blob/master/diagramy/4.PNG)

5. Informacja ze ścieżką instalacji programu kliknij next.

![5 krok](https://github.com/mjochab/PZ_2019_Lab3_Gr7/blob/master/diagramy/5.PNG)

6. poczekaj aż instalator zakończy pracę

![6 krok](https://github.com/mjochab/PZ_2019_Lab3_Gr7/blob/master/diagramy/6.PNG)

7. instalacja zakonczona pomyślnie kliknij next

![7 krok](https://github.com/mjochab/PZ_2019_Lab3_Gr7/blob/master/diagramy/7.PNG)

## Instrukcja obsługi.

1. Po instalacji na pulpicie powinien pojawić się sktót do aplikacji o nazwie thoth. Uruchom go.
2. Program działa poprawie jeżeli widzisz główne okno aplikacji 

![1 krok](https://github.com/mjochab/PZ_2019_Lab3_Gr7/blob/master/diagramy/uzytkowanie/2.PNG)

oraz otwarta w tle konsola poprawnie wystartowala serwer bazodanowy.

![Server running](https://github.com/mjochab/PZ_2019_Lab3_Gr7/blob/master/diagramy/uzytkowanie/3.PNG)

3. zaloguj się na konto administratora login: admin hasło: amdin

![1 krok](https://github.com/mjochab/PZ_2019_Lab3_Gr7/blob/master/diagramy/uzytkowanie/2.PNG)

4. Wybierz jedną z dostępnych opcji

![4 krok](https://github.com/mjochab/PZ_2019_Lab3_Gr7/blob/master/diagramy/uzytkowanie/4.PNG)

### Hibernate 

![Diagram ERD](https://github.com/mjochab/PZ_2019_Lab3_Gr7/blob/DEVELOPE/diagramy/hibernatejars.png)

### JDBC driver

![Diagram ERD](https://github.com/mjochab/PZ_2019_Lab3_Gr7/blob/DEVELOPE/diagramy/mysqlconnectorjar.png)

## Autorzy
* Paweł Durda
* Mateusz Gawlak
* Wojciech Gałka
* Adrian Gajewski
* Kamil Bania

## Licencja

Projekt objęty jest licencją MIT - sprawdź szczegóły w pliku [LICENSE.md](https://github.com/mjochab/PZ_2019_Lab3_Gr7/blob/master/LICENSE)
