public class CreateCourierRequestModel {
    public String login;
    public String password;
    public String firstName;

    public CreateCourierRequestModel(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }
}
