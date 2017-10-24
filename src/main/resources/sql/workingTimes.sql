CREATE TABLE `workingTimes`
    (`day` INTEGER NOT NULL,
     `start` INTEGER NOT NULL,
     `employeeID` INTEGER NOT NULL,
     PRIMARY KEY(`start`, `day` , `employeeID`)
     FOREIGN KEY(`employeeID`) REFERENCES `employee`(`id`))