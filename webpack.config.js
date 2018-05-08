const path = require('path');

module.exports = {
    entry: './client/src/index.js',
    output: {
        path: path.resolve(__dirname, 'public'),
        filename: 'app.bundle.js'
    },
    module: {
        rules: [
            {
                test: /\.js$/,
                loader: 'babel-loader',
                query: {
                    presets: ['es2015']
                }
            }
        ]
    },
    stats: {
        colors: true
    },
    devtool: 'source-map'
};