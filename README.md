# 核心板虚拟键配置 && 面积配置


>板子是  armv7l架构 arm 32位衍生版本 oracle 只提供了jdk8 因此运行此项目时请使用jdk8 编译运行

## jdk 8 Download
- <a href="https://rovmaker.oss-cn-shanghai.aliyuncs.com/public/jdk-8u333-linux-arm32.tar.gz"> jdk-8u333-linux-arm32.tar.gz </a>
- <a href="https://rovmaker.oss-cn-shanghai.aliyuncs.com/public/jdk-8u333-windows-x64.exe"> jdk-8u333-windows-x64.exe </a>

## 一些注意事项
> 一定要注入 ServerEndpointExporter
>
> 可以用@Configuration注入 也可以在Application.java中添加注解
>
> 本来为了安全是要在核心板上装nginx从而将端口转发出去的 暂时没找到 armv7l  nginx

## websocket 依赖 xml -> pom.xml

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-websocket</artifactId>
</dependency>
```
>
> 
```java
private static final Set<Session> list = new HashSet<>();
```
此list保存着所有session 如果 client 正常进行断开会通过
```java
 @OnClose
public void close(Session session){
        list.remove(session);
}
```
进行自动删除
python 和 js(uniapp) 断开的代码如下

py
```python
ws.close()
```

js
```js
onUnload(() => uni.closeSocket())
```

```java
@OnMessage
public void message( Session session){
}
```

@OnMessage可以获取当前的session 
```java
  @OnMessage
    public void message(byte[] msg Session sion){
        list.forEach(s ->{
            try {
                s.getBasicRemote().sendBinary(ByteBuffer.wrap(msg));
//                System.out.println("onMessage:"+ Arrays.toString(msg));
            }catch (IOException i){
                i.printStackTrace();
            }
        });
    }
```
之后可以做相应处理

