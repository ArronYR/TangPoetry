var webpack = require('webpack');

module.exports = {
    entry: {
        app: './app.jsx',
    },
    output: {
        path: __dirname + '/build/',
        publicPath: "/build/",
        filename: "[name].js"
    },
    resolve: {
        extensions: ['', '.js', '.jsx']
    },
    module: {
        loaders: [
            {
                test: /\.css$/,
                loader: 'style!css'
            },
            {
                test: /\.jsx$/,
                loaders: [
                    'jsx?harmony'
                ]
            }
        ]
    },
    plugins: [
        new webpack.optimize.CommonsChunkPlugin('common.js'),
    ]
}