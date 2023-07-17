package ru.skypro.homework.service.impl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.mappers.UserMapper;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.repositories.AdsRepository;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.service.ImageService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceImplTest {

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private UserServiceImpl userService;

    @Mock
    private Authentication authentication;

    @Mock
    private UserDetails userDetails;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AdsRepository adsRepository;

    @Mock
    private ImageService imageService;

    @InjectMocks
    private AdsServiceImpl adsService;

@Test
public void testGetUser_WithNonExistingUser_ShouldReturnEmptyOptional() {
    // Arrange
    String email = "nonexisting@example.com";

    // Act
    Optional<UserDto> userDtoOptional = userService.getUser(email);

    // Assert
    Assertions.assertFalse(userDtoOptional.isPresent());
}

    @Test
    public void testRemoveAd_NonExistingId_ReturnsFalse3() {
        // Arrange
        Integer id = 1;
        Optional<Ads> adOptional = Optional.empty();
        when(adsRepository.findById(id)).thenReturn(adOptional);

        // Act
        boolean result = adsService.removeAd(id);

        // Assert
        assertFalse(result);
        verify(adsRepository, never()).deleteById(id);
    }
        @Test
    public void testGetAds_NonExistingId_ThrowsIllegalArgumentException2() {
        // Arrange
        Integer id = 1;
        when(adsRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> adsService.getAds(id));
    }

}