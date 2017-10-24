CREATE TABLE `user`
    (`id` INTEGER,
     `username` TEXT        NOT NULL,
     `password` TEXT        NOT NULL,
     `userTypeID` INTEGER   NOT NULL,
     `businessID` INTEGER   NOT NULL,
     PRIMARY KEY(`id`),
     FOREIGN KEY(`userTypeID`) REFERENCES `userType`(`id`),
     FOREIGN KEY(`businessID`) REFERENCES `business`(`id`))