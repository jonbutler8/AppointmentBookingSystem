INSERT INTO `user`
    (`id`,`username`,`password`,`userTypeID`,`businessID`)
VALUES
    (NULL, 'superuser', 'password',
        (SELECT `id` FROM `userType` WHERE `name` = 'superUser'), 0);

INSERT INTO `user`
    (`id`,`username`,`password`,`userTypeID`,`businessID`)
VALUES
    (NULL, 'businessowner', 'password',
        (SELECT `id` FROM `userType` WHERE `name` = 'businessOwner'),
        (SELECT `id` FROM `business` WHERE `name` = 'Lily Massage'));

INSERT INTO `user`
    (`id`,`username`,`password`,`userTypeID`,`businessID`)
VALUES
    (NULL, 'customer', 'password',
        (SELECT `id` FROM `userType` WHERE `name` = 'customer'),
        (SELECT `id` FROM `business` WHERE `name` = 'Lily Massage'));

INSERT INTO `user`
    (`id`,`username`,`password`,`userTypeID`,`businessID`)
VALUES
    (NULL, 'businessowner2', 'password',
        (SELECT `id` FROM `userType` WHERE `name` = 'businessOwner'),
        (SELECT `id` FROM `business` WHERE `name` = 'Sakura Massage'))
