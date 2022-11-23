const express = require('express')
const bodyParser = require('body-parser')
const app = express()
const port = 3000
// const pool = require("./db")
require('dotenv').config()
const connectionString = process.env.CONNECTION_STRING
const pgp = require("pg-promise")()
const db = pgp(connectionString)
const { response } = require('express')

//params, query (?) body ways

//app.use(express.json()) // => req.body, gives access 
app.use(bodyParser.json())
app.use(
    bodyParser.urlencoded({
        extended: true,
    })
)

app.get('/todoscd', async(req, res) => {
    try {
        const allTodos = await db.query("SELECT * FROM todo");
        res.json(allTodos.rows);
    } catch (err) {
        console.error(err.message);
    }
  }); 

  app.get("/todos/:id", async(req, res) => {
    const { id } = req.params
    try {
        const todo = await db.query("SELECT * FROM todo WHERE todo_id = $1", [id])
        res.json(todo.rows[0]);
    } catch (err)
    {
        console.error(err.message);
    }
    //console.log(req.params);
  });

//ROUTE//

//get all todos

//get a todo

//create a todo 1.

app.post("/todos", async(req,res) => {
    try {
        //await
        //console.log(req.body) //use Postman to test body - raw 
        const { description } = req.body //description is the column header description VARCHAR(255)
        const newTodo = await db.query("INSERT INTO todo (description) VALUES ($1) RETURNING *", [description]
        );

        res.json(newTodo.rows[0]);
    } catch (err){
        console.error(err.message)
    }
}); 

//update a todo

app.put("/todos/:id", async(req, res) => {
    try {
        const { id } = req.params; //WHERE
        const { description } = req.body; //SET
        const updateTodo = await db.query("UPDATE todo SET description = $1 WHERE todo_id = $2", 
        [description, id]
        );
        res.json("Todo was updated");
    } catch (err){
        console.error(err.message);
        }
    }
);

//delete a todo

app.delete("/todos/:id", async (req, res) => {
    try {
        const { id } = req.params;
        const deleteTodo = await db.query(" DELETE FROM todo WHERE todo_id = $1", [id])
        res.json("Todo was successfully deleted");
    } catch (err) {
        console.error(err.message);
    }
});

app.listen(port, () =>  { //what is 3000 vs 5000
    console.log(`server is listening on port ${port}.`);
});