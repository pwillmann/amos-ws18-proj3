const path = require('path');
const config = require('../config');
const webpack = require('webpack');
const ESlintFormatter = require('eslint-friendly-formatter');

// Helper to get current git branch, commit, author, date etc
const git = require('git-rev-sync');
const gitlog = require('gitlog');

const gitLogOptions =
  {
    repo: __dirname + '/../../',
    number: 1,
    fields:
      [
        'authorName',
      ]
  };

const defaults = {
  __DEV__: JSON.stringify(config.isDev),
  __PROD__: JSON.stringify(config.isProd),
  'process.env': {
    'NODE_ENV': `"${config.env}"`,
    'JUNIT_REPORT_PATH': "./junit",
    'JUNIT_REPORT_NAME': "test-results.xml",
  },
  __APP_MODE__: `"${config.appMode}"`,
  'GIT': {
    'COMMIT': JSON.stringify(git.short()),
    'BRANCH': JSON.stringify(git.branch()),
    'DATE': JSON.stringify(git.date()),
    'MESSAGE': JSON.stringify(git.message()),
    'AUTHOR': JSON.stringify(gitlog(gitLogOptions)[0].authorName)
  },
};


const webpackConfig = {
  entry: './src/js/main.js',
  output: {
    path: config.assetsRoot,
    publicPath: config.assetsPublicPath,
    filename: config.isDev ? './js/[name].js' : './js/[name].[chunkhash].js',
    chunkFilename: config.isDev ? './js/[id].js' : './js/chunk.[chunkhash].js',
  },
  resolve: {
    extensions: ['.js', '.vue', '.json'],
    alias: {
      vue$: 'vue/dist/vue.esm.js',
      assets: path.resolve(__dirname, '../src/assets'),
      js: path.resolve(__dirname, '../src/js'),
      components: path.resolve(__dirname, '../src/js/components'),
      src: path.resolve(__dirname, '../src'),
      scss: path.resolve(__dirname, '../src/scss'),
    },
  },
  plugins: [
    new webpack.DefinePlugin(defaults)

  ],
  module: {
    rules: [
      /* {
         test: /\.(js|vue)$/,
         loader: 'eslint-loader',
         enforce: 'pre',
         exclude: /node_modules/,
         options: {
           formatter: ESlintFormatter,
         },
       },*/
      {
        test: /\.js$/,
        loader: 'babel-loader',
        exclude: /node_modules/,
      },
      {
        test: /\.vue$/,
        loader: 'vue-loader',
        options: {
          extractCSS: config.isProd,
          loaders: {
            sass: 'vue-style-loader!css-loader!sass-loader?indentedSyntax=1&data=@import "./src/scss/base"',
            scss: 'vue-style-loader!css-loader!sass-loader?data=@import "./src/scss/base";'
          },
        },
      },
      {
        test: /\.(png|jpe?g|gif|svg)(\?.*)?$/,
        loader: 'url-loader',
        options: {
          limit: 8000,
          name: path.posix.join(config.assetsSubDirectory, './img/[name].[hash:7].[ext]'),
        },
      },
      {
        test: /\.(mp4|webm|ogg|mp3|wav|flac|aac)(\?.*)?$/,
        loader: 'url-loader',
        options: {
          limit: 10000,
          name: path.posix.join(config.assetsSubDirectory, './media/[name].[hash:7].[ext]'),
        },
      },
      {
        test: /\.(woff2?|eot|ttf|otf)(\?.*)?$/,
        loader: 'url-loader',
        options: {
          limit: 10000,
          name: path.posix.join(config.assetsSubDirectory, './fonts/[name].[hash:7].[ext]'),
        },
      },
    ],
  },
};

module.exports = webpackConfig;
