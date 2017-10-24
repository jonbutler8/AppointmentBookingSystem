INSERT INTO `employeeDisallowedServices`
    (`employeeID`,`serviceID`)
VALUES (
    (SELECT `id` FROM `employee` WHERE
      `firstName` = 'John' AND `lastName` = 'Smith'),
    (SELECT `id` FROM `service` WHERE
      `name` = 'Foot Massage'))
