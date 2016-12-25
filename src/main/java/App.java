import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import data.house.House;
import data.house.HousesRepository;
import data.user.User;
import data.user.UsersRepository;
import domain.SocketListener;
import domain.houses.GetAllHousesListener;
import domain.houses.GetMyOwnPropertiesListener;
import domain.houses.RemoveHouseListener;
import domain.houses.SaveHouseListener;
import domain.authorization.LoginListener;
import domain.authorization.LogoutListener;
import domain.authorization.RegisterListener;
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


        Configuration configuration = new Configuration();
        configuration.setHostname("localhost");
        configuration.setPort(8081);

        server = new SocketIOServer(configuration);


        addListener("LOGIN", new LoginListener());
        addListener("LOGOUT", new LogoutListener());
        addListener("REGISTER", new RegisterListener());

        addListener("SAVE_HOUSE", new SaveHouseListener());
        addListener("REMOVE_HOUSE", new RemoveHouseListener());
        addListener("GET_ALL_HOUSES", new GetAllHousesListener());
        addListener("GET_MY_OWN_PROPERTIES", new GetMyOwnPropertiesListener());


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
