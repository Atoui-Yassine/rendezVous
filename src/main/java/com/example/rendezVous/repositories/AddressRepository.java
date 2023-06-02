package com.example.rendezVous.repositories;

import com.example.rendezVous.models.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AddressRepository extends JpaRepository<Address,Long> {

}
