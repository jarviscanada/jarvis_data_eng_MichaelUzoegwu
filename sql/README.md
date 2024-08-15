# Introduction

# SQL Queries

###### Table Setup (DDL)
```sql
CREATE SCHEMA cd;

CREATE TABLE cd.members (
	memid SERIAL NOT NULL,
	surname VARCHAR(200) NOT NULL,
	firstname VARCHAR(200) NOT NULL,
	address VARCHAR(300) NOT NULL,
	zipcode INT NOT NULL,
	telephone VARCHAR(20) NOT NULL,
	recommendedby INT NOT NULL,
	joindate TIMESTAMP NOT NULL,
	CONSTRAINT pk_members_memid
		PRIMARY KEY(memid),
	CONSTRAINT fk_members_recommendedby
		FOREIGN KEY(recommendedby) 
			REFERENCES cd.members(memid)
);

CREATE TABLE cd.facilities(
	facid INT NOT NULL,
	name VARCHAR(100) NOT NULL,
	membercost NUMERIC NOT NULL,
	guestcost NUMERIC NOT NULL,
	initialoutlay NUMERIC NOT NULL,
	monthlymaintenance NUMERIC NOT NULL,
	CONSTRAINT pk_facilities_facid
		PRIMARY KEY(facid)
);

CREATE TABLE cd.bookings(
	bookid INT NOT NULL,
	facid INT NOT NULL,
	memid INT NOT NULL,
	starttime TIMESTAMP NOT NULL,
	slots INT NOT NULL,
	CONSTRAINT pk_bookings_bookid
		PRIMARY KEY(bookid),
	CONSTRAINT fk_bookings_facid
		FOREIGN KEY(facid)
			REFERENCES cd.facilities(facid),
	CONSTRAINT fk_bookings_memid
		FOREIGN KEY(memid)
			REFERENCES cd.members(memid)
);
```
###### Question 1: [Add Spa](https://pgexercises.com/questions/updates/insert.html)

```sql
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
```

###### Questions 2: [Add Spa (Auto ID)](https://pgexercises.com/questions/updates/insert3.html)

```sql
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
```
###### Questions 3: [Update Tennis Court 2](https://pgexercises.com/questions/updates/update.html) 
```sql
UPDATE cd.facilities
SET initialoutlay = 10000
WHERE facid = 1;
```

###### Questions 4: [Raise Tennis Court 2 Prices](https://pgexercises.com/questions/updates/updatecalculated.html)
```sql
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
```

###### Questions 5: [Clear Bookings Table](https://pgexercises.com/questions/updates/delete.html)
```sql
DELETE FROM cd.bookings;
```

###### Questions 6: [Delete Member 37](https://pgexercises.com/questions/updates/deletewh.html)
```sql
DELETE FROM cd.members
WHERE memid = 37;
```

###### Questions 7: [Select Facilities](https://pgexercises.com/questions/basic/where2.html)
```sql
SELECT 
	facid, 
	name, 
	membercost, 
	monthlymaintenance 
FROM cd.facilities
WHERE 
	membercost > 0 AND 
	membercost < monthlymaintenance / 50;
```

###### Questions 8: [Select Tennis Courts](https://pgexercises.com/questions/basic/where3.html)
```sql
SELECT * FROM cd.facilities
WHERE name ILIKE '%tennis%';
```

###### Questions 9: [Select Facilities](https://pgexercises.com/questions/basic/where4.html)
```sql
SELECT * FROM cd.facilities
WHERE facid IN (1, 5);
```

###### Questions 10: [Select Members After Join Date](https://pgexercises.com/questions/basic/date.html)
```sql
SELECT 
    memid, 
    surname, 
    firstname, 
    joindate 
FROM cd.members
WHERE joindate  >= '2012-09-01';
```

###### Questions 11: [List Surnames and Facilities](https://pgexercises.com/questions/basic/union.html)
```sql
SELECT surname FROM cd.members
UNION
SELECT name FROM cd.facilities;
```

###### Questions 12: [List Start Times By David Farrell](https://pgexercises.com/questions/joins/simplejoin.html)
```sql
SELECT starttime
FROM cd.members
JOIN cd.bookings ON 
    cd.members.memid  = cd.bookings.memid
WHERE 
    firstname = 'David' AND
    surname = 'Farrell';
```

###### Questions 13: [Select Tennis Start Times](https://pgexercises.com/questions/joins/simplejoin2.html)
```sql
SELECT starttime AS "start", "name"
FROM cd.bookings
JOIN cd.facilities ON
	cd.bookings.facid = cd.facilities.facid
WHERE
	cd.facilities.facid IN (0, 1) AND
	starttime >= '2012-09-21' AND
	starttime  < '2012-09-22'
ORDER BY "start";
```

###### Questions 14: [Select Members With Recommender](https://pgexercises.com/questions/joins/self2.html)
```sql
SELECT 
	cdm.firstname AS memfname,
	cdm.surname AS memsname,
	recm.firstname AS recfname,
	recm.surname AS recsname
FROM cd.members AS cdm
LEFT JOIN cd.members AS recm
	ON cdm.recommendedby = recm.memid
ORDER BY memsname, memfname;
```

###### Questions 15: [Select Members Who Recommended](https://pgexercises.com/questions/joins/self.html)
```sql
SELECT DISTINCT
	recm.firstname AS firstname,
	recm.surname  AS surname
FROM cd.members AS cdm
JOIN cd.members AS recm ON
	cdm.recommendedby = recm.memid
ORDER BY surname, firstname;
```

###### Questions 16: [Select Members With Recommender (No Join)](https://pgexercises.com/questions/joins/sub.html)
```sql
SELECT DISTINCT 
	members.firstname || ' ' || members.surname AS "member",
	(
	SELECT recs.firstname || ' ' || recs.surname AS recommender
		FROM cd.members AS recs
		WHERE recs.memid = members.recommendedby
	) AS recommender
FROM cd.members AS members;
```

###### Questions 17: [Count recommendations](https://pgexercises.com/questions/aggregates/count3.html)
```sql
SELECT recm.memid, COUNT(recm.memid)
FROM cd.members AS cdm
JOIN cd.members AS recm ON
	cdm.recommendedby = recm.memid
GROUP BY recm.memid
ORDER BY recm.memid;
```

###### Questions 18: [Total Slots Per Facility](https://pgexercises.com/questions/aggregates/fachours.html)
```sql
SELECT facid, SUM(slots)
FROM cd.bookings
GROUP BY facid
ORDER BY facid;
```

###### Questions 19: [Total Booked In September](https://pgexercises.com/questions/aggregates/fachoursbymonth.html)
```sql
SELECT facid, SUM(slots) AS "Total Slots"
FROM cd.bookings
WHERE 
	starttime >= '2012-09-01' AND
	starttime < '2012-10-01'
GROUP BY facid
ORDER BY "Total Slots";
```

###### Questions 20: [Facility Bookings Per Month In 2012](https://pgexercises.com/questions/aggregates/fachoursbymonth2.html)
```sql
SELECT facid, EXTRACT(MONTH FROM starttime) AS "month" , SUM(slots) AS "Total Slots"
FROM cd.bookings
WHERE 
	starttime >= '2012-01-01' AND 
	starttime < '2013-01-01'
GROUP BY facid, "month" 
ORDER BY facid, month;
```

###### Questions 21: [Count Members With Bookings](https://pgexercises.com/questions/aggregates/members1.html)
```sql
SELECT COUNT(DISTINCT memid)
FROM cd.bookings;
```

###### Questions 22: [List First Booking After Sep 1st](https://pgexercises.com/questions/aggregates/nbooking.html)
```sql
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
```

###### Questions 23: [List of Names With Total](https://pgexercises.com/questions/aggregates/countmembers.html)
```sql
SELECT
	COUNT(*) OVER (),
	firstname,
	surname
FROM cd.members
ORDER BY joindate;
```

###### Questions 24: [Produce A Numbered List of Members](https://pgexercises.com/questions/aggregates/nummembers.html)
```sql
SELECT
	ROW_NUMBER() OVER(),
	firstname,
	surname
FROM cd.members
ORDER BY joindate;
```

###### Questions 25: [Select Facility With Most Slots](https://pgexercises.com/questions/aggregates/fachours4.html)
```sql
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
```

###### Questions 26: [Format Member Names](https://pgexercises.com/questions/string/concat.html)
```sql
SELECT surname || ', '  || firstname AS name
FROM cd.members;
```

###### Questions 27: []()
```sql

```

###### Questions 28: []()
```sql

```

