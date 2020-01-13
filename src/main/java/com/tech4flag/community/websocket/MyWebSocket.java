package com.tech4flag.community.websocket;

import com.google.gson.Gson;
import com.tech4flag.community.model.Chat;
import com.tech4flag.community.model.User;
import com.tech4flag.community.service.ChatService;
import com.tech4flag.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-12-20 09:36
 */
@ServerEndpoint("/websocket/{fromUserId}")
@Component
public class MyWebSocket {


    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    public static MyWebSocket myWebSocket;

    @PostConstruct
    public void init(){
        myWebSocket = this;
        myWebSocket.chatService = this.chatService;
        myWebSocket.userService = this.userService;
    }
    /**
     *用来存放每个客户端对应的MyWebSocket对象。
     */
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    private Integer fromUserId;

    //用来记录sessionId和该session进行绑定
    private static Map<Integer,Session> map = new HashMap<Integer, Session>();

    /**
      * 连接建立成功调用的方法
      */
    @OnOpen
    public void onOpen(Session session,@PathParam("fromUserId")Integer fromUserId) {
        this.session = session;
        this.fromUserId = fromUserId;
        map.put(fromUserId,session);
        //加入set中
        webSocketSet.add(this);
        System.out.println("有新连接加入！当前在线人数为" + webSocketSet.size());
        this.session.getAsyncRemote().sendText("恭喜您成功连接上WebSocket-->当前在线人数为："+webSocketSet.size());
     }

    /**
     * 连接关闭调用的方法
     */
     @OnClose
     public void onClose(@PathParam("fromUserId")Integer fromUserId){
         //从set中删除
        webSocketSet.remove(this);
        map.remove(fromUserId);
        System.out.println("有一连接关闭！当前在线人数为" + webSocketSet.size());
    }


     /**
      * 收到客户端消息后调用的方法
      *
      * @param message 客户端发送过来的消息*/
     @OnMessage
     public void onMessage(String message,@PathParam("fromUserId")Integer fromUserId, Session session) {
         System.out.println("来自客户端的消息:" + message);
         Gson gson = new Gson();
         Chat chat = gson.fromJson(message, Chat.class);
         User user = null;
         //将信息存入到数据库
         if (chat != null){
             UserService userService = myWebSocket.userService;
             ChatService chatService = myWebSocket.chatService;
             chatService.insertChat(chat);
             //创建通知
             chatService.createNotify(fromUserId,chat.getToUserId(),3);
             user = userService.selectUserById(fromUserId);
         }
         //发送者的session
         Session fromUserSession = map.get(chat.getFromUserId());
         //接收者的session
         Session toUserSession = map.get(chat.getToUserId());
         if (toUserSession != null){
             fromUserSession.getAsyncRemote().sendText(user.getName()+"： "+chat.getMsg());
             toUserSession.getAsyncRemote().sendText(user.getName()+"： "+chat.getMsg());
         }else {
//             fromUserSession.getAsyncRemote().sendText("系统消息：对方不在线");
             fromUserSession.getAsyncRemote().sendText(user.getName()+"： "+chat.getMsg());
         }
         //群发消息
//         broadcast(message);
     }
     /**
      * 发生错误时调用
      *
      */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
     }
     /**
      * 群发自定义消息
      * */
     public void broadcast(String message){
         for (MyWebSocket item : webSocketSet) {
             //同步异步说明参考：http://blog.csdn.net/who_is_xiaoming/article/details/53287691
             //this.session.getBasicRemote().sendText(message);
             //异步发送消息.
             item.session.getAsyncRemote().sendText(message);
         }
     }







}
