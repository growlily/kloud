module.exports = {

    outputDir: 'dist',  //build输出目录

    devServer: {
        open: true, //是否自动弹出浏览器界面
        host: 'localhost',
        port: '8887',
        https: false,
        hotOnly: false, //是否开启热更新
        proxy: null
    }

    //调试选项
    // configureWebpack: {
    //     devtool: 'source-map'
    // }

}