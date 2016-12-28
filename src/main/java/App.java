import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import data.house.House;
import data.house.HousesRepository;
import data.message.MessagesRepository;
import data.user.User;
import data.user.UsersRepository;
import domain.SocketListener;
import domain.authorization.Auth;
import domain.houses.GetAllHousesListener;
import domain.houses.GetMyOwnPropertiesListener;
import domain.houses.RemoveHouseListener;
import domain.houses.SaveHouseListener;
import domain.authorization.LoginListener;
import domain.authorization.LogoutListener;
import domain.authorization.RegisterListener;
import domain.messaging.GetMessagesListener;
import domain.messaging.SendMessageListener;
import org.jongo.Jongo;

import java.util.Map;

/**
 * Created by mrsfy on 21-Dec-16.
 */
public class App {

    public static void main(String... args) {
        new App();
    }


    private SocketIOServer server;

    public App() {

        Jongo jongo = new Jongo(new MongoClient(new MongoClientURI("mongodb://koala_user:koala123@ds119618.mlab.com:19618/koala")).getDB("koala"));
        UsersRepository.getInstance().setJongo(jongo);
        HousesRepository.getInstance().setJongo(jongo);
        MessagesRepository.getInstance().setJongo(jongo);

        Configuration configuration = new Configuration();
        configuration.setHostname("localhost");
        configuration.setPort(8081);

        server = new SocketIOServer(configuration);

        server.addDisconnectListener(client -> {
            Auth.removeUser(client.getSessionId());
        });


        // Authorization listeners
        addListener("LOGIN", new LoginListener());
        addListener("LOGOUT", new LogoutListener());
        addListener("REGISTER", new RegisterListener());

        // House listeners
        addListener("SAVE_HOUSE", new SaveHouseListener());
        addListener("REMOVE_HOUSE", new RemoveHouseListener());
        addListener("GET_ALL_HOUSES", new GetAllHousesListener());
        addListener("GET_MY_OWN_PROPERTIES", new GetMyOwnPropertiesListener());

        // Message listeners
        addListener("GET_MESSAGES", new GetMessagesListener());
        addListener("SEND_MESSAGE", new SendMessageListener());


        server.start();

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void addListener(String eventName, SocketListener<?> socketListener) {
        server.addEventListener(eventName, String.class, socketListener);
    }
}
