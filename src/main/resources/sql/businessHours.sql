CREATE TABLE `businessHours`
    (`day` INTEGER NOT NULL,
     `start` INTEGER NOT NULL,
     `businessID` INTEGER NOT NULL,
     PRIMARY KEY(`start`, `day` , `businessID`)
     FOREIGN KEY(`businessID`) REFERENCES `business`(`id`))