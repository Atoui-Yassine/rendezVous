package com.example.rendezVous.controllers;

import com.example.rendezVous.DTOs.request.LoginForm;
import com.example.rendezVous.DTOs.request.RefreshTokenDto;
import com.example.rendezVous.DTOs.request.SignUpForm;
import com.example.rendezVous.DTOs.response.JwtRefreshResponse;
import com.example.rendezVous.DTOs.response.JwtResponse;
import com.example.rendezVous.DTOs.response.MessageResponse;
import com.example.rendezVous.models.userModel.ERole;
import com.example.rendezVous.models.userModel.Role;
import com.example.rendezVous.models.userModel.UserModel;
import com.example.rendezVous.security.serviceUser.UserDetailServiceImp;
import com.example.rendezVous.security.serviceUser.UserDetailsImpl;
import com.example.rendezVous.security.serviceUser.jwt.JwtUtils;
import com.example.rendezVous.services.userServices.RoleService;
import com.example.rendezVous.services.userServices.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;

    @Value("${jwtSecret}")
    private String jwtSecret;

    @Value("${jwtExpirationMs}")
    private int jwtExpirationMs;

    @Autowired private UserDetailServiceImp userDetailService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginForm) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginForm.getEmail(), loginForm.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        String refreshJwt = jwtUtils.generateRefreshJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(JwtResponse.builder()
                .token(jwt).refreshToken(refreshJwt).roles(roles).build());


    }

    @PostMapping("/signup")
    public ResponseEntity addUser(@Valid @RequestBody SignUpForm signUpForm) {
        if (userService.existsByEmail(signUpForm.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email  is already taken!"));
        }
        UserModel user = new UserModel(signUpForm.getLastname(),
                signUpForm.getFirstname(),
                signUpForm.getEmail(),
                encoder.encode(signUpForm.getPassword()));
        Set<String> strRoles = signUpForm.getRoles();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleService.findbyNameOrCreate(ERole.ROLE_USER);
//                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleService.findbyNameOrCreate(ERole.ROLE_ADMIN);
                        //  .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "pro":
                        Role rolePro = roleService.findbyNameOrCreate(ERole.ROLE_PRO);
                        //  .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(rolePro);

                        break;
                    default:
                        Role userRole = roleService.findbyNameOrCreate(ERole.ROLE_USER);
                        // .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });

        }
        user.setRoles(roles);
        userService.saveUser(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));

    }
    @PostMapping("/refreshtoken") // Todo:RefreshToken
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenDto refreshToken){
        if(jwtUtils.validateJwtToken(refreshToken.getRefreshToken())==false)
            return new ResponseEntity<>(new MessageResponse("refreshToken not valid"), HttpStatus.BAD_REQUEST);


        UserDetailsImpl userPrincipal = (UserDetailsImpl) userDetailService.loadUserByUsername(jwtUtils.getUserNameFromJwtToken(refreshToken.getRefreshToken()));
        String token = Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .claim("roles",userPrincipal.getAuthorities().stream()
                        .map(item -> item.getAuthority())
                        .collect(Collectors.toList()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();


        return new ResponseEntity<>(new JwtRefreshResponse(token),HttpStatus.OK);
    }
}
