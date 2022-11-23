const { readFile } = require('fs')

console.log('started a first task')
//check file path
//fs.readFile is asynchronous whereas fs.readFileSync is synchronous
readFile('./first.txt', 'utf8', (err, result) => {
    if (err) {
        console.log(err)
        return
    }
    console.log(result)
    console.log('completed first task')
})
    console.log('starting next task')