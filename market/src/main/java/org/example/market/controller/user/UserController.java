package org.example.market.controller.user;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.example.market.service.ImageService;
import org.example.market.service.JwtService;
import org.example.market.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
private final ImageService imageService;
private final ProfileService profileService;
    private final JwtService jwtService;

    @PostMapping("/upload")
public ResponseEntity<String> uploadAvatarUser(
        @NotBlank @RequestHeader(name = "AccessToken") String token,
        MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.ok()
                    .body("File is empty");
        }
        imageService.addAvatarUser(token,file);
        return ResponseEntity.ok()
                .body("Upload successful");
}

@GetMapping("/settings")
public ResponseEntity<String> settingUser(@NotBlank @RequestHeader(name = "AccessToken") String token) {
    profileService.
}

@GetMapping("/profile/{profileId}")
public ResponseEntity<> getProfile(@PathVariable Long profileId) {

}

}
