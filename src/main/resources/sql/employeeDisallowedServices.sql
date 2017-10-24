CREATE TABLE `employeeDisallowedServices`
    (`serviceID` INTEGER,
     `employeeID` INTEGER,
     PRIMARY KEY(`serviceID`, `employeeID`),
     FOREIGN KEY(`serviceID`) REFERENCES `service`(`id`),
     FOREIGN KEY(`employeeID`) REFERENCES `employee`(`id`))