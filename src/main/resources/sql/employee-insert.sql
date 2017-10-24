INSERT INTO `employee`(`id`,`firstName`,`lastName`,`businessID`)
    VALUES (NULL, 'John', 'Smith',
    (SELECT `id` FROM `business` WHERE `name` = 'Lily Massage'));
INSERT INTO `employee`(`id`,`firstName`,`lastName`,`businessID`)
    VALUES (NULL, 'Jane', 'Thompson',
    (SELECT `id` FROM `business` WHERE `name` = 'Lily Massage'));
INSERT INTO `employee`(`id`,`firstName`,`lastName`,`businessID`)
    VALUES (NULL, 'Maria', 'Garcia',
    (SELECT `id` FROM `business` WHERE `name` = 'Lily Massage'));