# **Helpful SQL Commands**

to run a sql query from a file 
`=# \i /Users/filepath/name.sql`

## **AGE()**
`SELECT col_names, AGE(NOW(), date_of_birth) AS AGE` find the age based on a birthday

## **ALTER TABLE ... **

- **DROP CONSTRAINT**
Changing the primary key. you can find the index by typing `=# \d table_name`.
````
Indexes:
    "person_pkey" PRIMARY KEY, btree (id)
```

`ALTER TABLE person DROP CONSTRAINT person_pkey;` : Drop of the primary key
`ALTER TABLE person DROP CONSTRAINT unique_email_address` : Drop the unique specification

- **ADD PRIMARY KEY**
`ALTER TABLE person ADD PRIMARY KEY (id);` 
Note we cannot add primary key if have duplicate records

- **ADD CONSTRAINT**
Add primary key to a table
`ALTER TABLE person ADD CONSTRAINT unique_email_address UNIQUE (email);` : Defines a rule that must be followed based on column values such as an email. Cannot add two records with the same column value.


## **AS**
`FUNCTION() (or var_name) AS pick_name FROM table_name`
Example:: `SELECT id, make, model, price AS original_price, ROUND(price*.10,2) AS ten_percent FROM car;` 

## **AVG()**
`SELECT AVG(col_name) FROM table_name; can use ROUND(AVG(col_name)) as well

## **BETWEEN**
select data from a range
`SELECT * FROM table_name WHERE col_name BETWEEN <DATA_TYPE> 'start_val' AND 'end_val';

## **CHECK**
Only allows the specified values as field values in that column
Example: 
`ALTER TABLE person ADD CONSTRAINT gender_constraint CHECK (gender = 'Female' || gender = 'Male');`

`=# \d table_name` you can find the following added 

```
Check constraint:
    "gender_constraint" CHECK (gender::text = 'Female' ::text OR gender::text = 'Male'::text)
```

## **COALESCE()**
each null value can be replaced with a message. 
`SELECT COALESCE(col_name, 'message') (AS pick_name) FROM table_name;`

## **\copy**
`=# \copy (SELECT * FROM person_car LEFT JOIN cars_ ON cars_.id = person_car.car_id) TO '/Users/siobhan/Desktop' DELIMITER ',' CSV HEADER;'

## **CREATE TABLE**

```
CREATE TABLE table_name (
    Column name + data type + constraints if any
)

//Ex. 
CREATE TABLE person (
    id BIGSERIAL NOT NULL PRIMARY KEY, //BIGSERIAL increments by itself
    first_name VARCHAR(50) NOT NULL, //varchar is characters (max number of characters)
    last_name VARCHAR(50) NOT NULL,
    gender VARCHAR(5) NOT NULL,
    date_of_birth DATE NOT NULL;
    email VARCHAR(150) );
)

app_database=# \d
              List of relations
 Schema |     Name      |   Type   |  Owner  
--------+---------------+----------+---------
 public | person        | table    | siobhan
 public | person_id_seq | sequence | siobhan
(2 rows)

app_database=# \d person
                                         Table "public.person"
      Column      |          Type          | Collation | Nullable |              Default               
------------------+------------------------+-----------+----------+------------------------------------
 id               | bigint                 |           | not null | nextval('person_id_seq'::regclass)
 first_name       | character varying(50)  |           | not null | 
 last_name        | character varying(50)  |           | not null | 
 email            | character varying(150) |           |          | 
 gender           | character varying(6)   |           | not null | 
 date_of_birth    | date                   |           | not null | 
 country_of_birth | character varying(50)  |           |          | 
Indexes:
    "person_pkey" PRIMARY KEY, btree (id)
    
```

## **DELETE FROM**
delete record(s) where column value is specified

`DELETE FROM person WHERE id = 1` by column field value
`DELETE FROM person` deletes all records


## **DISTINCT** 
lists only one of each keyword and remove duplicates from query

`SELECT DISTINCT col_name FROM table_name ORDER BY col_name` 

## **DROP TABLE**

```
=# DROP TABLE person;
DROP TABLE
=# \d
Did not find any relations.
```

## **EXTRACT()**
`SELECT EXTRACT(YEAR(OR MONTH/DOW/CENTURY) FROM NOW())` 

## **FETCH**
Used like `limit` but is a SQL standard.
`SELECT * FROM table_name FETCH FIRST (#) ROW ONLY;`

## **GROUP BY**
use function `COUNT()` which will count the number of keywords. might be helpful to also add `ORDER BY` after words 
`SELECT col_name, COUNT(*) FROM table_name GROUP BY col_name (ORDER BY col_name);`

## **GROUP BY HAVING**
must be before `ORDER BY` and takes a function and specifies range 
`SELECT col_name, COUNT(*) FROM person GROUP BY col_name HAVING COUNT(*) > # ORDER BY col_name;`

## **IN**
select from more than one keyword to use in the query 
`SELECT * FROM table_name WHERE col_name IN ('value1', 'value2', 'value3');

## **INSERT INTO**
- inserts records into tables by inserting columns then say VALUES

```
INSERT INTO table_name (
    column_name
)
VALUES ('value');

ex. 
INSERT INTO person (
    first_name
    last_name
    gender,
    date_of_birth)
VALUES ('Anne', 'Smith', 'FEMALE', DATE '1988-01-09', anne@gmail.com)
```

## **INTERVAL**
can use to subtract or add time to a timetamp

`SELECT NOW() + INTERVAL '10 DAYS` can also use `10 MONTHS`
cast to date to remove timestamp

`SELECT (NOW() + INTERVAL '10 MONTHS')::DATE;`

## **JOIN .. ON**

**INNER JOINS**
From the table you can join foreign key to primary key and create a new record - ONLY for those records where foreign key and primary key are found in both records
`SELECT * FROM person JOIN car ON person.car_id = car.id`  

\x to toggle expanded record view

**LEFT JOINS**
Returns all records including with and without foreign keys.

```
SELECT * FROM table_name
LEFT JOIN foreign ON foreign.val = table_name.foreign_id; /* (also a val) */
```

## **LIKE**
returns data which has a substring equal to the LIKE statement
`SELECT * FROM table_name WHERE col_name LIKE 'something%something'; //where %is wildcard`
or `_______@` which means the number of characters up until that symbol
`SELECT * FROM person WHERE email LIKE '__h@%';` is an example
Can use ILIKE to ignore the case

## **ILIKE**
ignore case

## **LIMIT**
limit the number of rows in query to this number
`SELECT * FROM table_name LIMIT #rows;` example: `SELECT * FROM person LIMIT 5;`

## **MAX() and MIN()**
`SELECT MAX/MIN(col_name) FROM table_name` 

ex. `SELECT make MIN(price) FROM car GROUP BY make' '

## **OFFSET**
start query after this number of rows
`SELECT * FROM table_name OFFSET #rows`

```
ex.
app_database=# SELECT * FROM person OFFSET 5 LIMIT 5;
 id | first_name | last_name |              email              | gender | date_of_birth | country_of_birth 
----+------------+-----------+---------------------------------+--------+---------------+------------------
  6 | Drugi      | Shoreman  | dshoreman7@google.ca            | Male   | 2022-07-04    | Sweden
  7 | Jacquie    | Titterton | jtitterton8@topsy.com           | Female | 2022-01-08    | Syria
  8 | Basil      | Ingerith  |                                 | Male   | 2022-01-20    | Indonesia
  9 | Pascal     | Tubble    | ptubbleb@nymag.com              | Male   | 2022-05-15    | Czech Republic
 10 | Ingaborg   | Bucknill  | ibucknillc@networksolutions.com | Female | 2022-04-23    | France
 ```
 
## **NOW()**
gives us the timestamp
`SELECT NOW()::DATE;` will give us just the date
`SELECT NOW()::TIME;` cast to `TIME`

## **NULLIF**
this way we can get a null value instead of error
`SELECT NULLIF(#, #)` gives us null
`SELECT (NULLIF (#, diff#)` gives us #
can set a default value with `COALESCE` if `NULLIF` results in the value we don't want 
`SELECT COALESCE(10 / NULLIF(0, 0), 0);` here we get the result 0 

## **ON CONFLICT () **

**DO NOTHING**
when using `INSERT INTO` use this command to avoid inserting if there are duplicate values for specification
for example: `INSERT INTO table_name (col_name1, col_name2, etc) VALUES ('', '') ON CONFLICT (col_name) DO NOTHING;`

**DO UPDATE SET**
override existing data if present otherwise insert a new row
`INSERT INTO table_name (col_name1, col_name2, etc) VALUES ('', '') ON CONFLICT (col_name1) DO UPDATE SET col_name4 = EXCLUDED.col_name4;` simply changes the field value that has the duplicate 


## **ORDER BY**
order the reslts by the column name specified

`SELECT col_name FROM table_name ORDER BY col_name (DESC or) ASC`
`DESC` is default, if you want ascending order need to specify `ASC`

## **REFERENCES**
use this to link a foreign key to a primary key of another table. Can make sure only one of each of the linking table's primary key belonging to foreign key values.
example: we have a tables `person` with column name `car_id` of type `BIGINT` , use `REFERENCES` to another table `car`, specifically the column name `id`. 
Can use `UNIQUE` to make sure each car belongs to only one person `UNIQUE (car_id)`

Altogether when `CREATE TABLE table_name ( );` we specify `car_id BIGINT REFERENCES car (id), UNIQUE(car_id)`

```
create table person_car (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    gender VARCHAR(50) NOT NULL,
    email VARCHAR(50),
    date_of_birth DATE NOT NULL,
    country_of_birth VARCHAR(50) NOT NULL,
    car_id BIGINT REFERENCES cars_(id),
    UNIQUE(car_id)
);
```

You can also create a query `UPDATE person SET car_id = 13 WHERE id = 9000` after inserting new values into each table from `person` and `car`. Trying to delete like `DELETE FROM WHERE id = 13` will result in an error because it violates foreign key constraint. need to delete where the primary key originates or update the `car_id` to null like so: `DELETE FROM person WHERE id = 9000;` 

## **ROUND()**
`SELECT col_names, col_names, ROUND(col_name*0.10, <#decimalplaces>) FROM table_name;`

## **SELECT \* FROM**
- select columns to show from table

\* Can be the column name

`SELECT col_name FROM table_name`

## **SUM()**
`SELECT col_name, SUM(col_name) FROM table_name;`

## **UNIQUE**
Will define the column name as having the unique specification.

## **UPDATE**
updates a record with new added field values 
`UPDATE person SET email = 'blah@gmail.com' WHERE id = 1234;`

can also update several columns at once within a record
`UPDATE person SET first_name = 'Bob', last_name = 'Smith', email = 'blah@gmail.com' WHERE id = 1234;`

you can assign a foreign key using this command as well
`UPDATE table_name SET foreign_key_value = 4 WHERE primary_key_value = 1` an example

## **WHERE (AND OR) CLAUSE**
filter by keyword
`SELECT * FROM person WHERE col1_name = 'something' AND (col2_name = 'foo' OR col2_name) = 'bar');`

