package com.acme.edu.chat;

import com.acme.edu.SysoutCaptureAndAssertionAbility;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.DataOutputStream;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.mockito.Mockito.mock;

public class ChatObserverTest {
    ChatObserver observer;
    User user1;
    User user2;
    User user3;
    @Before
    public void beforeInit(){
        DataOutputStream dataOutputStream = mock(DataOutputStream.class);
        user1 = new User(dataOutputStream);
        user2 = new User(dataOutputStream);
        user3 = new User(dataOutputStream);
        user1.setUserName("Alina");
        user1.setIsAuthenticated(true);
        user1.setRoomId("main");
        user2.setUserName("Albina");
        user2.setIsAuthenticated(true);
        user2.setRoomId("main");
        user3.setUserName("Max");
        user3.setIsAuthenticated(true);
        user3.setRoomId("main");
    }

    @Test
    public void testSubscribeToChat() {
        observer = new ChatObserver(new ConcurrentHashMap<>());
        observer.subscribeToChat("roomId1", user1);
        observer.subscribeToChat("roomId2", user2);
        observer.subscribeToChat("roomId2", user3);
        Assert.assertTrue(observer.chatMembers.containsKey("roomId1"));
        Assert.assertTrue(observer.chatMembers.containsKey("roomId2"));
        Assert.assertTrue(observer.chatMembers.get("roomId1").contains(user1));
        Assert.assertTrue(observer.chatMembers.get("roomId2").contains(user2));
        Assert.assertTrue(observer.chatMembers.get("roomId2").contains(user3));
    }
}