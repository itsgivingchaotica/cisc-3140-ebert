var http = require('http')
var fs = require('fs')

http
    .createServer(function(req,res) {
        // const text = fs.readFileSync('./big.txt', 'utf8')
        // res.end(text)
        const fileStream = fs.createReadStream('./big.txt', 'utf8')
        fileStream.on('open', ()=> {
            //pushes readStream to writeStream
            //read and write data in chunks: Transfer-Encoding: chunked 
            //in developer view of localhost Response Headers
            fileStream.pipe(res)
        })
        fileStream.on('error',(err) => {
            res.end(err)
        })
    })
    .listen(3000)