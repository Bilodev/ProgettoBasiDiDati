-- 1. Registrazione nuovo utente
INSERT INTO UTENTE (utenteID, nome, cognome, email, password, isAdmin)
VALUES (?, ?, ?, ?, ?, FALSE);

-- 2. Login utente
SELECT utenteID, nome, cognome, isAdmin
FROM UTENTE
WHERE email = ? AND password = ?;

-- 3. Aggiunta libro (RegistrareL)
INSERT INTO Libro (libroID, titolo, dataPrimaEdizione, autoreID)
VALUES (?, ?, ?, ?);

-- 4. Aggiunta edizione (RegistrareE)
INSERT INTO Edizione (edizioneID, editoreID, libroID, titolo, lingua, didascalia, numeroPagine)
VALUES (?, ?, ?, ?, ?, ?, ?);

-- 5. Inserimento lettura completata (Concludere)
INSERT INTO LETTURA (utenteID, edizioneID, status, dataFine)
VALUES (?, ?, 'finito', CURRENT_DATE);

-- 6. Inserimento recensione collegata alla lettura (Appartenere)
UPDATE LETTURA
SET voto = ?, mezzo = ?, descrizione = ?
WHERE utenteID = ?
  AND edizioneID = ?
  AND status = 'finito';

-- 7. Aggiunta alla lista "Vuole leggere"
INSERT INTO LETTURA (utenteID, edizioneID, status)
VALUES (?, ?, 'da_leggere');

-- 8. Aggiornamento stato lettura
UPDATE LETTURA
SET status = ?
WHERE utenteID = ?
  AND edizioneID = ?;

-- 8b. Aggiornamento per passare a "finito"
UPDATE LETTURA
SET status = 'finito',
    dataFine = CURRENT_DATE
WHERE utenteID = ?
  AND edizioneID = ?;

-- 9. Generazione voto medio di un libro
SELECT AVG(voto + mezzo * 0.5) AS votoMedio
FROM LETTURA L JOIN Edizione E ON L.edizioneID = E.edizioneID
WHERE L.voto IS NOT NULL AND E.libroID = ?;

-- 10. Import massivo editori
INSERT INTO Editore (editoreID, nome, sede, nazione)
SELECT editoreID, nome, sede, nazione
FROM EditoriTemp;
