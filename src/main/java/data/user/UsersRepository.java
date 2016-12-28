package data.user;

import com.mongodb.WriteResult;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import rx.Observable;

import java.util.UUID;

/**
 * Created by mrsfy on 03-Dec-16.
 */

public class UsersRepository {

    private static UsersRepository _instance;

    public static UsersRepository getInstance() {
        if (_instance == null)
            _instance = new UsersRepository();

        return _instance;
    }

    private UsersRepository(){ }

    public void setJongo(Jongo jongo) {
        this.users = jongo.getCollection("users");
    }


    private MongoCollection users;

    public Observable<User> findByUsernameAndPassword(String username, String password) {
        return Observable.just(users.findOne("{username: #, password: #}", username, password).as(User.class));
    }

    public Observable<Void> save(User user) {
        if (user.getId() == null)
            user.setId(UUID.randomUUID().toString());

        return Observable.create(subscriber -> {
            users.save(user);
            subscriber.onCompleted();
        });
    }

    public boolean usernameExists(String username) {
        return users.count("{username: #}", username) > 0;
    }

    public Observable<Void> logout() {

        return Observable.empty();
    }


    public boolean emailExists(String email) {
        return users.count("{ email: # }", email) > 0;
    }
}
