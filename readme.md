# Triones Message 
对spring项目中用到的一些消息中间件进行封装，解决业务中使用异步消息事件时，
如果需要更换消息中间件，需要修改业务代码的问题。

一般使用的时候，我们只需要区分是队列消息(只有一个订阅者能收到)，还是广播消息(所有订阅者都能收到)即可。

支持的消息类型：
- spring event
- redis pub/sub stream
---
## 使用
1. 引入依赖
```xml
<dependency>
    <groupId>com.triones.message</groupId>
    <artifactId>message-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```
2. 配置消息中间件

一般情况下，直接使用默认配置即可，有需要的可以根据自己的需求进行配置

3. 注册监听
```java
import com.trionesdev.message.core.Message;
import com.trionesdev.message.core.MessageContainer;
import com.trionesdev.message.core.MessageListener;
    
@RequiredArgsConstructor
public class Bootstrap {
    private final MessageContainer messageContainer;
    public void run(){
        //注册队列监听
        messageContainer.addQueueListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                //todo 
            }
        }, "topic");

        //注册广播监听
        messageContainer.addBroadcastListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                //todo 
            }
        }, "topic");
    }
}
```

4. 发送消息

发送消息的时候，同样需要区分是队列消息 还是广播消息

```java
import com.trionesdev.message.core.Message;
import com.trionesdev.message.core.MessageContainer;
import com.trionesdev.message.core.MessageListener;

import java.security.MessageDigestSpi;

@RequiredArgsConstructor
public class Bootstrap {
    private final MessageContainer messageContainer;

    public void run() {
        
        Message message = Message.builder().payload("message").toipc("topic").build();
        //发送队列消息
        messageContainer.publish(message);

        //发送广播消息
        messageContainer.boroadcast(message);
    }
}
```

----
# 互相吹捧，共同进步

<div style="width: 100%;text-align: center;">
   <img src="images/shuque_wx.jpg" width="200px" alt="">
</div>
