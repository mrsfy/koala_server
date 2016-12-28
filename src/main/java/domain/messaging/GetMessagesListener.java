package domain.messaging;

import com.corundumstudio.socketio.SocketIOClient;
import data.message.Message;
import data.message.MessagesRepository;
import domain.DefaultSubscriber;
import domain.SocketListener;
import domain.authorization.Auth;

import java.util.ArrayList;

/**
 * Created by mrsfy on 27-Dec-16.
 */
public class GetMessagesListener extends SocketListener<Void> {

    @Override
    public void onData(SocketIOClient client, Void aVoid) {

        ArrayList<Message> messages = new ArrayList<>();

        MessagesRepository
                .getInstance()
                .getMessagesOfUser(Auth.getUser(client.getSessionId()).getId())
                .subscribe(new DefaultSubscriber<Message>() {

            @Override
            public void onNext(Message message) { messages.add(message);
            }

            @Override
            public void onCompleted() {
                client.sendEvent("GET_MESSAGES_RESULT", messages);
            }

        });
    }

}
