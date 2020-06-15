package inter.venture.project.domain.user.response;


public class JwtResponse {

    //Output token if the validation went accordingly
    private final String jwtToken;

    public JwtResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getToken() {
        return this.jwtToken;
    }

}
