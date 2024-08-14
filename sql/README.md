# Introduction

# SQL Queries

###### Table Setup (DDL)
```psql
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
###### Question 1: Show all members 

```sql
SELECT *
FROM cd.members
```

###### Questions 2: Lorem ipsum...

```sql
SELECT blah blah 
```
