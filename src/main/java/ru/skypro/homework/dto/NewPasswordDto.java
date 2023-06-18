package ru.skypro.homework.dto;
import lombok.Data;

@Data
public class NewPasswordDto {
    private String currentPassword;
    private String newPassword;

    public CharSequence getPassword() {
        return newPassword;
    }
}
