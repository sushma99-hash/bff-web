package cmu.edu.ds.filters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Component
public class JwtValidationFilter extends OncePerRequestFilter {
    private static final List<String> VALID_SUBJECTS = Arrays.asList("starlord", "gamora", "drax", "rocket", "groot");
    private static final String VALID_ISSUER = "cmu.edu";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Skip validation for /status endpoint
        if (request.getRequestURI().equals("/status")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Check Client Type header
        String clientType = request.getHeader("X-Client-Type");
        if (clientType == null) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Missing required header: X-Client-Type\"}");
            return;
        }

        if(!clientType.equals("web")){
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Missing required header: X-Client-Type\"}");
            return;
        }

        // Check Authorization header
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Missing or invalid Authorization header\"}");
            return;
        }

        // Validate JWT token
        String token = authHeader.substring(7); // Remove "Bearer " prefix
        try {
            validateJwtToken(token);
        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void validateJwtToken(String token) throws Exception {
        try {
            // Decode JWT token
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                throw new Exception("Invalid token format");
            }

            // Decode payload (second part)
            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
            JsonNode payloadJson = objectMapper.readTree(payload);

            // Validate token claims
            if (!payloadJson.has("sub") || !VALID_SUBJECTS.contains(payloadJson.get("sub").asText())) {
                throw new Exception("Invalid subject in token");
            }

            if (!payloadJson.has("iss") || !VALID_ISSUER.equals(payloadJson.get("iss").asText())) {
                throw new Exception("Invalid issuer in token");
            }

//                if (!payloadJson.has("exp") || payloadJson.get("exp").asLong() < (System.currentTimeMillis() / 1000)) {
//                    throw new Exception("Token expired");
//                }

        } catch (Exception e) {
            throw new Exception("Invalid JWT token: " + e.getMessage());
        }
    }
}


