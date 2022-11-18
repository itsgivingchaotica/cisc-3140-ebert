const Pool = require("pg").Pool;

const pool = new Pool({
    user: 'siobhan',
    database: 'todo_database',
    host: 'localhost',
    //postgres runs on port 5432
    port: 5432
});

module.exports = pool;