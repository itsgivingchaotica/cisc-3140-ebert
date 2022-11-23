//read stream option
const { writeFileSync } = require('fs')
for (let i = 0; i < 10000; i++) {
    writeFileSync('./big.txt', `hello world ${i}\n`, { flag : 'a'})
}