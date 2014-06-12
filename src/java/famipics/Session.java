package famipics;

import famipics.domain.User;

/**
 *
 * @author guillermo
 */
public class Session {

    private final User user;
    private final String token;

    public Session(User user) {
        this.user = user;
        this.token = new SessionIdentifierGenerator().nextSessionId();
    }

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

}
