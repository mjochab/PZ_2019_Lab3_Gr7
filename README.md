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
  * Pracownik sklepu, może przeglądać dostępne towary na magazynie oraz sklepie, w przypadku ich braku może zamówić towar z magazynu. Wprowadza dane klientów do systemu oraz może je odczytać, tworzy zamówienia dla klientów.
  * Pracownik magazynu, może odczytać braki magazynowe sklepu oraz zamowić towar z innych magazynów.
  * Pracownik działu logistycznego, odczytuje zamówienia pracownika magazynu. Potwierdza odbiór oraz dostarczenie towarów z magazynów.
  * Analityk generuje raport, zmienia ceny produktów, ma dostęp do informacji ile dany pracownik sklepu sprzedał produktów.
  * Administrator może wykonywać wszystkie w/w czynności oraz możliwość dodawania nowych pracowników do systemu.
  
![Diagram Klas](https://github.com/mjochab/PZ_2019_Lab3_Gr7/blob/master/diagramy/class%20diagram.png)
![Diagram Sekwencji](https://github.com/mjochab/PZ_2019_Lab3_Gr7/blob/master/diagramy/sequence%20diagram.png)
![Diagram Stanów](https://github.com/mjochab/PZ_2019_Lab3_Gr7/blob/master/diagramy/state%20diagram.png)
![Diagram Przypadków Użycia](https://github.com/mjochab/PZ_2019_Lab3_Gr7/blob/master/diagramy/usecase%20diagram.png)


  
## Autorzy
* Paweł Durda
* Mateusz Gawlak
* Wojciech Gałka
* Adrian Gajewski
* Kamil Bania

## Licencja

Projekt objęty jest licencją MIT - sprawdź szczegóły w pliku [LICENSE.md](google.pl)
