
const express =  require('express')
const app = express()
const passport = require('passport')

// Setup env vars
require('dotenv').config()

// json parsing middleware
app.use(express.urlencoded({extended: true}));
app.use(express.json()) // To parse the incoming requests with JSON payloads


data = require('./data')
require('./authentication').setup(passport, data)

// setup routers
let usersRouter = require('./routes/usersRouter')(passport, data)
let restaurantsRouter = require('./routes/restaurantsRouter')(passport, data)
let ordersRouter = require('./routes/ordersRouter')(passport, data)

app.use('/users', usersRouter)
app.use('/restaurants', restaurantsRouter)
app.use('/orders', ordersRouter)

let serverInstance = null

module.exports = {
    start: () => {
        let port = process.env.PORT
        serverInstance = app.listen(port, () => {
            console.log(`Server running on port ${port}`)
        })
    },
    close: () => {
        serverInstance.close()
    }
}