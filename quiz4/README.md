# QUIZ 4
> Objective: create a database that would enable users to track data about squirrels on campus

## **Database Product: PostgreSQL**
- Version: postgres (PostgreSQL) 14.5 (Homebrew)
- Operating system: MacOS Monterey Version 12.5.1
    - Installation: Make sure homebrew is downloaded, then enter the following command in Terminal:
        `% brew install postgresql@14` 
    - To start: 
        `% brew services start postgresql`
    - Connect to the database:
        `% psql -h localhost -p 5432 -U <user_name> <database_name>`
    - Or simply connect to postgres (can use \l to list then \c to open database from there)
        `% psql postgres`
        
[Documentation](https://www.postgresql.org/docs/14/index.html)
[Reference Manual](https://www.postgresql.org/docs/current/reference.html)
[PostgreSQL tutorial](https://www.postgresql.org/docs/14/tutorial.html)
[pgAdmin Documentation](https://www.pgadmin.org/docs/)
