-- Question 1
INSERT INTO cd.facilities(
	facid,
	name,
	membercost,
	guestcost,
	initialoutlay,
	monthlymaintenance
)
VALUES (
	9,
	'Spa',
	20,
	30,
	100000,
	800
);

-- Question 2
INSERT INTO cd.facilities(
	facid,
	name,
	membercost,
	guestcost,
	initialoutlay,
	monthlymaintenance
)
VALUES (
	(
		SELECT MAX(facid)
		FROM cd.facilities
	) + 1,
	'Spa',
	20,
	30,
	100000,
	800
);

-- Question 3
UPDATE cd.facilities
SET initialoutlay = 10000
WHERE facid = 1;

-- Question 4
UPDATE cd.facilities
SET
	membercost = (
		SELECT membercost FROM cd.facilities
		WHERE facid = 0
	) * 1.1,
	guestcost = (
		SELECT guestcost FROM cd.facilities
		WHERE facid = 0
	) * 1.1
WHERE facid = 1;

-- Question 5
DELETE FROM cd.bookings;

-- Question 6
DELETE FROM cd.members
WHERE memid = 37;

-- Question 7
SELECT
	facid,
	name,
	membercost,
	monthlymaintenance
FROM cd.facilities
WHERE
	membercost > 0 AND
	membercost < monthlymaintenance / 50;

-- Question 8
SELECT * FROM cd.facilities
WHERE name ILIKE '%tennis%';

-- Question 9
SELECT * FROM cd.facilities
WHERE facid IN (1, 5);

-- Question 10
SELECT
    memid,
    surname,
    firstname,
    joindate
FROM cd.members
WHERE joindate  >= '2012-09-01';

-- Question 11
SELECT surname FROM cd.members
UNION
SELECT name FROM cd.facilities;

-- Question 12
SELECT starttime
FROM cd.members
JOIN cd.bookings
	ON (cd.members.memid  = cd.bookings.memid)
WHERE
	firstname = 'David' AND
	surname = 'Farrell';

-- Question 13
SELECT starttime AS "start", "name"
FROM cd.bookings
JOIN cd.facilities ON
	cd.bookings.facid = cd.facilities.facid
WHERE
	cd.facilities.facid IN (0, 1) AND
	starttime >= '2012-09-21' AND
	starttime  < '2012-09-22'
ORDER BY "start";

-- Question 14
SELECT
	cdm.firstname AS memfname,
	cdm.surname AS memsname,
	recm.firstname AS recfname,
	recm.surname AS recsname
FROM cd.members AS cdm
LEFT JOIN cd.members AS recm
	ON cdm.recommendedby = recm.memid
ORDER BY memsname, memfname;

-- Question 15
SELECT DISTINCT
	recm.firstname AS firstname,
	recm.surname  AS surname
FROM cd.members AS cdm
JOIN cd.members AS recm ON
	cdm.recommendedby = recm.memid
ORDER BY surname, firstname;

-- Question 16
SELECT DISTINCT
	members.firstname || ' ' || members.surname AS "member",
	(
	SELECT recs.firstname || ' ' || recs.surname AS recommender
		FROM cd.members AS recs
		WHERE recs.memid = members.recommendedby
	) AS recommender
FROM cd.members AS members;

-- Question 17
SELECT recm.memid, COUNT(recm.memid)
FROM cd.members AS cdm
JOIN cd.members AS recm ON
	cdm.recommendedby = recm.memid
GROUP BY recm.memid
ORDER BY recm.memid;

-- Question 18
SELECT facid, SUM(slots)
FROM cd.bookings
GROUP BY facid
ORDER BY facid;

-- Question 19
SELECT facid, SUM(slots) AS "Total Slots"
FROM cd.bookings
WHERE
	starttime >= '2012-09-01' AND
	starttime < '2012-10-01'
GROUP BY facid
ORDER BY "Total Slots";

-- Question 20
SELECT facid, EXTRACT(MONTH FROM starttime) AS "month" , SUM(slots) AS "Total Slots"
FROM cd.bookings
WHERE
	starttime >= '2012-01-01' AND
	starttime < '2013-01-01'
GROUP BY facid, "month"
ORDER BY facid, month;

-- Question 21
SELECT COUNT(DISTINCT memid)
FROM cd.bookings;

-- Question 22
SELECT
	cd.members.surname,
	cd.members.firstname,
	cd.members.memid,
	MIN(starttime) AS starttime
FROM cd.bookings
JOIN cd.members
	ON cd.bookings.memid = cd.members.memid
WHERE starttime > '2012-09-01'
GROUP BY
	cd.members.surname,
	cd.members.firstname,
	cd.members.memid
ORDER BY memid;

-- Question 23
SELECT
	COUNT(*) OVER (),
	firstname,
	surname
FROM cd.members
ORDER BY joindate;

-- Question 24
SELECT
	ROW_NUMBER() OVER(),
	firstname,
	surname
FROM cd.members
ORDER BY joindate;

-- Question 25
SELECT facid, total
FROM(
	SELECT
		facid,
		SUM(slots) AS total,
		RANK () OVER(
			ORDER BY SUM(slots) DESC
		) AS rank
	FROM cd.bookings
	GROUP BY facid
) AS ranked_totals
WHERE RANK = 1;

-- Question 26
SELECT surname || ', '  || firstname AS name
FROM cd.members;

-- Question 27
