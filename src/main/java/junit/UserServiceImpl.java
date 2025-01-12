package junit;

import java.util.UUID;

public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public User create(String firstName, String lastName, String email, String password, String repeatPassword) {
        if(firstName == null || firstName.trim().length() == 0) {
            throw new IllegalArgumentException("비어있는 이름");
        }

        if(lastName == null || lastName.trim().length() == 0) {
            throw new IllegalArgumentException("비어있는 성");
        }
        
        User user = new User(firstName, lastName, email, UUID.randomUUID().toString());
        
        boolean isUserCreated;
        
        try {
        	isUserCreated = userRepository.save(user);
        } catch (RuntimeException exception) {
        	throw new UserServiceException(exception.getMessage());
        }
        
        if (!isUserCreated) {
        	throw new UserServiceException("사용자 생성 실패");
        }

        return user;
	}
}