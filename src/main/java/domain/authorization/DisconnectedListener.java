package domain.authorization;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DisconnectListener;

/**
 * Created by mrsfy on 21-Dec-16.
 */
public class DisconnectedListener implements DisconnectListener {
    @Override
    public void onDisconnect(SocketIOClient socketIOClient) {

        Auth.removeUser(socketIOClient.getSessionId());
    }
}
