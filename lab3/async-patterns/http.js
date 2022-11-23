const http = require('http')

//req = coming in, res = sending out
const server = http.createServer((req, res) => {
    if (req.url === '/'){
        res.end('Home page')
    }
    if (req.url === '/about'){
        //blocking code
    for (let i = 0; i < 1000; i++){
        for (let j = 0; j < 1000; j++) {
            console.log(`${i} ${j}`)
        }
    }
        res.end('About page')
    }
    res.end('Error page')
})

server.listen(3000,() => {
    console.log('Server listening on port 3000...')
})