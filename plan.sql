-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : lun. 13 mai 2024 à 15:36
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `plan`
--

-- --------------------------------------------------------

--
-- Structure de la table `myplans`
--

CREATE TABLE `myplans` (
  `planID` int(11) NOT NULL,
  `plan` varchar(250) NOT NULL,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL,
  `dateCreated` date NOT NULL,
  `status` varchar(250) NOT NULL,
  `planner` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `myplans`
--

INSERT INTO `myplans` (`planID`, `plan`, `startDate`, `endDate`, `dateCreated`, `status`, `planner`) VALUES
(26, 'projet java', '2024-05-01', '2024-05-09', '2024-05-13', 'Finished', 'fekher'),
(27, 'projet php', '2024-05-01', '2024-05-09', '2024-05-13', 'Not Finish', 'ahmed'),
(29, 'projet php', '2024-05-02', '2024-05-04', '2024-05-13', 'Finished', 'ayoub');

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `date`) VALUES
(12, 'atef', '12341234', '2024-05-13'),
(13, 'sofien', '12341234', '2024-05-13');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `myplans`
--
ALTER TABLE `myplans`
  ADD PRIMARY KEY (`planID`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `myplans`
--
ALTER TABLE `myplans`
  MODIFY `planID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
