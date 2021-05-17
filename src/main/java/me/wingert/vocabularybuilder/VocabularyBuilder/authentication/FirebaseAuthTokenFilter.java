package me.wingert.vocabularybuilder.VocabularyBuilder.authentication;

import com.google.api.core.ApiFuture;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import me.wingert.vocabularybuilder.VocabularyBuilder.User;
import me.wingert.vocabularybuilder.VocabularyBuilder.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

// Request filter that validates the access token every time a request is made
// against the API.

@Component
public class FirebaseAuthTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(FirebaseAuthTokenFilter.class);
    private static final String TOKEN_HEADER = "Authorization";

    public FirebaseAuthTokenFilter() {
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("FirebaseAuthTokenFilter: doFilter:: authenticating...");

        logger.info(String.valueOf(request));
        String authToken = request.getHeader(TOKEN_HEADER);

        logger.info("Token: " + authToken);

        if (authToken == null || authToken.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            Authentication authentication = getAndValidateAuthentication(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info(("doFilter():: successfully authenticated"));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            logger.info("Failed to authenticate", e);
        }

        filterChain.doFilter(request, response);
    }

    private Authentication getAndValidateAuthentication(String authToken) throws Exception {
        FirebaseToken firebaseToken = authenticateFirebaseToken(authToken);
        Authentication authentication = new UsernamePasswordAuthenticationToken(firebaseToken, authToken, new ArrayList<>());
//        updateUser(firebaseToken);
        return authentication;
    }

    private FirebaseToken authenticateFirebaseToken(String authToken) throws Exception {
        ApiFuture<FirebaseToken> app = FirebaseAuth.getInstance().verifyIdTokenAsync(authToken);
        return app.get();
    }

//    private void updateUser(FirebaseToken token) {
//        logger.info("Updating user with email " + token.getEmail());
//        User user = userRepository.findByEmail(token.getEmail());
//        if (user == null) {
//            logger.info("Creating new user");
//            user = new User(
//                    token.getName(),
//                    token.getEmail(),
//                    token.isEmailVerified(),
//                    token.getUid(),
//                    null,
//                    null,
//                    null
//                    );
//            userRepository.save(user);
//            logger.info("Saved new user " + user);
//        }
//        else {
//            Timestamp ts = new Timestamp(System.currentTimeMillis());
//            user.setLastSignInDateTime(ts.toString());
//            userRepository.save(user);
//            logger.info("Updated last sign in timestamp for " + user.getDisplayName() + " to " + ts.toString());
//        }
//    }

}
