package domain.houses;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import data.house.House;
import data.house.HousesRepository;
import domain.SocketListener;

/**
 * Created by mrsfy on 22-Dec-16.
 */
public class SaveHouseListener extends SocketListener<House> {

    public void onData(SocketIOClient socketIOClient, House house) {

        System.out.println("Save house listener");


        HousesRepository.getInstance().save(house);
        socketIOClient.sendEvent("SAVE_HOUSE_RESULT", "Save house successful!");

    }

}
