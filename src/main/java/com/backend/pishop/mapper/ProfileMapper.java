package com.backend.pishop.mapper;

import com.backend.pishop.entity.Account;
import com.backend.pishop.entity.User;
import com.backend.pishop.response.ProfileResponse;

public class ProfileMapper {

    public static ProfileResponse toResponse(Account account) {
        if (account == null) return null;

        User user = account.getUser();

        return new ProfileResponse(
        		account.getId(),
                account.getFirstName(),
                account.getLastName(), // chú ý sửa tên field trong Account
                account.getImage(),
                account.getCreateAt(),
                account.getPoint(),
                account.getRank(),
                user != null ? user.getPhoneNumber() : null,
                account.getEmail(),
                user != null ? user.isGender() : null
                
        );
    }
}
