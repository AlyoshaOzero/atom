package ru.atom.lecture07.server.service;

import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.atom.lecture07.server.dao.MessageDao;
import ru.atom.lecture07.server.dao.UserDao;
import ru.atom.lecture07.server.model.Message;
import ru.atom.lecture07.server.model.User;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ChatService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ChatService.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private MessageDao messageDao;

    @Nullable
    public User getLoggedIn(@NotNull String name) {
        return userDao.getByLogin(name);
    }

    @Nullable
    public List<User> getAllUsers() {
        return (List<User>) userDao.findAll();
    }

    public void login(@NotNull String login) {
        User user = new User();
        userDao.save(user.setLogin(login));
        log.info("[" + login + "] logged in");
    }

    public void logout(@NotNull User user) {
        userDao.delete(user);
        log.info("[" + user.getLogin() + "] logged out");
    }

    @NotNull
    public List<User> getOnlineUsers() {
        return Lists.newArrayList(userDao.findAll());
    }

    @Nullable
    public List<Message> getAllMessages() {
        return (List<Message>) messageDao.findAll();
    }

    public void sendMessage(@NotNull String name, @NotNull String msg) {
        User user = userDao.getByLogin(name);
        Message message = new Message();
        message.setTime(new Date());
        message.setUser(user);
        message.setValue(msg);
        messageDao.save(message);
        log.info("[" + name + "]: " + msg);
    }
}