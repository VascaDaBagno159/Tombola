-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Creato il: Mag 08, 2026 alle 09:55
-- Versione del server: 8.0.45
-- Versione PHP: 8.0.22

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `my_kevinincarnato`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `TAS_cartelle`
--

CREATE TABLE `TAS_cartelle` (
  `car_id` int NOT NULL,
  `car_colonne` int NOT NULL,
  `car_righe` int NOT NULL,
  `car_numeri` text COLLATE utf8mb4_general_ci,
  `car_ute_id` int DEFAULT NULL,
  `car_tom_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `TAS_cartelle`
--

INSERT INTO `TAS_cartelle` (`car_id`, `car_colonne`, `car_righe`, `car_numeri`, `car_ute_id`, `car_tom_id`) VALUES
(1, 9, 3, '1,5,10,23,45', 2, 1),
(2, 9, 3, '3,7,11,54,67', 3, 1),
(3, 9, 3, '2,8,19,33,72', 2, 2);

-- --------------------------------------------------------

--
-- Struttura della tabella `TAS_estrazioni`
--

CREATE TABLE `TAS_estrazioni` (
  `est_id` int NOT NULL,
  `est_car_id` int DEFAULT NULL,
  `est_num_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `TAS_estrazioni`
--

INSERT INTO `TAS_estrazioni` (`est_id`, `est_car_id`, `est_num_id`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 1, 3),
(4, 2, 4),
(5, 2, 5),
(6, 3, 6);

-- --------------------------------------------------------

--
-- Struttura della tabella `TAS_gestori`
--

CREATE TABLE `TAS_gestori` (
  `ges_id` int NOT NULL,
  `ges_nome` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `ges_cognome` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `ges_username` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `ges_admin` tinyint(1) DEFAULT '0',
  `ges_password` varchar(255) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `TAS_gestori`
--

INSERT INTO `TAS_gestori` (`ges_id`, `ges_nome`, `ges_cognome`, `ges_username`, `ges_admin`, `ges_password`) VALUES
(1, 'Mario', 'Rossi', 'mrossi', 1, 'hash1'),
(2, 'Luca', 'Bianchi', 'lbianchi', 0, 'hash2'),
(3, 'Anna', 'Verdi', 'averdi', 0, 'hash3');

-- --------------------------------------------------------

--
-- Struttura della tabella `TAS_giocate`
--

CREATE TABLE `TAS_giocate` (
  `gio_id` int NOT NULL,
  `gio_ute_id` int DEFAULT NULL,
  `gio_tom_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `TAS_giocate`
--

INSERT INTO `TAS_giocate` (`gio_id`, `gio_ute_id`, `gio_tom_id`) VALUES
(1, 2, 1),
(2, 3, 1),
(3, 2, 2);

-- --------------------------------------------------------

--
-- Struttura della tabella `TAS_numeriestratti`
--

CREATE TABLE `TAS_numeriestratti` (
  `num_id` int NOT NULL,
  `num_numero` int NOT NULL,
  `num_timeStamp` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `TAS_numeriestratti`
--

INSERT INTO `TAS_numeriestratti` (`num_id`, `num_numero`, `num_timeStamp`) VALUES
(1, 5, '2026-04-23 18:32:08'),
(2, 10, '2026-04-23 18:32:08'),
(3, 23, '2026-04-23 18:32:08'),
(4, 45, '2026-04-23 18:32:08'),
(5, 67, '2026-04-23 18:32:08'),
(6, 72, '2026-04-23 18:32:08');

-- --------------------------------------------------------

--
-- Struttura della tabella `TAS_tipovincite`
--

CREATE TABLE `TAS_tipovincite` (
  `tip_id` int NOT NULL,
  `tip_tipo` varchar(100) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `TAS_tipovincite`
--

INSERT INTO `TAS_tipovincite` (`tip_id`, `tip_tipo`) VALUES
(1, 'Ambo'),
(2, 'Terno'),
(3, 'Quaterna'),
(4, 'Cinquina'),
(5, 'Tombola');

-- --------------------------------------------------------

--
-- Struttura della tabella `TAS_tombolate`
--

CREATE TABLE `TAS_tombolate` (
  `tom_id` int NOT NULL,
  `tom_nome` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `tom_timeStamp` datetime DEFAULT CURRENT_TIMESTAMP,
  `tom_luogo` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `tom_stato` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `TAS_tombolate`
--

INSERT INTO `TAS_tombolate` (`tom_id`, `tom_nome`, `tom_timeStamp`, `tom_luogo`, `tom_stato`) VALUES
(1, 'Tombolata di Natale', '2026-04-23 18:32:08', 'Roma', 'attiva'),
(2, 'Tombolata Capodanno', '2026-04-23 18:32:08', 'Milano', 'terminata');

-- --------------------------------------------------------

--
-- Struttura della tabella `TAS_utenti`
--

CREATE TABLE `TAS_utenti` (
  `utente_id` bigint UNSIGNED NOT NULL,
  `username` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `password_hash` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `nome` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `cognome` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `ruolo` varchar(20) COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'giocatore'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `TAS_vincite`
--

CREATE TABLE `TAS_vincite` (
  `vin_id` int NOT NULL,
  `vin_nVincitori` int NOT NULL,
  `vin_timeStamp` datetime DEFAULT CURRENT_TIMESTAMP,
  `vin_tip_id` int DEFAULT NULL,
  `vin_car_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `TAS_vincite`
--

INSERT INTO `TAS_vincite` (`vin_id`, `vin_nVincitori`, `vin_timeStamp`, `vin_tip_id`, `vin_car_id`) VALUES
(1, 1, '2026-04-23 18:32:08', 1, 1),
(2, 1, '2026-04-23 18:32:08', 2, 1),
(3, 2, '2026-04-23 18:32:08', 5, 2);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `TAS_cartelle`
--
ALTER TABLE `TAS_cartelle`
  ADD PRIMARY KEY (`car_id`),
  ADD KEY `car_ute_id` (`car_ute_id`),
  ADD KEY `car_tom_id` (`car_tom_id`);

--
-- Indici per le tabelle `TAS_estrazioni`
--
ALTER TABLE `TAS_estrazioni`
  ADD PRIMARY KEY (`est_id`),
  ADD KEY `est_car_id` (`est_car_id`),
  ADD KEY `est_num_id` (`est_num_id`);

--
-- Indici per le tabelle `TAS_gestori`
--
ALTER TABLE `TAS_gestori`
  ADD PRIMARY KEY (`ges_id`),
  ADD UNIQUE KEY `ges_username` (`ges_username`);

--
-- Indici per le tabelle `TAS_giocate`
--
ALTER TABLE `TAS_giocate`
  ADD PRIMARY KEY (`gio_id`),
  ADD KEY `gio_ute_id` (`gio_ute_id`),
  ADD KEY `gio_tom_id` (`gio_tom_id`);

--
-- Indici per le tabelle `TAS_numeriestratti`
--
ALTER TABLE `TAS_numeriestratti`
  ADD PRIMARY KEY (`num_id`);

--
-- Indici per le tabelle `TAS_tipovincite`
--
ALTER TABLE `TAS_tipovincite`
  ADD PRIMARY KEY (`tip_id`);

--
-- Indici per le tabelle `TAS_tombolate`
--
ALTER TABLE `TAS_tombolate`
  ADD PRIMARY KEY (`tom_id`);

--
-- Indici per le tabelle `TAS_utenti`
--
ALTER TABLE `TAS_utenti`
  ADD PRIMARY KEY (`utente_id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indici per le tabelle `TAS_vincite`
--
ALTER TABLE `TAS_vincite`
  ADD PRIMARY KEY (`vin_id`),
  ADD KEY `vin_tip_id` (`vin_tip_id`),
  ADD KEY `vin_car_id` (`vin_car_id`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `TAS_cartelle`
--
ALTER TABLE `TAS_cartelle`
  MODIFY `car_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT per la tabella `TAS_estrazioni`
--
ALTER TABLE `TAS_estrazioni`
  MODIFY `est_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT per la tabella `TAS_gestori`
--
ALTER TABLE `TAS_gestori`
  MODIFY `ges_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT per la tabella `TAS_giocate`
--
ALTER TABLE `TAS_giocate`
  MODIFY `gio_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT per la tabella `TAS_numeriestratti`
--
ALTER TABLE `TAS_numeriestratti`
  MODIFY `num_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT per la tabella `TAS_tipovincite`
--
ALTER TABLE `TAS_tipovincite`
  MODIFY `tip_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT per la tabella `TAS_tombolate`
--
ALTER TABLE `TAS_tombolate`
  MODIFY `tom_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT per la tabella `TAS_utenti`
--
ALTER TABLE `TAS_utenti`
  MODIFY `utente_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `TAS_vincite`
--
ALTER TABLE `TAS_vincite`
  MODIFY `vin_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
