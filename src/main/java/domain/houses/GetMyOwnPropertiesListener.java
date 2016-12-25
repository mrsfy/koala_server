package domain.houses;

import com.corundumstudio.socketio.SocketIOClient;
import data.house.House;
import data.house.HousesRepository;
import domain.DefaultSubscriber;
import domain.SocketListener;
import domain.authorization.Auth;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrsfy on 24-Dec-16.
 */
public class GetMyOwnPropertiesListener extends SocketListener<Void> {

    @Override
    public void onData(SocketIOClient client, Void aVoid) {

        HousesRepository
                .getInstance()
                .findBySeller(Auth.getUser(client.getSessionId()).getId())
                .subscribe(new DefaultSubscriber<House>(){

                    List<House> houses = new ArrayList<House>();

                    @Override
                    public void onNext(House house) {
                        houses.add(house);
                    }

                    @Override
                    public void onCompleted() {
                        client.sendEvent("GET_MY_OWN_PROPERTIES_RESULT", houses);
                    }

                });


    }

}
