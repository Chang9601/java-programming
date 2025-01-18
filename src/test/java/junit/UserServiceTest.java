package junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository usersRepository;
    
    @Mock
    EmailServiceImpl emailService;
    
    String firstName;
    String lastName;
    String email;
    String password;
    String repeatPassword;
    
    @BeforeEach
    void initialize() {
        firstName = "길동";
        lastName  = "홍";
        email = "hongkildong@naver.com";
        password = "12345678";
        repeatPassword = "12345678";
    }
    
    @DisplayName("사용자 생성")
    @Test
    void testCreate_whenUserDetailsProvided_thenReturnsUser() {
        // Arrange
        when(usersRepository.save(any(User.class))).thenReturn(true);

        // Act
        User user = userService.create(firstName, lastName, email, password, repeatPassword);

        // Assert
        assertNotNull(user, "create() 메서드는 null을 반환하면 안된다.");
        assertEquals(firstName, user.getFirstName(), "사용자의 성이 다르다.");
        assertEquals(lastName, user.getLastName(), "사용자의 이름이 다르다.");
        assertEquals(email, user.getEmail(), "사용자의 이메일이 다르다.");
        assertNotNull(user.getId(), "사용자의 아이디가 없다.");
        verify(usersRepository, times(1)).save(any(User.class));
    }    

    @DisplayName("save() 메서드가 RuntimeException 예외를 발생시켜서 UserServiceException 예외 발생")
    @Test
    void testCreate_whenSaveThrowsException_thenThrowsUserServiceException() {
    	when(usersRepository.save(any(User.class))).thenThrow(RuntimeException.class);
    	
    	assertThrows(UserServiceException.class, () -> {
    		userService.create(firstName, lastName, email, password, repeatPassword);
    	}, "UserServiceException이 발생해야 한다.");    	    	
    }
    
    @DisplayName("scheduleConfirmation() 메서드가 EmailConfirmationException 예외를 발생시켜서 UserServiceException 예외 발생")
    @Test
    void testCreate_whenScheduleConfirmationThrowsException_thenThrowsUserServiceException() {
        when(usersRepository.save(any(User.class))).thenReturn(true);
    	doThrow(EmailConfirmationException.class).when(emailService).scheduleConfirmation(any(User.class));
    	//doNothing().when(emailService).scheduleConfirmation(any(User.class));
    	
    	assertThrows(UserServiceException.class, () -> {
    		userService.create(firstName, lastName, email, password, repeatPassword);
    	}, "UserServiceException이 발생해야 한다.");    
    	
    	verify(emailService, times(1)).scheduleConfirmation(any(User.class));
    }
    
    @DisplayName("실제 scheduleConfirmation() 메서드를 호출")
    @Test
    void testCreate_whenUserCreated_thenScheduleConfirmation() {
    	when(usersRepository.save(any(User.class))).thenReturn(true);
    	doCallRealMethod().when(emailService).scheduleConfirmation(any(User.class));
    	
    	userService.create(firstName, lastName, email, password, repeatPassword);
    	
    	verify(emailService, times(1)).scheduleConfirmation(any(User.class));
    }
}