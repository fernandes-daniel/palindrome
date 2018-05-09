const express  = require('express')
const app      = express()
const server   = require('http').createServer(app)
const nunjucks = require('nunjucks')
const port     = 3000

nunjucks.configure('ui/server/src/views', {autoescape: true, express: app})

app.use(express.static('public'))

app.get('/', (req,res) => {
    res.render('index.html')
})

server.listen(port, () => console.log('App listening on port '+port))