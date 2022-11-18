% postgres --version
postgres (PostgreSQL) 14.5 (Homebrew)

% which psql
/opt/homebrew/bin/psql

    - Installation: Make sure homebrew is downloaded, then enter the following command in Terminal:
        `% brew install postgresql@14` 
    - To start: 
        `% brew services start postgresql`
    - `psql postgres`
    - `CREATE ROLE app_user WITH LOGIN PASSWORD 'app_password';
    - `ALTER ROLE app_user CREATEDB;
    `\du` will show us the users
    - Login as the user: `psql postgres -U app_user`
    then we can begin to create our databse
    - `CREATE DATABASE todo_database;`
    when viewing the list of databases with `\l` in terminal we will see that `todo_database` is now listed with owner as `app_user` or whatever the owner will be in the db.js file for our app. 
    \c squirrel_sightings
    
[Create Bash Script to automate process to setup new postgres DB] (https://stackfame.com/creating-user-database-and-adding-access-on-postgresql)

[how do i automate creating database in postgresql] (https://stackoverflow.com/questions/56180966/how-do-i-automate-creating-database-in-postgresql)

`psql -U ... -d ... -f my_tables.sql` = Populating postgres db via terminal with sql file
`psql -U username -d myDataBase -a -f myInsertFile`

[postgres creating database using .sql file] (https://stackoverflow.com/questions/53770816/postgres-creating-database-using-sql-file)

[BASH SCRIPT AND NODE.JS GENERATOR FOR SEEDING POSTGRES DATABASE] (https://hashinteractive.com/blog/bash-script-and-node-js-generator-for-seeding-postgres-database/)

[connect ElephantSQL with node.js]
(https://stackoverflow.com/questions/64022271/connect-elephantsql-with-node-js)
