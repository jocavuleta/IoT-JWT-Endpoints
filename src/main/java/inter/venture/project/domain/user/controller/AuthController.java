package inter.venture.project.domain.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import inter.venture.project.domain.user.request.JwtRequest;
import inter.venture.project.domain.user.response.JwtResponse;
import inter.venture.project.domain.user.service.JwtUserDetailsService;

@RestController
@CrossOrigin
public class AuthController {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        return ResponseEntity.ok(new JwtResponse(this.jwtUserDetailsService.authenticateAndGetToken(jwtRequest.getUsername(), jwtRequest.getPassword())));
    }
}
