package data.house;

import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import rx.Observable;

import java.util.UUID;

/**
 * Created by mrsfy on 03-Dec-16.
 */

public class HousesRepository {

    private static HousesRepository _instance;

    public static HousesRepository getInstance() {
        if (_instance == null)
            _instance = new HousesRepository();

        return _instance;
    }

    private HousesRepository(){ }

    public void setJongo(Jongo jongo) {
        this.houses = jongo.getCollection("houses");
    }



    private MongoCollection houses;


    public Observable<House> findBySeller(String sellerId) {
        return Observable.from(houses.find("{ \"seller._id\": # }", sellerId).as(House.class));
    }

    public Observable<Void> save(House house) {
        if (house.getId() == null)
            house.setId(UUID.randomUUID().toString());

        houses.save(house);

        return Observable.empty();
    }

    public Observable<House> findAll() {

        return Observable.from(houses.find().as(House.class));
    }

    public Observable<Void> removeById(String houseId) {
        houses.remove("{ _id: # }", houseId);
        return Observable.empty();
    }

}