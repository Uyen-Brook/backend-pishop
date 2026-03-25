package com.backend.pishop.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.pishop.entity.Account;
import com.backend.pishop.entity.Address;
import com.backend.pishop.entity.Province;
import com.backend.pishop.entity.User;
import com.backend.pishop.entity.Ward;
import com.backend.pishop.mapper.AddressMapper;
import com.backend.pishop.mapper.ProfileMapper;
import com.backend.pishop.repository.AccountRepository;
import com.backend.pishop.repository.AddressRepository;
import com.backend.pishop.repository.ProvinceRepository;
import com.backend.pishop.repository.UserRepository;
import com.backend.pishop.repository.WardRepository;
import com.backend.pishop.request.AddressRequest;
import com.backend.pishop.request.RegisterRequest;
import com.backend.pishop.request.UpdateAccountRequest;
import com.backend.pishop.request.UpdateProfileRequest;
import com.backend.pishop.request.UpdateUserRequest;
import com.backend.pishop.response.AddressResponse;
import com.backend.pishop.response.ProfileResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {
	private final UserRepository userRepository;
	private final AccountRepository accountRepository;
	private final AddressRepository addressRepository;
	private final ProvinceRepository ProvinceRepository;
	private final WardRepository WardRepository;
	private final PasswordEncoder passwordEncoder;
	private final CloudinaryService CloudinaryService;
	
	// 1. register
	public Account register(RegisterRequest req) {
	    Account acc = new Account();
	    acc.setFirstName(req.getFirstName());
	    acc.setLastName(req.getLastName());
	    acc.setEmail(req.getEmail());
	    acc.setPassword(passwordEncoder.encode(req.getPassword()));
	    acc.setActive(true);
	    acc.setCreateAt(LocalDateTime.now());

	    // tạo User
	    User user = new User();
	    user.setAccount(acc);

	    acc.setUser(user);

	    return accountRepository.save(acc);
	}
	//get profile infor by account id
	public ProfileResponse getProfile(Long accountId) {
	    ProfileResponse acc = accountRepository.findById(accountId)
	    		.map(ProfileMapper::toResponse).orElseThrow();
	    return acc;
	}
	//2 . update profile
	public Account updateProfile(Long accountId, UpdateProfileRequest req) {
	    Account acc = accountRepository.findById(accountId).orElseThrow();

	    // ===== Account =====
	    if (req.getFirstName() != null) {
	        acc.setFirstName(req.getFirstName());
	    }

	    if (req.getLastName() != null) {
	        acc.setLastName(req.getLastName());
	    }

	    if (req.getEmail() != null) {
	        acc.setEmail(req.getEmail());
	    }

	    if (req.getPassword() != null) {
	        acc.setPassword(passwordEncoder.encode(req.getPassword()));
	    }

	    // ===== User =====
	    User user = acc.getUser();

	    if (user == null) {
	        user = new User();
	        user.setAccount(acc);
	        acc.setUser(user);
	    }

	    if (req.getDob() != null) {
	        user.setDateOfBirth(req.getDob());
	    }

	    if (req.getPhone() != null) {
	        user.setPhoneNumber(req.getPhone());
	    }

	    user.setGender(req.isGender());

	    // chỉ cần save account (cascade sẽ save user)
	    return accountRepository.save(acc);
	}
	
	public Account updateAccount(Long id, UpdateAccountRequest req) {
	    Account acc = accountRepository.findById(id).orElseThrow();

	    acc.setFirstName(req.getFirstName());
	    acc.setLastName(req.getLastName());
	    acc.setEmail(req.getEmail());

	    if (req.getPassword() != null) {
	        acc.setPassword(passwordEncoder.encode(req.getPassword()));
	    }

	    return accountRepository.save(acc);
	}
	
	// 3. update user
	public User updateUser(Long accountId, UpdateUserRequest req) {
	    Account acc = accountRepository.findById(accountId).orElseThrow();

	    User user = acc.getUser();
	    user.setDateOfBirth(req.getDob());
	    user.setPhoneNumber(req.getPhone());
	    user.setGender(req.isGender());

	    return userRepository.save(user);
	}
	
	// 4. update avatar
	public String uploadAvatar(Long accountId, MultipartFile file) {
	    // 1. Lấy account
	    Account account = accountRepository.findById(accountId)
	            .orElseThrow(() -> new RuntimeException("Account not found"));

	    // 2. Upload ảnh lên Cloudinary
	    String imageUrl = CloudinaryService.uploadImage(file);
		
	    // 3. Lưu URL vào account
	    account.setImage(imageUrl);
	    accountRepository.save(account);

	    // 4. Trả về URL
	    return imageUrl;
	}
	
	//get all address by account id
	public List<AddressResponse> getAllByAccount(Long accountId) {
	    return addressRepository.findByAccountId(accountId)
	    		.stream()
	    		.map(AddressMapper::toResponse)
	    		.toList();
	}
	
	// 5. update address
	public Address updateAddress(Long id, AddressRequest req) {
	    Address addr = addressRepository.findById(id).orElseThrow();

	    addr.setFullName(req.getFullName());
	    addr.setPhone(req.getPhone());
	    addr.setSpecificAddress(req.getSpecificAddress());

	    return addressRepository.save(addr);
	}
	
	// 6. add Address
	public Address addAddress(Long accountId, AddressRequest req) {
	    Account acc = accountRepository.findById(accountId).orElseThrow();

	    Province province = ProvinceRepository.findById(req.getProvinceCode()).orElseThrow();
	    Ward ward = WardRepository.findById(req.getWardCode()).orElseThrow();

	    Address addr = new Address();
	    addr.setFullName(req.getFullName());
	    addr.setPhone(req.getPhone());
	    addr.setSpecificAddress(req.getSpecificAddress());
	    addr.setProvince(province);
	    addr.setWard(ward);
	    addr.setAccount(acc);

	    return addressRepository.save(addr);
	}
	//7. set address default
	public void setDefault(Long addressId, Long accountId) {
	    List<Address> list = addressRepository.findByAccountId(accountId);

	    for (Address a : list) {
	        a.setDefault(false);
	    }

	    Address selected = addressRepository.findById(addressId).orElseThrow();
	    selected.setDefault(true);
	}
	
	// 8. delete address
	public void deleteAddress(Long id) {
	    addressRepository.deleteById(id);
	}
	
	// 9. delete multile address
	public void deleteMultiple(List<Long> ids) {
	    addressRepository.deleteAllById(ids);
	}
}
