CREATE TABLE `service`
    (`id`         INTEGER,
     `name`       TEXT      NOT NULL,
     `businessID` INTEGER   NOT NULL,
     PRIMARY KEY(`id`),
     FOREIGN KEY(`businessID`) REFERENCES `business`(`id`))