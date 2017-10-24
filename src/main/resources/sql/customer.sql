CREATE TABLE `customer`
    (`id`           INTEGER,
     `userID`       INTEGER,
     `firstName`    TEXT          NOT NULL,
     `lastName`     TEXT,
     `phoneNumber`  CHARACTER(20) NOT NULL,
     `address`      VARCHAR(255),
     `businessID`       INTEGER,
     PRIMARY KEY(`id`),
     FOREIGN KEY(`userID`) REFERENCES `user`(`id`),
     FOREIGN KEY(`businessID`) REFERENCES `business`(`id`))