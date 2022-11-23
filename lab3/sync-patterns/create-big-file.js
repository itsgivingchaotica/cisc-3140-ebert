const {createReadStream} = require('fs')

//const stream = createReadStream('./sync-patterns/big.txt')

//reading data in chunks
//event is : data

//default 64kb
//last buffer - remainder
// highWaterMark - control size
const stream = createReadStream('./sync-patterns/big.txt', { 
    highWaterMark: 90000, 
    encoding: 'utf8' })
// const stream = createReadStream('./sync-patterns/big.txt', { encoding: 'utf8' })
stream.on('data',(result)=>{
    console.log(result)
})
stream.on('error',(err) => console.log(err))