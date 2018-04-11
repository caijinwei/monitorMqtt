/* 
 * 资源后台公共js
 */
(function () {
    var resCommon = {
        /**
         * 全局配置
         */
        config:
                {
                    // s3配置
                    S3:
                            {
                                AwsRegionName: "ap-southeast-1",
                                AwsBucketName: "felinkagres",
                                AwsBucketNameSpider: "felinkimg"
                            },
                },
        util: {
            /**
             * 获取完整s3地址
             * @param {type} url
             * @returns {unresolved}
             */
            getS3url: function (url)
            {
                return "http://s3-" + resCommon.config.S3.AwsRegionName + ".amazonaws.com/" + resCommon.config.S3.AwsBucketName + "/" + url;
            },
            /**
             * 获取s3前缀
             * @returns {String}
             */
            getS3config: function ()
            {
                return "http://s3-" + resCommon.config.S3.AwsRegionName + ".amazonaws.com/" + resCommon.config.S3.AwsBucketName + "/";
            },
            /**
             * 获取完整爬虫s3地址
             * @param {type} url
             * @returns {unresolved}
             */
            getS3SpiderUrl: function (url)
            {
                //return "http://" + resCommon.config.S3.AwsBucketNameSpider + ".s3.amazonaws.com/" + url;
                return "http://s3-" + resCommon.config.S3.AwsRegionName + ".amazonaws.com/" + resCommon.config.S3.AwsBucketNameSpider + "/" + url;
            },
            /**
             * 获取爬虫s3前缀
             * @returns {String}
             */
            getS3SpiderConfig: function ()
            {
                //return "http://" + resCommon.config.S3.AwsBucketNameSpider + ".s3.amazonaws.com/" ;
                return "http://s3-" + resCommon.config.S3.AwsRegionName + ".amazonaws.com/" + resCommon.config.S3.AwsBucketNameSpider + "/";
            },
            /**
             * 判断sourceStr是否以startStr开头
             * @param {type} sourceStr 原字符串
             * @param {type} startStr 比较字符串
             * @returns {undefined}
             */
            isStartWith: function (sourceStr, startStr) {
                if (startStr == null || startStr == "" || sourceStr.length == 0 || startStr.length > sourceStr.length)
                    return false;
                if (sourceStr.substr(0, startStr.length) == startStr)
                    return true;
                else
                    return false;
                return true;
            }
        }
    };
    var api = new Object();
    api.config = resCommon.config.api;
    api.util = resCommon.util;
    if (window.ResT == null)
    {
        window.ResT = new Object();
    }
    ResT.resCommon = api;
})();

