package uz.pro.usm_front.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.pro.usm_front.domain.dto.request.user.UserUpdateRequest;
import uz.pro.usm_front.domain.dto.response.user.UserResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RestTemplate restTemplate;
    private static final String BACKEND_URL = "http://localhost:8080/user"; // Backend URL

    public List<UserResponse> getAllUsers(String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<List<UserResponse>> response = restTemplate.exchange(
                BACKEND_URL,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    public UserResponse getUserById(Long id, String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<UserResponse> response = restTemplate.exchange(
                BACKEND_URL + "/" + id,
                HttpMethod.GET,
                request,
                UserResponse.class
        );
        return response.getBody();
    }

    public void updateUser(Long id, UserUpdateRequest userUpdateRequest, String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        HttpEntity<UserUpdateRequest> request = new HttpEntity<>(userUpdateRequest, headers);

        restTemplate.exchange(
                BACKEND_URL + "/" + id,
                HttpMethod.PUT,
                request,
                String.class
        );
    }

    public void deleteUser(Long id, String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        restTemplate.exchange(
                BACKEND_URL + "?id=" + id,
                HttpMethod.DELETE,
                request,
                String.class
        );
    }
}
