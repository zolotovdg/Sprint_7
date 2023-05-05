public class LoginCourierRequestModel {
    public String login;
    public String password;

    public LoginCourierRequestModel(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
