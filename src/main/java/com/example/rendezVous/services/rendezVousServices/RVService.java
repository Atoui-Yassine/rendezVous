package com.example.rendezVous.services.rendezVousServices;

import com.example.rendezVous.exceptions.EntityNotFoundException;
import com.example.rendezVous.models.rendezVous.RendezVous;
import com.example.rendezVous.models.rendezVous.Status;
import com.example.rendezVous.models.userModel.UserModel;
import com.example.rendezVous.repositories.RendezVousRepository;
import com.example.rendezVous.services.userServices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class RVService {
    @Autowired
    private RendezVousRepository rendezVousRepository;
    @Autowired
    private UserService userService;


    public RendezVous createNewRV( RendezVous rendezVous){
      return rendezVousRepository.save(rendezVous);
    }
    public List<RendezVous> findAllRVByUserPro(Long id){

        return rendezVousRepository.findAllByUserProId(id) ;
    }

    public List<RendezVous> findAllRVByClientSortAscDAte(Long id){

        return rendezVousRepository.findRVByClientIdSortedByDateAsc(id) ;
    }

    public void deleteRVById(Long id){
        RendezVous rv=getById(id);
            if(rv.isEnabled()){
                return ;
            }else {
                rv.setEnabled(true);
                rendezVousRepository.save(rv);
            }

    }
    public RendezVous changeStatusRv(Status status,RendezVous rv){
            try{

                rv.setStatus(status);

                return rendezVousRepository.save(rv);
            }catch (IllegalArgumentException e){
                throw new MethodArgumentTypeMismatchException(
                        status, Status.class, "status", null, null);
            }

    }

    public List<RendezVous> getAllRVByUserClient(Long id){

       return rendezVousRepository.findAllByClientId(id);
    }
    public List<RendezVous> getAllRVByClientAndUserPro(Long clientId,Long userProId){
        return rendezVousRepository.findAllByClientIdAndUserProId(clientId,userProId);
    }
    public RendezVous getById(Long id){
        RendezVous rv=rendezVousRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Rendez-vous Not Found with id : " + id));
        return rv;
    }

    public boolean existsConfirmedRendezVousOnDate(LocalDateTime dateOfRV, Long clientId) {
        List<RendezVous> rendezVousList = rendezVousRepository.findByDateOfRVAndClientAndStatus(dateOfRV, clientId, Status.CONFIRMED);
        return !rendezVousList.isEmpty();
    }

    public boolean isTimeWithinRange(Integer timeToCheck, Integer startTime, Integer endTime) {
        return timeToCheck >= startTime && timeToCheck <= endTime;
    }
    public Integer convertStringToMinutes(String timeString) {
        LocalTime time = LocalTime.parse(timeString);
        Integer hours = time.getHour();
        Integer minutes = time.getMinute();
        Integer totalMinutes = hours * 60 + minutes;
        return totalMinutes;
    }


    public int getConfirmedAppointmentCountForUserPro(Long userProId) {
       if(userService.findById(userProId) != null){
           return rendezVousRepository.countByUserProIdAndStatus(userProId, Status.CONFIRMED);

       }else {
           throw new EntityNotFoundException("user_pro Not Found with id : " + userProId);
       }


    }
}
