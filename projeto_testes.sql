-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 22-Jan-2022 às 17:56
-- Versão do servidor: 10.4.21-MariaDB
-- versão do PHP: 8.0.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `projeto_testes`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `login`
--

CREATE TABLE `login` (
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `tipo` varchar(20) DEFAULT 'cliente'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `login`
--

INSERT INTO `login` (`username`, `password`, `tipo`) VALUES
('admin', 'admin', 'admin'),
('Artur Mira', 'lghr8594', 'user'),
('DavidCapa', 'iodo546', 'admin'),
('GilbertoDias', 'gsjg34', 'empregado'),
('James', '546ffgg', 'user'),
('JoaoHorta', 'lrld998', 'empregado'),
('luisantunes', '1234', 'user'),
('user', 'user', 'user');

-- --------------------------------------------------------

--
-- Estrutura da tabela `produtos`
--

CREATE TABLE `produtos` (
  `id` int(10) UNSIGNED NOT NULL,
  `nome` varchar(100) NOT NULL,
  `preco` decimal(5,2) NOT NULL,
  `tipo` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `produtos`
--

INSERT INTO `produtos` (`id`, `nome`, `preco`, `tipo`) VALUES
(1, 'The Full English', '7.95', 'Breakfast'),
(2, 'The Vegetarian Breakfast', '6.25', 'Breakfast'),
(3, 'Scrambled Egg & Smoked Salmon', '6.25', 'Breakfast'),
(4, 'Round of Toast', '2.50', 'Breakfast'),
(5, 'Extra Bacon', '1.00', 'Breakfast'),
(6, 'Salmon Fish Cake', '8.95', 'Salad'),
(7, 'Herb Marinated Chicken Breast', '8.95', 'Salad'),
(8, 'Parmesan Crusted Fish Fillet', '8.95', 'Salad'),
(9, 'Grillerd Halloumi, Oilives & Sun Dried Tomato', '8.95', 'Salad'),
(10, 'Heath Beef Burger', '8.95', 'Burger'),
(11, 'Chilli Beef Burger', '8.95', 'Burger'),
(12, 'Herb Marinated Chicken Breast Burger', '8.95', 'Burger'),
(13, 'Grilled Halloumi Burger', '8.95', 'Burger'),
(14, 'Chicken Wrapped in Parma Ham', '9.25', 'Heath Favorites'),
(15, 'Beer Battered Fish & Chips', '9.50', 'Heath Favorites'),
(16, 'Traditional Beef Lasagne', '8.95', 'Heath Favorites'),
(17, 'Roasted Vegetable & Goats Cheese Lasagne', '8.95', 'Heath Favorites'),
(18, 'Chilli Beef Nachos', '7.95', 'Heath Favorites'),
(19, 'Roasted Vegetable Nachos', '5.95', 'Heath Favorites'),
(20, 'New York Style Steak Sandwich', '8.95', 'Open Sandwiches'),
(21, 'Heath Clucb Sandwich', '7.95', 'Open Sandwiches'),
(22, 'Bacon, Somerset Brie & Cranberry', '5.95', 'Open Sandwiches'),
(23, 'Roasted Vegetables & Goat Cheese', '5.95', 'Open Sandwiches');

--
-- Índices para tabelas despejadas
--

--
-- Índices para tabela `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`username`);

--
-- Índices para tabela `produtos`
--
ALTER TABLE `produtos`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `produtos`
--
ALTER TABLE `produtos`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
