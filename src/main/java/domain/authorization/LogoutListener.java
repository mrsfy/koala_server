package domain.authorization;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import domain.SocketListener;

/**
 * Created by mrsfy on 21-Dec-16.
 */
public class LogoutListener extends SocketListener<Void> {

    @Override
    public void onData(SocketIOClient client, Void aVoid) {
        System.out.println("Logout listener");
        Auth.removeUser(client.getSessionId());
        client.sendEvent("LOGOUT_RESULT", "Logout Successful");
    }
}
