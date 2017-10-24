--Full Body Massage
INSERT INTO `serviceDuration`
    (`serviceID`,`duration`)
VALUES
    ((SELECT `id` FROM `service` WHERE `name` = 'Full Body Massage' AND
    `businessID` = (SELECT `id` FROM `business` WHERE `name` = 'Lily ' ||
     'Massage')), '30');

INSERT INTO `serviceDuration`
    (`serviceID`,`duration`)
VALUES
    ((SELECT `id` FROM `service` WHERE `name` = 'Full Body Massage' AND
    `businessID` = (SELECT `id` FROM `business` WHERE `name` = 'Lily ' ||
     'Massage')), '60');

INSERT INTO `serviceDuration`
(`serviceID`,`duration`)
VALUES
  ((SELECT `id` FROM `service` WHERE `name` = 'Full Body Massage' AND
    `businessID` = (SELECT `id` FROM `business` WHERE `name` = 'Lily ' ||
     'Massage')), '90');

--Neck Massage
INSERT INTO `serviceDuration`
    (`serviceID`,`duration`)
VALUES
    ((SELECT `id` FROM `service` WHERE `name` = 'Neck Massage' AND
    `businessID` = (SELECT `id` FROM `business` WHERE `name` = 'Lily ' ||
     'Massage')), '30');

--Back Massage
INSERT INTO `serviceDuration`
    (`serviceID`,`duration`)
VALUES
    ((SELECT `id` FROM `service` WHERE `name` = 'Back Massage' AND
    `businessID` = (SELECT `id` FROM `business` WHERE `name` = 'Lily ' ||
     'Massage')), '30');

INSERT INTO `serviceDuration`
    (`serviceID`,`duration`)
VALUES
    ((SELECT `id` FROM `service` WHERE `name` = 'Back Massage' AND
    `businessID` = (SELECT `id` FROM `business` WHERE `name` = 'Lily ' ||
     'Massage')), '60');

--Foot Massage
INSERT INTO `serviceDuration`
    (`serviceID`,`duration`)
VALUES
    ((SELECT `id` FROM `service` WHERE `name` = 'Foot Massage' AND
    `businessID` = (SELECT `id` FROM `business` WHERE `name` = 'Lily ' ||
     'Massage')), '30');

--Deep Tissue Massage
INSERT INTO `serviceDuration`
    (`serviceID`,`duration`)
VALUES
    ((SELECT `id` FROM `service` WHERE `name` = 'Deep Tissue Massage' AND
    `businessID` = (SELECT `id` FROM `business` WHERE `name` = 'Lily ' ||
     'Massage')), '30');

INSERT INTO `serviceDuration`
    (`serviceID`,`duration`)
VALUES
    ((SELECT `id` FROM `service` WHERE `name` = 'Deep Tissue Massage' AND
    `businessID` = (SELECT `id` FROM `business` WHERE `name` = 'Lily ' ||
     'Massage')), '60');

INSERT INTO `serviceDuration`
    (`serviceID`,`duration`)
VALUES
    ((SELECT `id` FROM `service` WHERE `name` = 'Deep Tissue Massage' AND
    `businessID` = (SELECT `id` FROM `business` WHERE `name` = 'Lily ' ||
     'Massage')), '90')
