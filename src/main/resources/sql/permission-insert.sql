-- SuperUser
INSERT INTO `permission`
    (`id`,`userTypeID`,`permissionKeyId`,`value`)
VALUES (NULL,
    (SELECT `id` FROM `userType` WHERE `name` = 'superUser'),
    (SELECT `id` FROM `permissionKey` WHERE `key` = 'canGoToSUHome'), 1);
-- canGoToLogin
INSERT INTO `permission`
    (`id`,`userTypeID`,`permissionKeyId`,`value`)
VALUES (NULL,
    (SELECT `id` FROM `userType` WHERE `name` = 'businessOwner'),
    (SELECT `id` FROM `permissionKey` WHERE `key` = 'canGoToLogin'), 0);
INSERT INTO `permission`
    (`id`,`userTypeID`,`permissionKeyId`,`value`)
VALUES (NULL,
    (SELECT `id` FROM `userType` WHERE `name` = 'customer'),
    (SELECT `id` FROM `permissionKey` WHERE `key` = 'canGoToLogin'), 0);
-- canGoToRegister
INSERT INTO `permission`
    (`id`,`userTypeID`,`permissionKeyId`,`value`)
VALUES (NULL,
    (SELECT `id` FROM `userType` WHERE `name` = 'businessOwner'),
    (SELECT `id` FROM `permissionKey` WHERE `key` = 'canGoToRegister'), 0);
INSERT INTO `permission`
    (`id`,`userTypeID`,`permissionKeyId`,`value`)
VALUES (NULL,
    (SELECT `id` FROM `userType` WHERE `name` = 'customer'),
    (SELECT `id` FROM `permissionKey` WHERE `key` = 'canGoToRegister'), 0);
-- canGoToAddEmployee
INSERT INTO `permission`
    (`id`,`userTypeID`,`permissionKeyId`,`value`)
VALUES (NULL,
    (SELECT `id` FROM `userType` WHERE `name` = 'businessOwner'),
    (SELECT `id` FROM `permissionKey` WHERE `key` = 'canGoToAddEmployee'), 1);
INSERT INTO `permission`
    (`id`,`userTypeID`,`permissionKeyId`,`value`)
VALUES (NULL,
    (SELECT `id` FROM `userType` WHERE `name` = 'customer'),
    (SELECT `id` FROM `permissionKey` WHERE `key` = 'canGoToAddEmployee'), 0);
-- canGoToBOHome
INSERT INTO `permission`
    (`id`,`userTypeID`,`permissionKeyId`,`value`)
VALUES (NULL,
    (SELECT `id` FROM `userType` WHERE `name` = 'businessOwner'),
    (SELECT `id` FROM `permissionKey` WHERE `key` = 'canGoToBOHome'), 1);
INSERT INTO `permission`
    (`id`,`userTypeID`,`permissionKeyId`,`value`)
VALUES (NULL,
    (SELECT `id` FROM `userType` WHERE `name` = 'customer'),
    (SELECT `id` FROM `permissionKey` WHERE `key` = 'canGoToBOHome'), 0);
-- canGoToCustomerHome
INSERT INTO `permission`
    (`id`,`userTypeID`,`permissionKeyId`,`value`)
VALUES (NULL,
    (SELECT `id` FROM `userType` WHERE `name` = 'businessOwner'),
    (SELECT `id` FROM `permissionKey` WHERE `key` = 'canGoToCustomerHome'),
    0);
INSERT INTO `permission`
    (`id`,`userTypeID`,`permissionKeyId`,`value`)
VALUES (NULL,
    (SELECT `id` FROM `userType` WHERE `name` = 'customer'),
    (SELECT `id` FROM `permissionKey` WHERE `key` = 'canGoToCustomerHome'),
    1);
-- canGoToEmployeeTimes
INSERT INTO `permission`
    (`id`,`userTypeID`,`permissionKeyId`,`value`)
VALUES (NULL,
    (SELECT `id` FROM `userType` WHERE `name` = 'businessOwner'),
    (SELECT `id` FROM `permissionKey` WHERE `key` = 'canGoToEmployeeTimes'), 1);
INSERT INTO `permission`
    (`id`,`userTypeID`,`permissionKeyId`,`value`)
VALUES (NULL,
    (SELECT `id` FROM `userType` WHERE `name` = 'customer'),
    (SELECT `id` FROM `permissionKey` WHERE `key` = 'canGoToEmployeeTimes'), 0);
-- canGoToEmployeeService
INSERT INTO `permission`
    (`id`,`userTypeID`,`permissionKeyId`,`value`)
VALUES (NULL,
    (SELECT `id` FROM `userType` WHERE `name` = 'businessOwner'),
    (SELECT `id` FROM `permissionKey` WHERE `key` = 'canGoToEmployeeService'), 1);
INSERT INTO `permission`
    (`id`,`userTypeID`,`permissionKeyId`,`value`)
VALUES (NULL,
    (SELECT `id` FROM `userType` WHERE `name` = 'customer'),
    (SELECT `id` FROM `permissionKey` WHERE `key` = 'canGoToEmployeeService'), 0);
    -- canGoToBusinessHours
INSERT INTO `permission`
    (`id`,`userTypeID`,`permissionKeyId`,`value`)
VALUES (NULL,
    (SELECT `id` FROM `userType` WHERE `name` = 'businessOwner'),
    (SELECT `id` FROM `permissionKey` WHERE `key` = 'canGoToBusinessHours'), 1);
INSERT INTO `permission`
    (`id`,`userTypeID`,`permissionKeyId`,`value`)
VALUES (NULL,
    (SELECT `id` FROM `userType` WHERE `name` = 'customer'),
    (SELECT `id` FROM `permissionKey` WHERE `key` = 'canGoToBusinessHours'), 0);
        -- canGoToBusinessRegister
INSERT INTO `permission`
    (`id`,`userTypeID`,`permissionKeyId`,`value`)
VALUES (NULL,
    (SELECT `id` FROM `userType` WHERE `name` = 'businessOwner'),
    (SELECT `id` FROM `permissionKey` WHERE `key` = 'canGoToBusinessRegister'), 0);
INSERT INTO `permission`
    (`id`,`userTypeID`,`permissionKeyId`,`value`)
VALUES (NULL,
    (SELECT `id` FROM `userType` WHERE `name` = 'customer'),
    (SELECT `id` FROM `permissionKey` WHERE `key` = 'canGoToBusinessRegister'), 0);
    INSERT INTO `permission`
    (`id`,`userTypeID`,`permissionKeyId`,`value`)
VALUES (NULL,
    (SELECT `id` FROM `userType` WHERE `name` = 'superUser'),
    (SELECT `id` FROM `permissionKey` WHERE `key` = 'canGoToBusinessRegister'), 1);
            -- canGoToModifyServices
INSERT INTO `permission`
    (`id`,`userTypeID`,`permissionKeyId`,`value`)
VALUES (NULL,
    (SELECT `id` FROM `userType` WHERE `name` = 'businessOwner'),
    (SELECT `id` FROM `permissionKey` WHERE `key` = 'canGoToModifyServices'), 1);
INSERT INTO `permission`
    (`id`,`userTypeID`,`permissionKeyId`,`value`)
VALUES (NULL,
    (SELECT `id` FROM `userType` WHERE `name` = 'customer'),
    (SELECT `id` FROM `permissionKey` WHERE `key` = 'canGoToModifyServices'), 0);
    INSERT INTO `permission`
    (`id`,`userTypeID`,`permissionKeyId`,`value`)
VALUES (NULL,
    (SELECT `id` FROM `userType` WHERE `name` = 'superUser'),
    (SELECT `id` FROM `permissionKey` WHERE `key` = 'canGoToModifyServices'), 0)