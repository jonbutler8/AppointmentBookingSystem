CREATE TABLE `employee`
    (`id` INTEGER,
     `firstName` TEXT        NOT NULL,
     `lastName`  TEXT        NOT NULL,
     `businessID` INTEGER   NOT NULL,
     PRIMARY KEY(`id`),
     FOREIGN KEY(`businessID`) REFERENCES `business`(`id`))