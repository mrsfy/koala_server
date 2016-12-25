package domain.authorization;

import com.corundumstudio.socketio.SocketIOClient;
import data.user.User;
import data.user.UsersRepository;
import domain.DefaultSubscriber;
import domain.SocketListener;


/**
 * Created by mrsfy on 22-Dec-16.
 */
public class RegisterListener extends SocketListener<User> {

    @Override
    public void onData(SocketIOClient client, User user) {
        System.out.println("Register Listener");

        if (UsersRepository.getInstance().usernameExists(user.getUsername()))
            client.sendEvent("REGISTER_RESULT", "USERNAME_EXISTS");
        else if (UsersRepository.getInstance().emailExists(user.getEmail()))
            client.sendEvent("REGISTER_RESULT", "EMAIL_EXISTS");
        else {
            System.out.println(user);
            UsersRepository.getInstance().save(user).subscribe(new DefaultSubscriber<Void>(){
                @Override
                public void onCompleted() {
                    client.sendEvent("REGISTER_RESULT", "Register Successful!");
                }
            });
        }
    }

}