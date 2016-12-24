package domain.houses;

import com.corundumstudio.socketio.SocketIOClient;
import data.house.House;
import data.house.HousesRepository;
import domain.DefaultSubscriber;
import domain.SocketListener;

import java.util.ArrayList;

/**
 * Created by mrsfy on 23-Dec-16.
 */
public class GetAllHousesListener extends SocketListener<Void> {


    @Override
    public void onData(SocketIOClient client, Void aVoid) {

        System.out.println("Get All Houses Listener");

        ArrayList<House> houses = new ArrayList<>();

        HousesRepository.getInstance().findAll().subscribe(new DefaultSubscriber<House>() {

            @Override
            public void onNext(House house) {
                houses.add(house);
            }

            @Override
            public void onCompleted() {
                client.sendEvent("GET_ALL_HOUSES_RESULT", houses);
            }

        });

    }

}
