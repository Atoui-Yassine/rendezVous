package com.example.rendezVous.repositories;

import com.example.rendezVous.models.userModel.ExtraInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtraInfoRepository extends JpaRepository<ExtraInfo,Long> {

}
