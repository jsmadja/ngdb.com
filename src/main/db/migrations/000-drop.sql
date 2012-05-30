DROP TABLE IF EXISTS ArticlePictures;
DROP TABLE IF EXISTS ArticleTags;
DROP TABLE IF EXISTS CollectionObject;
DROP TABLE IF EXISTS HardwareShopItem;
DROP TABLE IF EXISTS GameGenres;
DROP TABLE IF EXISTS GameShopItem;
DROP TABLE IF EXISTS Game;
DROP TABLE IF EXISTS Hardware;
DROP TABLE IF EXISTS Genre;
DROP TABLE IF EXISTS Origin;
DROP TABLE IF EXISTS Platform;
DROP TABLE IF EXISTS Publisher;
DROP TABLE IF EXISTS State;
DROP TABLE IF EXISTS Tag;
DROP TABLE IF EXISTS Note;
DROP TABLE IF EXISTS Review;
DROP TABLE IF EXISTS Wish;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Comment;
DROP TABLE IF EXISTS Box;
DROP TABLE IF EXISTS hibernate_sequences;

INSERT INTO `Box` (`id`,`creation_date`,`name`,`modification_date`) VALUES (1,'2012-05-17 15:53:09','Hard','2012-05-17 15:53:09');
INSERT INTO `Box` (`id`,`creation_date`,`name`,`modification_date`) VALUES (2,'2012-05-17 15:53:09','Soft','2012-05-17 15:53:09');
INSERT INTO `Box` (`id`,`creation_date`,`name`,`modification_date`) VALUES (3,'2012-05-17 15:53:09','Cart','2012-05-17 15:53:09');

INSERT INTO `Genre` (`id`,`creation_date`,`title`,`modification_date`) VALUES (1,'2012-05-17 15:53:09','Sports','2012-05-17 15:53:09');

INSERT INTO `Origin` (`id`,`creation_date`,`title`,`modification_date`) VALUES (1,'2012-05-17 15:53:09','Japan','2012-05-17 15:53:09');
INSERT INTO `Origin` (`id`,`creation_date`,`title`,`modification_date`) VALUES (2,'2012-05-17 15:53:09','America','2012-05-17 15:53:09');
INSERT INTO `Origin` (`id`,`creation_date`,`title`,`modification_date`) VALUES (3,'2012-05-17 15:53:09','Europe','2012-05-17 15:53:09');
INSERT INTO `Origin` (`id`,`creation_date`,`title`,`modification_date`) VALUES (4,'2012-05-17 15:53:09','Korea','2012-05-17 15:53:09');

INSERT INTO `Platform` (`id`,`creation_date`,`name`,`modification_date`) VALUES (1,'2012-05-17 15:37:21','MVS','2012-05-17 15:37:21');
INSERT INTO `Platform` (`id`,`creation_date`,`name`,`modification_date`) VALUES (2,'2012-05-17 15:37:21','AES','2012-05-17 15:37:21');
INSERT INTO `Platform` (`id`,`creation_date`,`name`,`modification_date`) VALUES (3,'2012-05-17 15:37:21','CD','2012-05-17 15:37:21');
INSERT INTO `Platform` (`id`,`creation_date`,`name`,`modification_date`) VALUES (4,'2012-05-17 15:37:21','NGP','2012-05-17 15:37:21');
INSERT INTO `Platform` (`id`,`creation_date`,`name`,`modification_date`) VALUES (5,'2012-05-17 15:37:21','NGPC','2012-05-17 15:37:21');
INSERT INTO `Platform` (`id`,`creation_date`,`name`,`modification_date`) VALUES (6,'2012-05-17 15:37:21','64','2012-05-17 15:37:21');

INSERT INTO `Publisher` (`id`,`creation_date`,`name`,`modification_date`) VALUES (1,'2012-05-17 15:53:09','Data East','2012-05-17 15:53:09');
INSERT INTO `Publisher` (`id`,`creation_date`,`name`,`modification_date`) VALUES (2,'2012-05-17 15:53:09','SNK','2012-05-17 15:53:09');
INSERT INTO `Publisher` (`id`,`creation_date`,`name`,`modification_date`) VALUES (3,'2012-05-17 15:53:09','ADK','2012-05-17 15:53:09');
INSERT INTO `Publisher` (`id`,`creation_date`,`name`,`modification_date`) VALUES (4,'2012-05-17 15:53:09','Monolith','2012-05-17 15:53:09');
INSERT INTO `Publisher` (`id`,`creation_date`,`name`,`modification_date`) VALUES (5,'2012-05-17 15:53:09','Wave','2012-05-17 15:53:09');
INSERT INTO `Publisher` (`id`,`creation_date`,`name`,`modification_date`) VALUES (6,'2012-05-17 15:53:09','Visco','2012-05-17 15:53:09');
INSERT INTO `Publisher` (`id`,`creation_date`,`name`,`modification_date`) VALUES (7,'2012-05-17 15:53:09','Sammy','2012-05-17 15:53:09');
INSERT INTO `Publisher` (`id`,`creation_date`,`name`,`modification_date`) VALUES (8,'2012-05-17 15:53:09','Face','2012-05-17 15:53:09');
INSERT INTO `Publisher` (`id`,`creation_date`,`name`,`modification_date`) VALUES (9,'2012-05-17 15:53:09','Viccom','2012-05-17 15:53:09');
INSERT INTO `Publisher` (`id`,`creation_date`,`name`,`modification_date`) VALUES (10,'2012-05-17 15:53:09','Aicom','2012-05-17 15:53:09');
INSERT INTO `Publisher` (`id`,`creation_date`,`name`,`modification_date`) VALUES (11,'2012-05-17 15:53:09','Video System','2012-05-17 15:53:09');
INSERT INTO `Publisher` (`id`,`creation_date`,`name`,`modification_date`) VALUES (12,'2012-05-17 15:53:09','NMK','2012-05-17 15:53:09');
INSERT INTO `Publisher` (`id`,`creation_date`,`name`,`modification_date`) VALUES (13,'2012-05-17 15:53:09','Taito','2012-05-17 15:53:09');
INSERT INTO `Publisher` (`id`,`creation_date`,`name`,`modification_date`) VALUES (14,'2012-05-17 15:53:09','Eighting','2012-05-17 15:53:09');
INSERT INTO `Publisher` (`id`,`creation_date`,`name`,`modification_date`) VALUES (15,'2012-05-17 15:53:09','Sunsoft','2012-05-17 15:53:09');
INSERT INTO `Publisher` (`id`,`creation_date`,`name`,`modification_date`) VALUES (16,'2012-05-17 15:53:09','Saurus','2012-05-17 15:53:09');
INSERT INTO `Publisher` (`id`,`creation_date`,`name`,`modification_date`) VALUES (17,'2012-05-17 15:53:09','Technos','2012-05-17 15:53:09');
INSERT INTO `Publisher` (`id`,`creation_date`,`name`,`modification_date`) VALUES (18,'2012-05-17 15:53:09','Hudson Soft','2012-05-17 15:53:09');
INSERT INTO `Publisher` (`id`,`creation_date`,`name`,`modification_date`) VALUES (19,'2012-05-17 15:53:09','Takara','2012-05-17 15:53:09');
INSERT INTO `Publisher` (`id`,`creation_date`,`name`,`modification_date`) VALUES (20,'2012-05-17 15:53:09','Nazca','2012-05-17 15:53:09');
INSERT INTO `Publisher` (`id`,`creation_date`,`name`,`modification_date`) VALUES (21,'2012-05-17 15:53:09','Yumekobo','2012-05-17 15:53:09');
INSERT INTO `Publisher` (`id`,`creation_date`,`name`,`modification_date`) VALUES (22,'2012-05-17 15:53:09','Psikyo','2012-05-17 15:53:09');
INSERT INTO `Publisher` (`id`,`creation_date`,`name`,`modification_date`) VALUES (23,'2012-05-17 15:53:09','Gavaking','2012-05-17 15:53:09');
INSERT INTO `Publisher` (`id`,`creation_date`,`name`,`modification_date`) VALUES (24,'2012-05-17 15:53:09','SNK PLAYMORE','2012-05-17 15:53:09');
INSERT INTO `Publisher` (`id`,`creation_date`,`name`,`modification_date`) VALUES (25,'2012-05-17 15:53:09','Evoga','2012-05-17 15:53:09');
INSERT INTO `Publisher` (`id`,`creation_date`,`name`,`modification_date`) VALUES (26,'2012-05-17 15:53:09','ATLUS/NOISE FACTORY','2012-05-17 15:53:09');
INSERT INTO `Publisher` (`id`,`creation_date`,`name`,`modification_date`) VALUES (27,'2012-05-17 15:53:09','SNK PLAYMORE/Yuki Enterprise','2012-05-17 15:53:09');
INSERT INTO `Publisher` (`id`,`creation_date`,`name`,`modification_date`) VALUES (28,'2012-05-17 15:53:09','Aiky','2012-05-17 15:53:09');

INSERT INTO `State` (`id`,`creation_date`,`modification_date`,`title`) VALUES (1,'2000-00-00 00:00:00','2001-00-00 00:00:00','Mint');
INSERT INTO `State` (`id`,`creation_date`,`modification_date`,`title`) VALUES (2,'2000-00-00 00:00:00','2001-00-00 00:00:00','Near Mint');
INSERT INTO `State` (`id`,`creation_date`,`modification_date`,`title`) VALUES (3,'2000-00-00 00:00:00','2001-00-00 00:00:00','Sealed');
INSERT INTO `State` (`id`,`creation_date`,`modification_date`,`title`) VALUES (4,'2000-00-00 00:00:00','2001-00-00 00:00:00','Used');
INSERT INTO `State` (`id`,`creation_date`,`modification_date`,`title`) VALUES (5,'2000-00-00 00:00:00','2001-00-00 00:00:00','Loose');

INSERT INTO `User` (`id`, `creation_date`, `modification_date`, `login`) VALUES (1, '2000-00-00', '2000-00-00', 'jsmadja');
