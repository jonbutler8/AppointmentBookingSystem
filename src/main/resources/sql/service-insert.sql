-- Lily Massage
INSERT INTO `service` (`id`,`name`,`businessID`)
VALUES (NULL, 'Full Body Massage',
  (SELECT `id` FROM `business` WHERE `name` = 'Lily Massage'));
INSERT INTO `service` (`id`,`name`,`businessID`)
VALUES (NULL, 'Neck Massage',
  (SELECT `id` FROM `business` WHERE `name` = 'Lily Massage'));
INSERT INTO `service` (`id`,`name`,`businessID`)
VALUES (NULL, 'Back Massage',
  (SELECT `id` FROM `business` WHERE `name` = 'Lily Massage'));
INSERT INTO `service` (`id`,`name`,`businessID`)
VALUES (NULL, 'Foot Massage',
  (SELECT `id` FROM `business` WHERE `name` = 'Lily Massage'));
INSERT INTO `service` (`id`,`name`,`businessID`)
VALUES (NULL, 'Deep Tissue Massage',
  (SELECT `id` FROM `business` WHERE `name` = 'Lily Massage'));
-- Sakura Massage
INSERT INTO `service` (`id`,`name`,`businessID`)
VALUES (NULL, 'Full Body Massage',
  (SELECT `id` FROM `business` WHERE `name` = 'Sakura Massage'));
INSERT INTO `service` (`id`,`name`,`businessID`)
VALUES (NULL, 'Neck Massage',
  (SELECT `id` FROM `business` WHERE `name` = 'Sakura Massage'))
