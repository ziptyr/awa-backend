
module.exports = function(passport, data){
    let router = require('express').Router()



    // create a new user
    router.post('/', (req, res) => {

    })

    // delete user
    router.delete('/', (req, res) => {

    })

    // login user and return jwt 
    router.post('/login', (req, res) => {

    })

    router.get('/orders', (req, res) => {

    })




    return router
}