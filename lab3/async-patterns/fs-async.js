// const { readFile, writeFile } = require('fs')

// console.log('start')
// readFile('./second.txt', 'utf8', (err, result) => {
//     if (err) {
//         console.log(err)
//         return
//     }

//     const first = result
//     readFile('./second.txt', 'utf8', (err, result) => {
//         if (err){
//         console.log(err)
//         return
//         }
//         const second = result
//         writeFile('./result-async.txt', `Result: ${first}, ${second}`, (err, result) => {
//             if (err) {
//                 console.log(err)
//                 return
//             }
//             console.log('done with this task')
//         }
//         )
//     })
// })
// console.log('starting next task')

// const { readFile, writeFile } = require('fs')
const { readFile, writeFile } = require('fs').promises
// const util = require('util')
// const readFilePromise = util.promisify(readFile)
// const writeFilePromise = util.promisify(writeFile)

const start =  async() => {
    try {
        //waiting for Promises to resolve is a much 
        //cleaner and easier to read approach
        // 1.const first = await getText('./first.txt')
        // 2.const first = await readFilePromise('./first.txt','utf8')
        const first = await readFile('./first.txt','utf8')
        // 1.const second = await getText('./async-patterns/second.txt')
        // 2. const second = await readFilePromise('./async-patterns/second.txt','utf8')
        const second = await readFile('./async-patterns/second.txt','utf8')
        // await writeFilePromise('./async-patterns/result-mind-grenade.txt',
        // `This is awesome: ${first} ${second}`
        // )
        await writeFile('./async-patterns/result-mind-grenade.txt',
        `This is awesome: ${first} ${second}`,
        //to append to file
        { flag: 'a' }
        )
        
        console.log(first, second)
    } catch (error) {
        console.log(error)
    }
}

// const getText = (path) =>{
//     return new Promise((resolve,reject) => {
//         readFile(path,'utf8',(err,data) =>{
//             if (err){
//             reject(err)
//             }
//             else {
//             resolve(data)
//             }
//         })
//     })
// }

// getText('./async-patterns/second.txt')
//     .then((result) => console.log(result))
//     .catch((err) => console.log(err))


start()

