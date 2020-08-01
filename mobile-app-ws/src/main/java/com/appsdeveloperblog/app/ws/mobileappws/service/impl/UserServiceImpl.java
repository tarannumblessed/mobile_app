package com.appsdeveloperblog.app.ws.mobileappws.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.appsdeveloperblog.app.ws.mobileappws.exceptions.UserServiceException;
import com.appsdeveloperblog.app.ws.mobileappws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.mobileappws.io.repostories.UserRepository;
import com.appsdeveloperblog.app.ws.mobileappws.service.UserService;
import com.appsdeveloperblog.app.ws.mobileappws.shared.Utils;
import com.appsdeveloperblog.app.ws.mobileappws.shared.dto.AddressDto;
import com.appsdeveloperblog.app.ws.mobileappws.shared.dto.UserDto;
import com.appsdeveloperblog.app.ws.mobileappws.ui.model.response.ErrorMessages;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private Utils util;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDto createUser(UserDto userDto) {

		if (userRepository.findByEmail(userDto.getEmail()) != null)
			throw new RuntimeException("email already exixt");
		for (int i = 0; i < userDto.getAddresses().size(); i++) {
			AddressDto address = userDto.getAddresses().get(i); 
			address.setUserDetails(userDto);
			address.setAddressId(util.generateAddressId(30));
			userDto.getAddresses();
		}

		ModelMapper modelMapper = new ModelMapper();
		UserEntity entity = modelMapper.map(userDto, UserEntity.class);

		entity.setUserId(util.getUserId(10));
		entity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

		UserEntity storedUserDetails = userRepository.save(entity);

		UserDto returnValue = modelMapper.map(storedUserDetails, UserDto.class);

		return returnValue;

	}

	/*
	 * CODE TO LOAD USER DETAILS BY USERNAME//this method will help to load the user
	 */
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null)
			throw new UsernameNotFoundException(email);

		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());

	}

	/* code is for header should contain userid */
	@Override
	public UserDto getUser(String email) {
		UserEntity entity = userRepository.findByEmail(email);
		if (entity == null)
			throw new UsernameNotFoundException(email);
		UserDto dto = new UserDto();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		UserEntity storeDetails = userRepository.findByUserId(userId);
		if (storeDetails == null)
			throw new UsernameNotFoundException("No user found  with" + userId + "id");
		UserDto dto = new UserDto();
		BeanUtils.copyProperties(storeDetails, dto);
		return dto;
	}

	@Override
	public UserDto updateUser(String userId, UserDto user) {
		UserEntity entity = userRepository.findByUserId(userId);
		if (entity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		UserDto dto = new UserDto();
		entity.setFirstName(user.getFirstName());
		entity.setLastName(user.getLastName());
		UserEntity storedValues = userRepository.save(entity);

		BeanUtils.copyProperties(storedValues, dto);
		return dto;
	}

	@Override
	public void deleteUser(String userId) {
		UserEntity entity = userRepository.findByUserId(userId);
		if (entity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		userRepository.delete(entity);

	}

	@Override
	public List<UserDto> getUsers(int page, int limit) {
		List<UserDto> returnValue = new ArrayList<UserDto>();
		if (page > 0)
			page = page - 1;
		Pageable pageable = PageRequest.of(page, limit);
		Page<UserEntity> userPage = userRepository.findAll(pageable);
		for (UserEntity user : userPage) {
			UserDto dto = new UserDto();
			BeanUtils.copyProperties(user, dto);
			returnValue.add(dto);

		}

		return returnValue;
	}

}
