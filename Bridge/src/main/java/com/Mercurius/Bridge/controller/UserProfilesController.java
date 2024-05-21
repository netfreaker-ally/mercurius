package com.Mercurius.Bridge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Mercurius.Bridge.entity.AccountRepresentation;
import com.Mercurius.Bridge.service.IBridgeService;

@RestController
@RequestMapping("/bridge/api/users")
public class UserProfilesController {

	@Autowired
	private IBridgeService bridgeService;

	public UserProfilesController(IBridgeService bridgeService) {
		super();
		this.bridgeService = bridgeService;
	}

	@PostMapping
	public ResponseEntity<String> createUserProfile(@RequestBody AccountRepresentation userProfile) throws Exception {
		return ResponseEntity.ok(bridgeService.createUserProfile(userProfile));
	}

	@PutMapping("/{userId}")
	public ResponseEntity<String> updateUserProfile(@PathVariable String userId,
			@RequestBody AccountRepresentation userProfile) {
		return ResponseEntity.ok(bridgeService.updateUserProfile(userId, userProfile));
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<String> deleteUserProfile(@PathVariable String userId) {
		return ResponseEntity.ok(bridgeService.deleteUserProfile(userId));
	}

	@GetMapping("/{userId}")
	public ResponseEntity<AccountRepresentation> getUserProfileById(@PathVariable String userId) {
		return ResponseEntity.ok(bridgeService.getUserProfileById(userId));
	}

	@GetMapping
	public ResponseEntity<List<AccountRepresentation>> listUserProfiles() {
		return ResponseEntity.ok(bridgeService.listUserProfiles());
	}
}
