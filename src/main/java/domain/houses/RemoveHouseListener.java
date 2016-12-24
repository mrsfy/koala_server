package domain.houses;

import com.corundumstudio.socketio.SocketIOClient;
import data.house.House;
import data.house.HousesRepository;
import domain.DefaultSubscriber;
import domain.SocketListener;

/**
 * Created by mrsfy on 24-Dec-16.
 */
public class RemoveHouseListener extends SocketListener<String> {
    @Override
    public void onData(SocketIOClient client, String id) {
        HousesRepository.getInstance().removeById(id).subscribe(new DefaultSubscriber<Void>() {

            @Override
            public void onCompleted() {
                client.sendEvent("REMOVE_HOUSE_RESULT", "House removed successfully!");
            }

        });
    }
}
