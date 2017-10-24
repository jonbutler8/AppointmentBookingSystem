CREATE TABLE `booking`
    (`id`         INTEGER,
     `customerID` INTEGER   NOT NULL,
     `employeeID` INTEGER 	NOT NULL,
     `serviceID`  INTEGER	  NOT NULL,
     `duration`   INTEGER   NOT NULL,
     `date`       INTEGER   NOT NULL,
     `time`       INTEGER   NOT NULL,
     `businessID` INTEGER   NOT NULL,
     PRIMARY KEY(`id`),
     FOREIGN KEY(`customerID`) REFERENCES `customer`(`id`),
     FOREIGN KEY(`employeeID`) REFERENCES `employee`(`id`),
     FOREIGN KEY(`serviceID`,`duration`) REFERENCES `serviceDuration`(`serviceID`,`duration`),
     FOREIGN KEY(`businessID`) REFERENCES `business`(`id`))