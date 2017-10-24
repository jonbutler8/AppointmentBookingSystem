INSERT INTO `customer`(`userID`,`firstName`,`lastName`,`phoneNumber`,`address`,`businessID`)
VALUES ((SELECT `id` FROM `user` WHERE `username` = 'customer'),
'Customer', 'User', '0123456789', '123 Fake Street',
(SELECT `id` FROM `business` WHERE `name` = 'Lily Massage'))