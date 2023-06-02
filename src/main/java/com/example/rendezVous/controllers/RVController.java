package com.example.rendezVous.controllers;

import com.example.rendezVous.DTOs.request.RvDto;
import com.example.rendezVous.DTOs.request.StatusRVConvert;
import com.example.rendezVous.exceptions.EntityNotFoundException;
import com.example.rendezVous.models.rendezVous.RendezVous;
import com.example.rendezVous.models.rendezVous.Status;
import com.example.rendezVous.models.userModel.UserModel;
import com.example.rendezVous.repositories.RendezVousRepository;
import com.example.rendezVous.services.rendezVousServices.RVService;
import com.example.rendezVous.services.userServices.UserService;
import com.example.rendezVous.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/Rv")
public class RVController {

    @Autowired
    private RVService rvService;
    @Autowired
    private UserService userService;
    @Autowired
    private RendezVousRepository rendezVousRepository;
    @Autowired
    private TokenUtils tokenUtils;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/add/{id_pro}")
    public ResponseEntity<?> addRendezVous(@Valid @RequestBody RvDto rvDto , @PathVariable Long id_pro){
        UserModel user_pro=userService.findById(id_pro);
        Long clientId=tokenUtils.ExtractId();
        if (rvService.existsConfirmedRendezVousOnDate(rvDto.getDateOfRV(), clientId)) {
            List<RendezVous> rvs=rendezVousRepository.findByDateOfRVAndClientAndStatus(rvDto.getDateOfRV(),clientId, Status.CONFIRMED);
            Integer heureDebut =rvService.convertStringToMinutes(rvs.get(0).getHeure());
            if (rvService.isTimeWithinRange(rvService.convertStringToMinutes(rvDto.getHeure()),heureDebut ,heureDebut+user_pro.getProfilePro().getDuration() )){
                return new ResponseEntity<>("Impossible de créer le rendez-vous. Un rendez-vous confirmé existe déjà à cette date.", HttpStatus.BAD_REQUEST);

            }
        }
      RendezVous rv =  RendezVous.builder()
               .title(rvDto.getTitle())
               .heure(rvDto.getHeure())
               .client(userService.findById(tokenUtils.ExtractId()))
               .description(rvDto.getDescription())
               .price(user_pro.getProfilePro().getPriceHeure())
               .dateOfRV(rvDto.getDateOfRV())
               .status(Status.SCHEDULED)
              .userPro(user_pro)
        .build();
        return new ResponseEntity(rvService.createNewRV(rv), HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{clientId}")
    public ResponseEntity<List<RendezVous>> getAllRVByUserClient(@PathVariable Long clientId){

           return new ResponseEntity<>(rvService.getAllRVByUserClient(clientId),HttpStatus.OK);

    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/AllRV")
    public ResponseEntity<List<RendezVous>> getAllRVByClientId(){
        Long clientId=tokenUtils.ExtractId();
        return new ResponseEntity<>(rvService.findAllRVByClientSortAscDAte(clientId),HttpStatus.OK);
    }


    @GetMapping("/{clientId}/{userProId}")
    public ResponseEntity<?> getAllRVByUserClientAndUserPro(@PathVariable Long clientId,@PathVariable Long userProId){
      try {
          return new ResponseEntity<>(rvService.getAllRVByClientAndUserPro(clientId, userProId),HttpStatus.OK);
      }catch (EntityNotFoundException ex){
          throw new EntityNotFoundException("userClient Not Found with id : " + clientId +" Or userPro Not Found with id :" + userProId );
      }

    }
    @PreAuthorize("hasRole('ROLE_PRO')")
    @PostMapping("changeStatus/{rvId}")
    public ResponseEntity<?> UserProChangeStatusOfRv(@PathVariable Long rvId ,@RequestBody StatusRVConvert statusRVConvert){
            Long userProId=tokenUtils.ExtractId();
            RendezVous rv=rvService.getById(rvId);
            if(rv.getUserPro().getId() == userProId){
                switch (statusRVConvert.getStatus()) {
                    case CONFIRMED:
                        return new ResponseEntity<>( rvService.changeStatusRv(Status.CONFIRMED,rv),HttpStatus.OK);
                    case REJECTED:
                        return new ResponseEntity<>( rvService.changeStatusRv(Status.REJECTED,rv),HttpStatus.OK);
                    case FINISHED:
                        return new ResponseEntity<>( rvService.changeStatusRv(Status.FINISHED,rv),HttpStatus.OK);
                    default:
                        return new ResponseEntity<>( rv,HttpStatus.OK);
                }

            }else {
                return new ResponseEntity<>("Unauthorized access", HttpStatus.UNAUTHORIZED);
            }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("UserChangeStatus/{rvId}")
    public ResponseEntity<?> UserClientChangeStatusOfRv(@PathVariable Long rvId ,@RequestBody StatusRVConvert statusRVConvert){
        Long userClientId=tokenUtils.ExtractId();
        RendezVous rv=rvService.getById(rvId);
        if(rv.getClient().getId() == userClientId){
                    rv.setEnabled(true);
                    return new ResponseEntity<>( rvService.changeStatusRv(Status.CANCELED,rv),HttpStatus.OK);
    }else {
            return new ResponseEntity<>("Unauthorized access", HttpStatus.UNAUTHORIZED);
        }
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("update/{idRv}")
    public ResponseEntity<?> UpdateRv(@RequestBody RvDto rvDto,@PathVariable Long idRv){
        RendezVous rv=rvService.getById(idRv);
        Long userId=tokenUtils.ExtractId();
        if(userId == rv.getClient().getId()){
            rv.setTitle(rvDto.getTitle());
            rv.setDescription(rvDto.getDescription());
            rv.setStatus(Status.EXCHANGE);
            return new ResponseEntity<>(rendezVousRepository.save(rv),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Unauthorized access", HttpStatus.UNAUTHORIZED);

        }
    }

    @GetMapping("/confirmed-count/{userProId}")
    public ResponseEntity<Integer> getConfirmedAppointmentCountForUserPro(@PathVariable Long userProId) {
        int confirmedCount = rvService.getConfirmedAppointmentCountForUserPro(userProId);
        return ResponseEntity.ok(confirmedCount);
    }
}
