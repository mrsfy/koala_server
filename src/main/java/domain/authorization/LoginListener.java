package domain.authorization;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.user.User;
import data.user.UsersRepository;
import domain.DefaultSubscriber;
import domain.SocketListener;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by mrsfy on 21-Dec-16.
 */
public class LoginListener extends SocketListener<Map> {

    @Override
    public void onData(SocketIOClient client, Map dat) {

        Map<String, String> data = (Map<String, String>) dat;
        System.out.println("Login Listener");


        UsersRepository.getInstance()
                .findByUsernameAndPassword(data.get("username"), data.get("password"))
                .subscribe(new DefaultSubscriber<User>(){
                    @Override
                    public void onNext(User user) {

                        if (user != null)
                            Auth.setUser(client.getSessionId(), user);

                        client.sendEvent("LOGIN_RESULT", user);
                    }
                });
    }
}
