# What is AIGPT?
这是我用JavaFX开发的一个调用ChatGPT API的客户端程序。我本来是想打算开发自己来用，后来觉得还是开源出来吧，给想用的人用，当然我开发的这个客户端很简洁，如果你们有能力，也可以自己进行二次开发。具体效果可以看一下我发布到网上的[视频](https://www.bilibili.com/video/BV1Cs4y1v7aT/)。
# How to use?
如何使用这个客户端呢，很简单，首先需要把代码拉取到你本地，比如拉取到idea中，然后在src目录中创建ChatGPT.properties的文件，并写入下面三个属性值。
本客户端需要配合[服务端](https://github.com/lukeewin/AIGPT_Server)一起使用。如果想要看视频，可以点击[这里](https://www.bilibili.com/video/BV1VW4y1Z7r6/)
```java
URL="你的接口地址"
CHAT_URL="openai的ChatGPT接口地址"
API_KEY="openai key"
```
说明：你的接口地址，指的是你安装服务端的地址，默认是你服务器ip:/9000/openai/chat
