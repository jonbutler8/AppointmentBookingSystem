CREATE TABLE `serviceDuration`
    (`serviceID` INTEGER,
     `duration` INTEGER NOT NULL,
     PRIMARY KEY(`serviceID`, `duration`),
     FOREIGN KEY(`serviceID`) REFERENCES `service`(`id`))