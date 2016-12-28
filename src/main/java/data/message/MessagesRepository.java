package data.message;

import org.jongo.Jongo;
import org.jongo.MongoCollection;
import rx.Observable;

import java.util.UUID;

/**
 * Created by mrsfy on 13-Dec-16.
 */
public class MessagesRepository {

    private static MessagesRepository _instance;

    private MongoCollection messages;

    private MessagesRepository() { }

    public static MessagesRepository getInstance() {

        if (_instance == null)
            _instance = new MessagesRepository();

        return _instance;
    }

    public void setJongo(Jongo jongo) {
        messages = jongo.getCollection("messages");
    }

    public Observable<Message> getMessagesOfUser(String id) {
        return Observable.from(messages.find("{ $or: [{sender._id: #}, {receiver._id: #}]}", id, id).as(Message.class));
    }

    public Observable<Void> save(Message message) {
        return Observable.create(subscriber -> {
            message.setId(UUID.randomUUID().toString());
            messages.save(message);
            subscriber.onCompleted();
        });
    }



}