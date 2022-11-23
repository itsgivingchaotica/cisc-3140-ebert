# QUIZ 4
> Objective: create a database that would enable users to track data about squirrels on campus

## **Database Product: PostgreSQL**
- Version: postgres (PostgreSQL) 14.5 (Homebrew)
- Operating system: MacOS Monterey Version 12.5.1
    - Installation: Make sure homebrew is downloaded, then enter the following command in Terminal:
        `% brew install postgresql@14` 
    - To start: 
        `% brew services start postgresql`
    - Or if you don't want or need a background service just run':
        `% pg_ctrl -D /usr/local/var/postgres start`
    - `psql postgres`
    - `CREATE ROLE app_user WITH LOGIN PASSWORD 'app_password';
    - `ALTER ROLE app_user CREATEDB;
    `\du` will show us the users
    - Login as the user: `psql postgres -U app_user`
    then we can begin to create our databse
    - `CREATE DATABASE todo_database;`
    when viewing the list of databases with `\l` in terminal we will see that `todo_database` is now listed with owner as `app_user` or whatever the owner will be in the db.js file for our app. 
    
    
    - (Another way to Connect to a database):
        `% psql -h localhost -p 5432 -U <user_name> <database_name>`  
        
(when installing postgres specify user because db.js file will be doing so)
        
[Documentation](https://www.postgresql.org/docs/14/index.html)
[Reference Manual](https://www.postgresql.org/docs/current/reference.html)
[PostgreSQL tutorial](https://www.postgresql.org/docs/14/tutorial.html)
[pgAdmin Documentation](https://www.pgadmin.org/docs/)

[Setting up DB link in PostgreSQL](https://medium.com/@techrandomthoughts/setting-up-db-link-in-postgresql-d196468b43f8)

[How to create database link in postgres](https://dbaclass.com/article/how-to-create-database-link-in-postgres/)

[Setting up DB link in PostgreSQL](https://medium.com/@techrandomthoughts/setting-up-db-link-in-postgresql-d196468b43f8)

[How to allow remote connections to PostgreSQL database server](https://bosnadev.com/2015/12/15/allow-remote-connections-postgresql-database-server/)

[Building a restful API with Elephant SQL and Express JS Framework](https://codeburst.io/build-a-restful-api-with-elephant-sql-postgres-and-express-js-framework-nodejs-part-1-1b137257d5c8)

| HTTP     |  CRUD  | CRUD Operation            |
|----------|--------|---------------------------|
|  POST    | CREATE | INSERT INTO...VALUES      |   
|  GET     | READ   | SELECT ... FROM... WHERE  |   
|  PUT     | UPDATE | UPDATE ... SET ... WHERE  |  
|  DELETE  | DELETE | DELETE FROM ... WHERE     |      

## **Build Express Server and create Database/table**
1. From repository `% npm init`

The following sets up the package.json file
```
This utility will walk you through creating a package.json file.
It only covers the most common items, and tries to guess sensible defaults.

See `npm help init` for definitive documentation on these fields
and exactly what they do.

Use `npm install <pkg>` afterwards to install a package and
save it as a dependency in the package.json file.

Press ^C at any time to quit.
package name: (lab3) 
version: (1.0.0) 
description: 
entry point: (index.js) 
test command: 
git repository: 
keywords: 
author: siobhan
license: (ISC) 
About to write to /Users/siobhan/cisc-3140-ebert/lab3/package.json:

{
  "name": "lab3",
  "version": "1.0.0",
  "description": "",
  "main": "index.js",
  "scripts": {
    "test": "echo \"Error: no test specified\" && exit 1"
  },
  "author": "siobhan",
  "license": "ISC"
}


Is this OK? (yes) yes
```
2. create index.js file `% touch index.js`
3. install express `% npm i express pg` make sure : found 0 vulnerabilities
4. write index.js `% code index.js`

```
const express = require("express");
const app = express();

app.listen(3000, () =>  { //what is 3000 vs 5000
    console.log("server is listening on port 3000");
});
```

5. Save file: the following will appear in terminal: `server is listening on port 3000`
6. Install nodemon: `% npm install -g nodemon` as a global depdency vs. `% npm i nodemon -D` to install as a devDependcy in package.json file. You can install any global dependcy on mac `sudo npm install -g` 
7. Start the local server `% nodemon app.js` or `% npm start` if in scripts you use `"start" : node app.js"` or `"dev":"nodemon app.js` so we can use `npm run dev` which spits up nodemon which watches the application and restarts the app everytime a change is made in the .js file. Combine both by setting `"start": "nodemon app.js"`
8. Create your database using SQL so `% touch database.sql` and `% code database.sql`
```
CREATE DATABASE todo_database;

CREATE TABLE todo(
    todo_id SERIAL PRIMARY KEY,
    description VARCHAR(255)
);
```
9. Connect Postgres with the following : `% psql postgres`
10. add first part of SQL file: `postgres=# CREATE DATABASE todo_database;`
11. open the database`postgres=# \c todo_database`
12. create table: `% CREATE TABLE todo(todo_id SERIAL PRIMARY KEY,description VARCHAR(255));`
13. Now create new file db.js `% touch db.js` and edit the file `% code db.js`

```
const Pool = require("pg").Pool;

const pool = new Pool({
    user: "postgres",
    database: "todo_database",
    host: "localhost",
    //postgres runs on port 5432
    port: 5432
});
 
module.exports = pool;
```

## **Connect Server and database**

14. Add the following line of code to index.js: `const pool = require("./db");` The file should now look like this: 

```
const express = require("express");
const app = express();
const pool = require("./db");

app.listen(3000, () =>  { //what is 3000 vs 5000
    console.log("server is listening on port 3000");
});
```
## **Create Routes with DB queries**
15. Add the following line of code to index.js: `app.use(express.json()) // => req.body

16. Now we want to do CRUD commands: start with Create. in HTTP this is `post` and will contain SQL commands such as "INSERT INTO... VALUES"

```
app.post("/todos", async(req,res) => {
    try {
        //await
        //console.log(req.body) //use Postman to test body - raw 
        const { description } = req.body //description is the column header description VARCHAR(255)
        const newTodo = await pool.query("INSERT INTO todo (description) VALUES ($1) RETURNING *", [description]
        );

        res.json(newTodo.rows[0]);
    } catch (err){
        console.error(err.message)
    }
}); 
```

17. Save the index.js file. Now head to Postman and create a Post request under http://localhost:3000/todos this is our first endpoint. "/todos"

18. Choose Body, raw, JSON and enter something like this underneath:

```
{
    "description": "I need to pass this class"
}
```

When you hit send, below the Post request on Postman this will appear:

```
{
    "todo_id": 1, (for example,this accumulates automatically)
    "description": "I need to pass this class"
}
```

additionally, once you are working with Post you should see this at http://localhost:3000/todos 

(say you have made several more post requests)

```
[
{
"todo_id": 1,
"description": "I need to eat lunch"
},
{
"todo_id": 3,
"description": "I need to clean my room"
},
{
"todo_id": 4,
"description": "I need to pass this class"
}
]
```
Now we you can also view in the local database the data is populating there as well `SELECT * FROM todo;` will result in: 

```
todo_database=# SELECT * FROM todo;
 todo_id |        description        
---------+---------------------------
       1 | I need to eat lunch
       3 | I need to clean my room
       4 | I need to pass this class
(3 rows)
```
So we can see that everything is definitely connected.

19. GET - is the READ  
```
app.get('/todos', async(req, res) => {
    try {
        const allTodos = await pool.query("SELECT * FROM todo");
        res.json(allTodos.rows);
    } catch (err) {
        console.error(err.message);
    }
  }); 
```

On Postman select GET http://localhost:3000/todos the following results because we use `SELECT * FROM todo"` 

``` 
[
    {
        "todo_id": 1,
        "description": "I need to eat lunch"
    },
    {
        "todo_id": 3,
        "description": "I need to clean my room"
    },
    {
        "todo_id": 4,
        "description": "I need to pass this class"
    }
]
```

We can include another `get` command to call for a specific id"

```
app.get("/todos/:id", async(req, res) => {
    const { id } = req.params
    try {
        const todo = await pool.query("SELECT * FROM todo WHERE todo_id = $1", [id])
        res.json(todo.rows[0]);
    } catch (err)
    {
        console.error(err.message);
    }
    //console.log(req.params);
  });
```

When we use Postman, the endpoint will be the id that the user specified : http://localhost:3000/todos/1 where 1 is the id specified in `[id]` in the SQL command

Therefore the following results from Postman:

```
{
    "todo_id": 1,
    "description": "I need to eat lunch"
}
```

20. UPDATE - this is the Put command

```
app.put("/todos/:id", async(req, res) => {
    try {
        const { id } = req.params; //WHERE
        const { description } = req.body; //SET
        const updateTodo = await pool.query("UPDATE todo SET description = $1 WHERE todo_id = $2", 
        [description, id]
        );
        res.json("Todo was updated");
    } catch (err){
        console.error(err.message);
        }
    }
)
```

`$1` is the `description` while `$2` in the `id` in this case. So we do PUT request in Postman with 
http://localhost:3000/todos/1 and use the following code: 

```
{
    "description": "I want to eat dinner"
}
```
When pressing enter this confiriation appears as we set it to be in `res.json` above:

`"Todo was updated"`

We can also see the following in our database on the terminal:

```
 todo_id |        description        
---------+---------------------------
       3 | I need to clean my room
       4 | I need to pass this class
       1 | I want to eat dinner
(3 rows)

```
and looking at our browser we see:

```
[
{
"todo_id": 3,
"description": "I need to clean my room"
},
{
"todo_id": 4,
"description": "I need to pass this class"
},
{
"todo_id": 1,
"description": "I want to eat dinner"
}
]
```

because we updated `"todo_id": 1` 

21. DELETE - the delete request

```
app.delete("/todos/:id", async (req, res) => {
    try {
        const { id } = req.params;
        const deleteTodo = await pool.query(" DELETE FROM todo WHERE todo_id = $1", [id])
        res.json("Todo was successfully deleted");
    } catch (err) {
        console.error(err.message);
    }
});
```

DELETE http://localhost:3000/todos/4 would delete the `todo_id` that is equal to 4

"Todo was successfully deleted!" appears below on Postman

We can our database or perform GET request on Postman to check this performed correctly

#**Connecting to ElephantSQL**

[Documentation](https://www.elephantsql.com/docs/nodejs.html)
- create a separate folder `db`
- create a new file `connect.js`
- in this file, declare `const conString = "INSERT_YOUR_POSTGRES_URL_HERE"

in app.js
-const connectDB = require('./db/connect');
-require('dotenv').config();

To set up .env file to push up to GitHub
- avoid meddling with data, keep secret - dependency needed is dotenv, and make sure to include in .gitignore file (as well as `/node_modules`)

npm install dotenv

from app.js

const start = async () => {
    try {
        await connectDB(process.env.MONGO_URI) //process.env.ELEPHANTSQL_URI
        app.listen(port, console.log(`server is listening on port ${port}...`))
    } catch (error) {
        console.log(error)
    }
}

