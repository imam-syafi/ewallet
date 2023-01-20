package sg.edts.ewallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edts.ewallet.dto.response.UserBalanceDto;
import sg.edts.ewallet.dto.response.UserInfoDto;
import sg.edts.ewallet.entity.UserEntity;
import sg.edts.ewallet.exception.KtpTakenException;
import sg.edts.ewallet.exception.PasswordInvalidException;
import sg.edts.ewallet.exception.UserNotFoundException;
import sg.edts.ewallet.exception.UsernameTakenException;
import sg.edts.ewallet.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void register(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new UsernameTakenException();
        }

        userRepository.save(new UserEntity(username, password));
    }

    public void changePassword(String username, String oldPassword, String newPassword) {
        UserEntity entity = userRepository
                .findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        if (!entity.getPassword().equals(oldPassword)) {
            throw new PasswordInvalidException();
        }

        entity.setPassword(newPassword);

        userRepository.save(entity);
    }

    public void addKtp(String username, String ktp) {
        UserEntity entity = userRepository
                .findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        if (userRepository.existsByKtp(ktp)) {
            throw new KtpTakenException();
        }

        entity.setKtp(ktp);

        userRepository.save(entity);
    }

    public UserInfoDto getInfo(String username) {
        return userRepository
                .findByUsername(username)
                .map(UserInfoDto::from)
                .orElseThrow(UserNotFoundException::new);
    }

    public UserBalanceDto getBalance(String username) {
        return userRepository
                .findByUsername(username)
                .map(UserBalanceDto::from)
                .orElseThrow(UserNotFoundException::new);
    }
}
