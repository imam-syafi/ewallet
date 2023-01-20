package sg.edts.ewallet.dto.response;

import sg.edts.ewallet.entity.UserEntity;

public record UserInfoDto(String username, String ktp) {

    public static UserInfoDto from(UserEntity entity) {
        return new UserInfoDto(entity.getUsername(), entity.getKtp());
    }
}
