-- phpMyAdmin SQL Dump
-- version 4.8.0
-- https://www.phpmyadmin.net/
--
-- Host: db
-- Generation Time: Dec 17, 2018 at 04:32 PM
-- Server version: 5.7.21
-- PHP Version: 7.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `TBD`
--

-- --------------------------------------------------------

--
-- Table structure for table `author`
--

CREATE TABLE `author` (
  `id` bigint(20) NOT NULL,
  `genre_id` bigint(20) NOT NULL,
  `hits` int(11) DEFAULT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `author`
--

INSERT INTO `author` (`id`, `genre_id`, `hits`, `name`) VALUES
(1, 1, 61757, 'Julio Verne'),
(2, 1, 34771, 'Miguel de Cervantes'),
(3, 1, 42412, 'Wilbur Smith'),
(4, 1, 26079, 'Robert Louis Stevenson'),
(5, 1, 8939, 'Mark Twain'),
(6, 2, 70753, 'Isaac Asimov'),
(7, 2, 58354, 'H. G. Wells'),
(8, 2, 25670, 'Ray Bradbury'),
(9, 2, 51858, 'Arthur C. Clarke'),
(10, 2, 40753, 'Philip K. Dick'),
(11, 3, 51205, 'Mario Vargas Llosa'),
(12, 3, 42953, 'Gabriel García Márquez'),
(13, 3, 51021, 'Julio Cortázar'),
(14, 3, 14735, 'Harper Lee'),
(15, 3, 57084, 'John Steinbeck'),
(16, 4, 51079, 'George R. R. Martin'),
(17, 4, 31172, 'J. R. R. Tolkien'),
(18, 4, 19915, 'Brandon Sanderson'),
(19, 4, 28333, 'C. S. Lewis'),
(20, 4, 68194, 'J. K. Rowling'),
(21, 5, 65088, 'Ildefonso Falcones'),
(22, 5, 39321, 'Arturo Pérez-Reverte'),
(23, 5, 60011, 'Santiago Posteguillo'),
(24, 5, 43675, 'Bernard Cornwell'),
(25, 5, 76575, 'Ken Follett'),
(26, 6, 45562, 'Marcela paz'),
(27, 6, 28168, 'Charles Perrault /Hermanos grimm'),
(28, 6, 36293, 'Oscar Wilde'),
(29, 6, 13417, 'Horacio Quiroga'),
(30, 6, 27325, 'Charles Dickens'),
(31, 7, 63884, 'Arthur Conan Doyle'),
(32, 7, 44618, 'Agatha Christie'),
(33, 7, 45812, 'Dashiell Hammett'),
(34, 7, 54087, 'Raymond Chandler'),
(35, 7, 63404, 'Patricia Highsmith'),
(36, 7, 15731, 'John le Carré'),
(37, 7, 10433, 'Henning Mankell'),
(38, 7, 12717, 'Camilla Läckberg'),
(39, 7, 9210, 'Osvaldo Soriano'),
(40, 7, 12713, 'Jeff Lindsay'),
(41, 7, 38, 'Juan Pablo Escobar'),
(42, 7, 8747, 'Alfred Stewart'),
(43, 7, 12712, 'Joel Dicker'),
(44, 7, 14593, 'Sue Grafton'),
(45, 7, 12130, 'Jorge Eduardo Benavides'),
(46, 7, 12715, 'Alfonso del Río'),
(47, 8, 33083, 'Johanna Lindsey'),
(48, 8, 48381, 'Federico Moccia'),
(49, 8, 33644, 'Nora Roberts'),
(50, 8, 38267, 'Lisa Kleypas'),
(51, 8, 75744, 'Megan Maxwell'),
(52, 9, 61775, 'Edgar Allan Poe'),
(53, 9, 65475, 'H.P. Lovecraft'),
(54, 9, 66065, 'Stephen King'),
(55, 9, 89, 'John Ajvide Lindqvist'),
(56, 9, 42223, 'Charles L. Grant'),
(57, 9, 10575, 'Enrique Laso'),
(58, 9, 9158, 'Bram Stoker');

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

CREATE TABLE `book` (
  `id` bigint(20) NOT NULL,
  `author_id` bigint(20) NOT NULL,
  `genre_id` bigint(20) NOT NULL,
  `hits` int(11) DEFAULT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `book`
--

INSERT INTO `book` (`id`, `author_id`, `genre_id`, `hits`, `name`) VALUES
(36, 7, 2, 13178, 'El alimento de los dioses'),
(35, 7, 2, 11512, 'Los primeros hombres en la luna'),
(34, 7, 2, 7323, 'La isla del doctor Moreau'),
(33, 7, 2, 13704, 'La guerra de los mundos'),
(32, 7, 2, 5128, 'El hombre invisible'),
(31, 7, 2, 7509, 'La máquina del tiempo'),
(30, 6, 2, 2651, 'Los propios dioses'),
(29, 6, 2, 5238, 'El sol desnudo'),
(28, 6, 2, 15150, 'El fin de la eternidad'),
(27, 6, 2, 10067, 'Las bóvedas de acero'),
(26, 6, 2, 24935, 'Yo, Robot'),
(25, 6, 2, 12712, 'Trilogía de la Fundación'),
(24, 5, 1, 8, 'Tom Sawyer'),
(23, 5, 1, 0, 'Huckleberry Finn'),
(22, 5, 1, 8931, 'Príncipe y Mendigo'),
(21, 4, 1, 7320, 'La Isla Del Tesoro'),
(20, 4, 1, 6708, 'La Flecha Negra'),
(19, 3, 1, 2, 'Zorro Dorado'),
(18, 3, 1, 13699, 'La Ruta De Los Vengadores'),
(17, 3, 1, 13, 'Horizonte Azul'),
(16, 3, 1, 5987, 'El Triunfo del Sol'),
(15, 3, 1, 5839, 'El Soberano del Nilo'),
(14, 3, 1, 5088, 'El Séptimo Papiro'),
(13, 3, 1, 5932, 'El Faro del Fin del Mundo'),
(12, 3, 1, 5852, 'El Destino del Cazador'),
(11, 2, 1, 6704, 'La Galatea'),
(10, 2, 1, 15347, 'Los trabajos de Persiles y Sigismunda'),
(9, 2, 1, 12720, 'Don Quijote de la Mancha'),
(8, 1, 1, 9, 'Miguel Strogoff'),
(7, 1, 1, 3573, 'Los Hijos del Capitán Grant'),
(6, 1, 1, 11221, 'La Vuelta al Mundo en 80 Días'),
(5, 1, 1, 6704, 'La Isla Misteriosa'),
(4, 1, 1, 9162, 'Escuela de Robinsones'),
(3, 1, 1, 16682, 'De la Tierra a la Luna'),
(2, 1, 1, 5240, 'Cinco Semanas En Globo'),
(1, 1, 1, 9166, '20.000 Leguas de Viaje Submarino'),
(37, 8, 2, 2, 'Crónicas marcianas'),
(38, 8, 2, 0, 'Fahrenheit 451'),
(39, 8, 2, 5128, 'El hombre ilustrado'),
(40, 8, 2, 12712, 'De la ceniza volverás'),
(41, 8, 2, 5873, 'El Vino Del Estío'),
(42, 8, 2, 1955, 'Remedio para Melancólicos'),
(43, 9, 2, 2211, 'Una odisea espacial'),
(44, 9, 2, 9379, 'Luz de otros días'),
(45, 9, 2, 12715, 'Cánticos de la lejana Tierra'),
(46, 9, 2, 2651, 'Las fuentes del paraíso'),
(47, 9, 2, 12554, 'Expedición a la Tierra'),
(48, 9, 2, 12348, 'El martillo de Dios'),
(49, 10, 2, 5877, 'El hombre del castillo'),
(50, 10, 2, 5787, 'Sueñan los androides con ovejas eléctricas'),
(51, 10, 2, 0, 'Palmer Eldritch'),
(52, 10, 2, 9309, 'Tiempo de Marte'),
(53, 10, 2, 6177, 'Fluyan mis lágrimas, dijo el policía'),
(54, 10, 2, 13603, 'Una mirada a la oscuridad'),
(55, 11, 3, 14205, 'La ciudad y los perros'),
(56, 11, 3, 7003, 'La casa verde'),
(57, 11, 3, 9899, 'Pantaleón y las visitadoras'),
(58, 11, 3, 7351, 'La fiesta del Chivo'),
(59, 11, 3, 12747, 'Travesuras de la niña mala'),
(60, 12, 3, 9441, 'Cien años de soledad '),
(61, 12, 3, 9842, 'Del amor y otros demonios '),
(62, 12, 3, 11128, 'El amor en los tiempos del cólera '),
(63, 12, 3, 5838, 'El otoño del patriarca '),
(64, 12, 3, 6704, 'La hojarasca'),
(65, 13, 3, 14602, 'Historias de cronopios y de famas'),
(66, 13, 3, 7676, 'Todos los fuegos el fuego'),
(67, 13, 3, 12479, 'El perseguidor y otros cuentos del cine'),
(68, 13, 3, 5097, 'Salvo el crepúsculo'),
(69, 13, 3, 1994, '62 Modelo para armar'),
(70, 13, 3, 9173, 'Libro de Manuel'),
(71, 14, 3, 3638, 'Matar un ruiseñor '),
(72, 14, 3, 11097, 'Ve y pon un centinela '),
(73, 15, 3, 9526, 'La luna se ha puesto'),
(74, 15, 3, 5120, 'El autobús perdido'),
(75, 15, 3, 14363, 'El invierno de mi descontento'),
(76, 15, 3, 14641, 'De ratones y hombres'),
(77, 15, 3, 13434, 'Las uvas de la ira'),
(78, 16, 4, 14621, 'Canción de hielo y fuego'),
(79, 16, 4, 9158, 'Danza de dragones'),
(80, 16, 4, 9196, 'Sueño de primavera '),
(81, 16, 4, 8943, 'Fuego y sangre'),
(82, 16, 4, 9161, 'Tormenta de espadas'),
(83, 17, 4, 5088, 'El Silmarillion'),
(84, 17, 4, 0, 'Roverandom'),
(85, 17, 4, 7801, 'La batalla del Campo del Este'),
(86, 17, 4, 5088, 'El hobbit'),
(87, 17, 4, 13195, 'El Señor de los Anillos'),
(88, 18, 4, 13180, 'El camino de los reyes'),
(89, 18, 4, 1, 'Juramentada '),
(90, 18, 4, 6705, 'La Espada Infinita'),
(91, 18, 4, 29, 'Palabras Radiantes'),
(92, 18, 4, 0, 'steelheart'),
(93, 19, 4, 0, 'Trilogía cósmica'),
(94, 19, 4, 10069, 'Las crónicas de Narnia'),
(95, 19, 4, 5088, 'El príncipe Caspian'),
(96, 19, 4, 5846, 'El sobrino del mago'),
(97, 19, 4, 7330, 'La travesía del Viajero del Alba'),
(98, 20, 4, 9181, 'Animales fantásticos y dónde encontrarlos'),
(99, 20, 4, 13144, 'Harry Potter y la piedra filosofal'),
(100, 20, 4, 17351, 'Harry Potter y las Reliquias de la Muerte'),
(101, 20, 4, 12032, 'Harry Potter y el legado maldito'),
(102, 20, 4, 16486, 'Harry Potter y el prisionero de Azkaban'),
(103, 21, 5, 15467, 'La luna y el advenedizo'),
(104, 21, 5, 7329, 'La catedral del mar'),
(105, 21, 5, 12725, 'La mano de Fátima'),
(106, 21, 5, 6709, 'La reina descalza'),
(107, 21, 5, 13700, 'Los herederos de la tierra'),
(108, 21, 5, 9158, 'de erfgenamen'),
(109, 22, 5, 12130, 'El maestro de esgrima'),
(110, 22, 5, 12712, 'La tabla de Flandes'),
(111, 22, 5, 5093, 'El club Dumas'),
(112, 22, 5, 2651, 'Las aventuras del capitán Alatriste'),
(113, 22, 5, 6712, 'La carta esférica'),
(114, 22, 5, 23, 'Cabo Trafalgar'),
(115, 23, 5, 6709, 'La legión perdida'),
(116, 23, 5, 13703, 'La sangre de los libros'),
(117, 23, 5, 19255, 'La noche en que Frankenstein leyó El Quijote'),
(118, 23, 5, 5901, 'Africanus: el hijo del cónsul'),
(119, 23, 5, 1717, 'Las legiones malditas'),
(120, 23, 5, 12726, 'La traición de Roma'),
(121, 24, 5, 13120, 'Crónicas del Señor de la Guerra'),
(122, 24, 5, 5851, 'El rey del invierno'),
(123, 24, 5, 5089, 'El trono vacante VIII'),
(124, 24, 5, 1055, 'Arqueros del rey'),
(125, 24, 5, 12712, 'Guerreros de la tormenta'),
(126, 24, 5, 5848, 'Svein el del caballo blanco'),
(127, 25, 5, 13700, 'Los pilares de la Tierra'),
(128, 25, 5, 4405, 'Un mundo sin fin'),
(129, 25, 5, 10311, 'Una columna de fuego'),
(130, 25, 5, 13700, 'La caída de los gigantes'),
(131, 25, 5, 5898, 'El invierno del mundo'),
(132, 25, 5, 15129, 'El umbral de la eternidad'),
(133, 25, 5, 13432, 'La isla de las tormentas'),
(134, 26, 6, 1, 'Papelucho'),
(135, 26, 6, 5096, 'El soldadito rojo'),
(136, 26, 6, 2645, 'Los Pecosos'),
(137, 26, 6, 15928, 'A pesar de mi tía'),
(138, 26, 6, 9172, 'Caramelos de luz'),
(139, 26, 6, 12720, 'La vuelta de Sebastián'),
(140, 27, 6, 6708, 'La caperucita Roja'),
(141, 27, 6, 5, 'Pulgarcito'),
(142, 27, 6, 6704, 'La Cenicienta'),
(143, 27, 6, 6709, 'La bella durmiente'),
(144, 27, 6, 8042, 'El gato con botas'),
(145, 28, 6, 5095, 'El gigante egoísta'),
(146, 28, 6, 5319, 'El príncipe feliz'),
(147, 28, 6, 15468, 'El ruiseñor y la rosa'),
(148, 28, 6, 5320, 'El amigo fiel'),
(149, 28, 6, 5091, 'El famoso cohete'),
(150, 29, 6, 6704, 'La Gama Ciega'),
(151, 29, 6, 6711, 'La Tortuga Gigante'),
(152, 29, 6, 2, 'Potro Salvaje'),
(153, 30, 6, 2, 'Oliver Twist'),
(154, 30, 6, 6711, 'La pequeña Dorrit'),
(155, 30, 6, 5116, 'El Armario Viejo'),
(156, 30, 6, 5102, 'El Velo Negro'),
(157, 30, 6, 10394, 'Una canción de Navidad'),
(158, 31, 7, 5225, 'Estudio en escarlata'),
(159, 31, 7, 13182, 'El Signo de los Cuatro'),
(160, 31, 7, 10067, 'Las aventuras de Sherlock Holmes'),
(161, 31, 7, 10067, 'Las Memorias de Sherlock Holmes'),
(162, 31, 7, 13213, 'El Perro de los Baskerville'),
(163, 31, 7, 12130, 'El Regreso de Sherlock Holmes'),
(164, 32, 7, 6, 'Diez negritos'),
(165, 32, 7, 12155, 'El misterioso caso de Styles'),
(166, 32, 7, 8748, 'Asesinato en el orient express'),
(167, 32, 7, 163, 'Tres ratones ciegos'),
(168, 32, 7, 8766, 'Muerte en el Nilo'),
(169, 32, 7, 12129, 'El asesinato de Roger Acroyd'),
(170, 32, 7, 2651, 'Los cinco cerditos'),
(171, 33, 7, 5088, 'El halcón maltés'),
(172, 33, 7, 7, 'Cosecha roja'),
(173, 33, 7, 5133, 'El hombre delgado'),
(174, 33, 7, 12712, 'La llave de cristal'),
(175, 33, 7, 13704, 'La maldición de los Dain'),
(176, 33, 7, 9168, 'Ciudad de pesadilla'),
(177, 34, 7, 5128, 'El largo adiós'),
(178, 34, 7, 7320, 'La dama del lago'),
(179, 34, 7, 6704, 'La ventana siniestra'),
(180, 34, 7, 12151, 'El simple arte de matar'),
(181, 34, 7, 12732, 'La historia de Poodle Springs'),
(182, 34, 7, 10052, 'Asesino en la lluvia'),
(183, 35, 7, 12132, 'El talento de Mr. Ripley'),
(184, 35, 7, 7867, 'Extraños en un tren'),
(185, 35, 7, 12136, 'El diario de Edith'),
(186, 35, 7, 18557, 'El muchacho que siguió a Ripley'),
(187, 35, 7, 12712, 'La máscara de Ripley'),
(188, 36, 7, 15731, 'El espía que surgió del frío'),
(189, 37, 7, 10433, 'Los perros de Riga'),
(190, 38, 7, 12717, 'La princesa de hielo'),
(191, 39, 7, 9210, 'Triste solitario y final '),
(192, 40, 7, 12713, 'La muerte de Dexter'),
(193, 41, 7, 38, 'Pablo Escobar In Fraganti'),
(194, 42, 7, 8747, 'Asesinato en el laberinto'),
(195, 43, 7, 12712, 'La desaparición de Stephanie Mailer'),
(196, 44, 7, 14593, 'Y de Yesterday'),
(197, 45, 7, 12130, 'El asesinato de Laura Olivo'),
(198, 46, 7, 12715, 'La ciudad de la lluvia'),
(199, 47, 8, 16611, 'No traiciones a mi corazón'),
(200, 47, 8, 282, 'Corazón indómito'),
(201, 47, 8, 8, 'Tempestad salvaje'),
(202, 47, 8, 518, 'Ríndete, amor mío'),
(203, 47, 8, 2231, 'Una dulce enemistad'),
(204, 47, 8, 13433, 'Las trampas de la seducción'),
(205, 48, 8, 12022, 'A tres metros sobre el cielo'),
(206, 48, 8, 3777, 'Carolina se enamora'),
(207, 48, 8, 16597, 'Esta noche dime que me quieres'),
(208, 48, 8, 11362, 'Perdona pero quiero irme a Roma contigo'),
(209, 48, 8, 4623, 'Perdona pero quiero casarme contigo'),
(210, 49, 8, 1, 'Aurora Boreal'),
(211, 49, 8, 5838, 'El estigma del arrecife'),
(212, 49, 8, 1, 'Legado mágico'),
(213, 49, 8, 2241, 'Una Muerte inmortal'),
(214, 49, 8, 13432, 'Las estrellas de la fortuna'),
(215, 49, 8, 12131, 'El bosque de Hollow'),
(216, 50, 8, 6708, 'La antigua magia'),
(217, 50, 8, 5498, 'El diablo tiene ojos azules'),
(218, 50, 8, 8951, 'Un extraño en mis brazos'),
(219, 50, 8, 6968, 'Mi nombre es Liberty'),
(220, 50, 8, 8751, 'El diablo en invierno'),
(221, 50, 8, 1391, 'Seducción al amanecer'),
(222, 51, 8, 4096, 'Una flor para otra flor'),
(223, 51, 8, 25084, 'Yo soy Eric Zimmerman'),
(224, 51, 8, 28038, 'Pídeme lo que quieras y yo te lo daré'),
(225, 51, 8, 11893, 'Soy una mamá divorciada y alocada'),
(226, 51, 8, 3281, 'Adivina quién soy esta noche'),
(227, 51, 8, 3352, 'Los príncipes azules también destiñen'),
(228, 52, 9, 5141, 'El gato negro'),
(229, 52, 9, 12130, 'El escarabajo de oro'),
(230, 52, 9, 5209, 'El corazón delator'),
(231, 52, 9, 12872, 'La caída de la casa Usher'),
(232, 52, 9, 12714, 'La máscara de la muerte roja'),
(233, 52, 9, 13709, 'Los crímenes de la calle Morgue'),
(234, 53, 9, 6446, 'Las ratas en las paredes'),
(235, 53, 9, 15225, 'En las montañas de la locura'),
(236, 53, 9, 6798, 'La sombra sobre Innsmouth'),
(237, 53, 9, 12714, 'La llamada de Cthulhu'),
(238, 53, 9, 12134, 'El horror de Dunwich'),
(239, 53, 9, 12158, 'El caso de Charles Dexter Ward'),
(240, 54, 9, 12131, 'El misterio de Salem’s Lot'),
(241, 54, 9, 5843, 'El nacimiento del pistolero'),
(242, 54, 9, 12712, 'La Saga de la Torre Oscura'),
(243, 54, 9, 12468, 'El viento por la cerradura'),
(244, 54, 9, 9193, 'Canción de Susannah'),
(245, 54, 9, 13718, 'La llegada de los tres'),
(246, 55, 9, 49, 'Déjame entrar'),
(247, 55, 9, 40, 'Puerto humano'),
(248, 56, 9, 9168, 'Viento de sangre'),
(249, 56, 9, 148, 'Expediente X: Duendes'),
(250, 56, 9, 7326, 'La última llamada del luto'),
(251, 56, 9, 10452, 'Una tranquila noche de terror'),
(252, 56, 9, 15129, 'El susurro de la medianoche'),
(253, 57, 9, 10575, 'Desde el infierno (la trilogía)'),
(254, 57, 9, 0, 'Crímenes diabólicos'),
(255, 58, 9, 9158, 'Dracula de Bram Stoker'),
(256, 4, 9, 12051, 'doctor Jekyll y el señor Hyde');

-- --------------------------------------------------------

--
-- Table structure for table `genre`
--

CREATE TABLE `genre` (
  `id` bigint(20) NOT NULL,
  `hits` int(11) DEFAULT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `genre`
--

INSERT INTO `genre` (`id`, `hits`, `name`) VALUES
(1, 173958, 'aventura'),
(2, 247388, 'ciencia ficcion'),
(3, 216998, 'drama'),
(4, 198693, 'fantasia'),
(5, 284670, 'historico'),
(6, 150765, 'infantil'),
(7, 393544, 'policiaca'),
(8, 229119, 'romance'),
(9, 255360, 'terror');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `author`
--
ALTER TABLE `author`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_or6k6jmywerxbme223c988bmg` (`name`);

--
-- Indexes for table `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_wugryet8mf6oi28n00x2eoc4` (`name`);

--
-- Indexes for table `genre`
--
ALTER TABLE `genre`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_ctffrbu4484ft8dlsa5vmqdka` (`name`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
