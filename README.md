# Nutikas Restorani Reserveerimissüsteem

Restoranikülastajale laudade broneerimise ja sobiva laua soovitamise veebirakendus.

---

## Kuidas käivitada

1. **Tee kindlaks, et Java 21 and Maven on installeeritud.**  
2. src/main/java/com/restaurant/restaurant.java – Spring Boot entry point, seejärel mvn spring-boot:run või valitud IDEs käivitamine
3. Pärast käivitamist mine valitud brauseris veebilehele http://localhost:8080/index.html

---

Tööks kulunud aeg: 1 nädal. See on mu esimene projekt Springbooti ja Javaga. Valminud töö peegeldab, mille ma nädalaga nende kahe kohta selgeks sain. Ei jõudnud tegeleda broneerimise osaga, kuupäev ja kellaaeg jäid kasutamata. Lahendaksin muidu seda nii, et küsiksin kasutaja sisestatud andmeid samamoodi, nagu olen teinud teiste andmetega, ja controller ning recommendation failides genereeriksin broneeringuid. Lisaksin veebirakendusele tabeli, mis näitaks genereeritud broneeringuid, et kontrollida rakenduse töötamise õigsust. Praegune laudade soovitamise meetod ei tööta niimoodi nagu ette kujutasin, peaksin süsteemi siluma.
