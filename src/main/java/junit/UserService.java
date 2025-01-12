package junit;

public interface UserService {
    User create(String firstName,
            String lastName,
            String email,
            String password,
            String repeatPassword);
}
