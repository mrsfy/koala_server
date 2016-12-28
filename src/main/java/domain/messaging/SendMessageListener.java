package domain.messaging;

import com.corundumstudio.socketio.SocketIOClient;
import data.message.Message;
import data.message.MessagesRepository;
import domain.DefaultSubscriber;
import domain.SocketListener;

/**
 * Created by mrsfy on 27-Dec-16.
 */
public class SendMessageListener extends SocketListener<Message> {

    @Override
    public void onData(SocketIOClient client, Message message) {
        MessagesRepository
                .getInstance()
                .save(message)
                .subscribe(new DefaultSubscriber<Void>(){
                    @Override
                    public void onCompleted() {
                        client.sendEvent("SEND_MESSAGE_RESULT", "Send message successful!");
                        // client.sendEvent("NEW_MESSAGE");
                        // TODO: trigger the other users' window if online
                    }
                });
    }

}
