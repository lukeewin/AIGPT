# What is AIGPT?
这是我用Java开发的一个调用ChatGPT API的客户端程序，本程序不是直接调用OpenAI的ChatGPT API，而是经过自己的服务器进行中转，这样方便国内用户使用。也因此本客户端需要搭配对应的服务端来使用。我本来是想打算开发自己来用，后来觉得还是开源出来吧，给想用的人用，当然我开发的这个客户端很简洁，如果你们有能力，也可以自己进行二次开发。
# How to use?
如何使用这个客户端呢，很简单，首先需要把代码拉取到你本地，比如拉取到idea中，然后在src目录中创建ChatGPT.properties的文件，并写入下面三个属性值。
```java
URL="你的接口地址"
CHAT_URL="openai的ChatGPT接口地址"
API_KEY="openai key"
```
说明：你的接口地址，指的是你安装服务端的地址，默认是你服务器ip:/9000/openai/chat
# AIGPT_Server
本客户端需要配合[服务端](https://github.com/lukeewin/AIGPT_Server)一起使用。
