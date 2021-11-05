
module.exports = function(passport, data){
    let router = require('express').Router()

    // Get order with id
    router.get('/:id', (req, res) => {

    })

    // update order
    router.put('/:id', (req, res) => {

    })

    // delete an order 
    router.delete('/:id', (req, res) => {

    })

    // for customer to confirm delivery => change order status
    router.get('/:id/confirm-delivery', (req, res) => {

    })


    return router
}