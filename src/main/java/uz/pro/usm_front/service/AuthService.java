package uz.pro.usm_front.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.pro.usm_front.domain.dto.request.user.LoginRequest;
import uz.pro.usm_front.domain.dto.request.user.RegisterRequest;
import uz.pro.usm_front.domain.dto.response.user.RoleResponse;
import uz.pro.usm_front.domain.dto.response.user.UserResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RestTemplate restTemplate;
    private static final String BACKEND_URL = "http://localhost:8080/auth";

    public String login(LoginRequest loginRequest) {
        try {
            return restTemplate.postForObject(BACKEND_URL + "/login", loginRequest, String.class);
        } catch (Exception e) {
            // Xato bo'lsa, null qaytariladi
            return null;
        }
    }

    public UserResponse register(RegisterRequest registerRequest) {
        return restTemplate.postForObject(BACKEND_URL + "/register", registerRequest, UserResponse.class);
    }

    public List<RoleResponse> getAllRoles(String jwtToken) {
        // Tokenni headerga qo'shish
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        // Rollarni olish uchun backend API'ga so'rov yuboring
        ResponseEntity<List<RoleResponse>> response = restTemplate.exchange(
                BACKEND_URL + "/role",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<RoleResponse>>() {}
        );
        return response.getBody();
    }


}
