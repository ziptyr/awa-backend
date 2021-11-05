
module.exports = function(passport, data){
    let router = require('express').Router()


    // return all restaurants
    router.get('/', (req, res) => {

    })

    // create new restaurant
    router.post('/', (req, res) => {

    })

    // return the restaurants menu and list of
    router.post('/:id', (req, res) => {

    })


    // delete a restaurant and all its products
    router.delete('/:id', (req, res) => {

    })

    // create a new product 
    router.post('/:id/products', (req, res) => {

    })


    router.put('/:id/products/:prodId', (req, res) => {

    })


    return router
}