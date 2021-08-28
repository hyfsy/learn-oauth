
启动oauth-server项目
访问：http://localhost:8021/oauth-server/register.html
填写应用名称，注册应用信息
通过console输出的clientId获取应用信息，包括clientSecret
将oauth-server服务器地址填写到oauth-client项目的配置文件中（application.properties）
将clientId和clientSecret填写到oauth-client项目的配置文件中（application.properties）
启动oauth-client项目
访问：http://localhost:8022/oauth-client/visit.html
点击访问信息按钮，进行认证，并获取接口返回结果
