package com.appsdeveloperblog.app.ws.mobileappws.io.repostories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.appsdeveloperblog.app.ws.mobileappws.io.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.mobileappws.io.entity.UserEntity;
@Repository
public interface AddressRepository extends CrudRepository<AddressEntity,Long> {
	List<AddressEntity> findAllByUserDetails(UserEntity userEntity);;
	AddressEntity findByAddressId(String addressId);

}
