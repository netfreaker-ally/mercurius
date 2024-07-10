package com.Mercurius.Bridge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Mercurius.Bridge.constants.BridgeConstants;
import com.Mercurius.Bridge.dto.ResponseDto;
import com.Mercurius.Bridge.entity.AccountRepresentation;
import com.Mercurius.Bridge.service.IBridgeService;

@RestController
@RequestMapping("/api/users")
public class UserProfilesController {

	@Autowired
	private IBridgeService bridgeService;

	public UserProfilesController(IBridgeService bridgeService) {
		super();
		this.bridgeService = bridgeService;
	}

	@PostMapping
	public ResponseEntity<ResponseDto> createUserProfile(@RequestBody AccountRepresentation userProfile)
			throws Exception {
		bridgeService.createUserProfile(userProfile);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(BridgeConstants.STATUS_201, BridgeConstants.MESSAGE_201));
	}

	@PutMapping
	public ResponseEntity<ResponseDto> updateUserProfile(
			@RequestBody AccountRepresentation userProfile) {
		bridgeService.updateUserProfile(userProfile);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseDto(BridgeConstants.STATUS_200, BridgeConstants.MESSAGE_200));
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<ResponseDto> deleteUserProfile(@PathVariable String userId) {
		bridgeService.deleteUserProfile(userId);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseDto(BridgeConstants.STATUS_200, BridgeConstants.MESSAGE_200));
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
