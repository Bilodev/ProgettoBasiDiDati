-- ============================================
-- TABELLA UTENTE
-- ============================================
CREATE TABLE Utente (
    utenteID INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    cognome VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    isAdmin BOOLEAN NOT NULL DEFAULT FALSE
);


-- ============================================
-- TABELLA AUTORE
-- ============================================
CREATE TABLE Autore (
    autoreID INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    cognome VARCHAR(50) NOT NULL,
    biografia TEXT,
    dataNascita DATE,
    dataMorte DATE
);


-- ============================================
-- TABELLA LIBRO
-- (SENZA VOTO — scelta: eliminazione ridondanza)
-- ============================================
CREATE TABLE Libro (
    libroID INT AUTO_INCREMENT PRIMARY KEY,
    titolo VARCHAR(200) NOT NULL,
    dataPrimaEdizione DATE,
    autoreID INT NOT NULL,
    FOREIGN KEY (autoreID) REFERENCES Autore(autoreID)
);


-- ============================================
-- TABELLA GENERE
-- ============================================
CREATE TABLE Genere (
    genereID INT AUTO_INCREMENT PRIMARY KEY,
    genere VARCHAR(50) NOT NULL UNIQUE
);


-- ============================================
-- TABELLA EDITORE
-- ============================================
CREATE TABLE Editore (
    editoreID INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    sede VARCHAR(100),
    nazione VARCHAR(50)
);


-- ============================================
-- TABELLA EDIZIONE
-- ============================================
CREATE TABLE Edizione (
    edizioneID INT AUTO_INCREMENT PRIMARY KEY,
    editoreID INT NOT NULL,
    libroID INT NOT NULL,
    titolo VARCHAR(200) NOT NULL,
    lingua VARCHAR(50),
    didascalia TEXT,
    numeroPagine INT,
    FOREIGN KEY (editoreID) REFERENCES Editore(editoreID),
    FOREIGN KEY (libroID) REFERENCES Libro(libroID)
);


-- ============================================
-- TABELLA PREFAZIONE
-- ============================================
CREATE TABLE Prefazione (
    prefazioneID INT AUTO_INCREMENT PRIMARY KEY,
    titolo VARCHAR(200) NOT NULL,
    autoreID INT NOT NULL,
    edizioneID INT NOT NULL,
    FOREIGN KEY (autoreID) REFERENCES Autore(autoreID),
    FOREIGN KEY (edizioneID) REFERENCES Edizione(edizioneID)
);


-- ============================================
-- RELAZIONE CLASSIFICARE (Edizione – Genere)
-- ============================================
CREATE TABLE CLASSIFICARE (
    edizioneID INT NOT NULL,
    genereID INT NOT NULL,
    PRIMARY KEY (edizioneID, genereID),
    FOREIGN KEY (edizioneID) REFERENCES Edizione(edizioneID),
    FOREIGN KEY (genereID) REFERENCES Genere(genereID)
);


-- ============================================
-- TABELLA LETTURA
-- (integrata con i dati della recensione)
-- ============================================
CREATE TABLE LETTURA (
    utenteID INT NOT NULL,
    edizioneID INT NOT NULL,

    -- status = 'da_leggere' | 'in_lettura' | 'finito'
    status ENUM('da_leggere', 'in_lettura', 'finito') NOT NULL,

    dataFine DATE DEFAULT NULL,

    -- voto = numero intero 0–10
    voto INT CHECK (voto BETWEEN 0 AND 10),

    -- mezzo = 0 se voto intero, 1 se mezzo voto
    mezzo BOOLEAN,

    descrizione TEXT,

    PRIMARY KEY (utenteID, edizioneID),
    FOREIGN KEY (utenteID) REFERENCES Utente(utenteID),
    FOREIGN KEY (edizioneID) REFERENCES Edizione(edizioneID),

    -- vincolo logico: se finito, dataFine non può essere NULL
    CHECK (
        (status <> 'finito') OR
        (status = 'finito' AND dataFine IS NOT NULL)
    ),

    -- se voto = 10, mezzo deve essere 0
    CHECK (
        mezzo IN (0,1) AND
        (voto IS NULL OR (voto < 10 OR mezzo = 0))
    )
);
