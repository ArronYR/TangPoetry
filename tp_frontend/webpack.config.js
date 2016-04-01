var webpack = require('webpack');

module.exports = {
    entry: {
        app: './app.jsx',
    },
    output: {
        // path: 打包文件存放的绝对路径
        // publicPath: 网站运行时的访问路径
        // filename: 打包后的文件名
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