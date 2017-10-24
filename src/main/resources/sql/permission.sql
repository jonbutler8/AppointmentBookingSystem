CREATE TABLE `permission`
    (`id` INTEGER PRIMARY KEY,
     `userTypeID` INTEGER NOT NULL,
     `permissionKeyID` INTEGER NOT NULL,
     `value` INTEGER NOT NULL,
     FOREIGN KEY(`userTypeID`) REFERENCES `userType`(`id`),
     FOREIGN KEY(`permissionKeyID`) REFERENCES `permissionKey`(`id`))