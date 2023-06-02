package com.example.rendezVous.repositories;

import com.example.rendezVous.models.rendezVous.RendezVous;
import com.example.rendezVous.models.rendezVous.Status;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Lazy
public interface RendezVousRepository extends JpaRepository<RendezVous,Long> {

    @Query("select r from RendezVous r where r.userPro.id = ?1")
    public List<RendezVous> findAllByUserProId(Long id);


    @Query("select r from RendezVous r where r.client.id = ?1")
    public List<RendezVous> findAllByClientId(Long id);

    @Query("select r from RendezVous r where r.client.id = ?1 and r.userPro.id = ?2")
    public List<RendezVous> findAllByClientIdAndUserProId(Long clientId, Long userProId);
    @Query("SELECT r FROM RendezVous r WHERE r.client.id = ?1 AND r.dateOfRV >= CURRENT_DATE ORDER BY r.dateOfRV ASC")
    public List<RendezVous> findRVByClientIdSortedByDateAsc(Long id);

    @Query("select r from RendezVous r where r.dateOfRV = ?1 and r.client.id = ?2 and r.status = ?3")
    List<RendezVous> findByDateOfRVAndClientAndStatus(LocalDateTime dateOfRV, Long clientId, Status status );
    @Query("select count(r) from RendezVous r where r.userPro.id = ?1 and r.status = ?2")
    int countByUserProIdAndStatus(Long userProId, Status status);
}
