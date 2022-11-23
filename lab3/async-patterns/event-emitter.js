//common practice to call EventEmitter
const EventEmitter = require('events')
//invoke instance of our class , the object customEmitter in this case
const customEmitter = new EventEmitter()
//name of event, pass in call back function
//order matters
customEmitter.on('response',(name,id) => {
    console.log(`data received : user ${name} with id ${id} `)
} )
//can have as many functions here
customEmitter.on('response',() => {
    console.log(`some other logic here `)
} )
//after subscribing to event, emit it
//strings need to match, the one we are listening for
//to have all events emitted, must put this at end of code
//or specifically after the events you want to emit
customEmitter.emit('response','john',34)