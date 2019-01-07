CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `screen_name` varchar(255) DEFAULT NULL,
  `location` varchar(255),
  `friends_count` int(11),
  `followers_count` int(11),
  `is_verified` bool,
  `score` int(11)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;



