package com.appsdeveloperblog.app.ws.mobileappws.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appsdeveloperblog.app.ws.mobileappws.io.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.mobileappws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.mobileappws.io.repostories.AddressRepository;
import com.appsdeveloperblog.app.ws.mobileappws.io.repostories.UserRepository;
import com.appsdeveloperblog.app.ws.mobileappws.service.AddressService;
import com.appsdeveloperblog.app.ws.mobileappws.shared.dto.AddressDto;

@Service
public class AddressServiceImpl implements AddressService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AddressRepository addressRepository;

	@Override
	public List<AddressDto> getAddress(String userId) {
		List<AddressDto> returnValue = new ArrayList<>();
		ModelMapper modelMapper = new ModelMapper();
		UserEntity userEntity = userRepository.findByUserId(userId);
		if (userEntity == null)
			return returnValue;
		Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);
		for (AddressEntity addressEntity : addresses) {
			returnValue.add(modelMapper.map(addressEntity, AddressDto.class));
		}

		return returnValue;
	}


	public AddressDto getAddres(String addressId) {
		AddressDto addressDto=null;
		AddressEntity addressEntity=addressRepository.findByAddressId(addressId);
		if(addressEntity!=null)
		{
			addressDto=new ModelMapper().map(addressEntity,AddressDto.class);
		}
		
		return addressDto;
	}

}
