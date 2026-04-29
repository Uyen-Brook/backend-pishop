package com.backend.pishop.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.backend.pishop.request.AddressRequest;
import com.backend.pishop.request.ChangePasswordRequest;
import com.backend.pishop.request.UpdateAccountRequest;
import com.backend.pishop.request.UpdateProfileRequest;
import com.backend.pishop.request.UpdateUserRequest;
import com.backend.pishop.service.ProfileService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/user/profile")
@RequiredArgsConstructor
public class ProfileController {
	private final ProfileService profileService;
	
	// get profile infor by account id
	@GetMapping("/infor/{accountId}")
	public ResponseEntity<?> getProfile(@PathVariable Long accountId) {
	    return ResponseEntity.ok(profileService.getProfile(accountId));
	}
	
	// update profile
	@PutMapping("/infor/update/{id}")
	public ResponseEntity<?> updateProfile(@PathVariable Long id,
										   @RequestBody UpdateProfileRequest req) {
		return ResponseEntity.ok(profileService.updateProfile(id, req));
	}
	
	//2. update avatar
    @PostMapping("/avatar/{id}")
    public ResponseEntity<?> upload(@PathVariable Long id,
                                    @RequestParam MultipartFile file) throws IOException {
        return ResponseEntity.ok(profileService.uploadAvatar(id, file));
    }
    
    // get all address by account id
    @GetMapping("/address/{accountId}")
    public ResponseEntity<?> getAll(@PathVariable Long accountId) {
        return ResponseEntity.ok(profileService.getAllByAccount(accountId));
    }
    
    // 4. add address by id
    @PostMapping("address/{accountId}")
    public ResponseEntity<?> add(@PathVariable Long accountId,
                                @RequestBody AddressRequest req) {
        return ResponseEntity.ok(profileService.addAddress(accountId, req));
    }
    
    // 5. update address by id
    @PutMapping("/address/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody AddressRequest req) {
        return ResponseEntity.ok(profileService.updateAddress(id, req));
    }
    
    // 6. set default address
    @PutMapping("/address/{id}/default")
    public ResponseEntity<?> setDefault(@PathVariable Long id,
                                        @RequestParam Long accountId) {
    	profileService.setDefault(id, accountId);
        return ResponseEntity.ok("ok");
    }
    
    // delete address by id
    @DeleteMapping("/address/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
    	profileService.deleteAddress(id);
        return ResponseEntity.ok("deleted");
    }
    
    // delete multiple address by id
    @DeleteMapping("/address/multi")
    public ResponseEntity<?> deleteMulti(@RequestBody List<Long> ids) {
    	profileService.deleteMultiple(ids);
        return ResponseEntity.ok("deleted");
    }
    
    @PutMapping("/password/update/{id}")
    public ResponseEntity<?> changePassword(
            @PathVariable Long id,
            @RequestBody ChangePasswordRequest req
    ) {

        profileService.changePassword(id, req);

        return ResponseEntity.ok("Đổi mật khẩu thành công");
    }
}
