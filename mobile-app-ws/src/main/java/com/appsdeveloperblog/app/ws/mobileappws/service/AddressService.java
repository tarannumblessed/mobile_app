package com.appsdeveloperblog.app.ws.mobileappws.service;

import java.util.List;

import com.appsdeveloperblog.app.ws.mobileappws.shared.dto.AddressDto;

public interface AddressService {
	List<AddressDto>getAddress(String userId);
	AddressDto getAddres(String addressId);

}
