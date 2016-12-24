package domain;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.user.User;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

/**
 * Created by mrsfy on 23-Dec-16.
 */
public abstract class SocketListener<T> implements DataListener<String> {

    @Override
    public final void onData(SocketIOClient socketIOClient, String str, AckRequest ackRequest) {

        try {


            Class<T> clazz = (Class<T>)
                    ((ParameterizedType) getClass().getGenericSuperclass())
                            .getActualTypeArguments()[0];

            T data = null;
            data = new ObjectMapper().readValue(str, clazz);

            onData(socketIOClient, data);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public abstract void onData(SocketIOClient client, T t);

}
