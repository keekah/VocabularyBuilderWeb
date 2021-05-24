package me.wingert.vocabularybuilder.VocabularyBuilder.authentication;

import com.google.api.core.ApiFuture;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.ArrayList;

// Request filter that validates the access token every time a request is made to the API.

public class FirebaseAuthTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(FirebaseAuthTokenFilter.class);
    private static final String TOKEN_HEADER = "Authorization";
    private static final String FIREBASE_ID = "FirebaseId";

    private String firebaseId;      // Firebase ID of the user making the request.

    public FirebaseAuthTokenFilter() { }

    // The Firebase Token is passed from the app via an HTTP Authorization Header.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("FirebaseAuthTokenFilter :: doFilterInternal() :: Authenticating... URL = " + request.getRequestURL());

        String authToken = request.getHeader(TOKEN_HEADER);

        // If null or empty, server returns 403 Forbidden.
        if (authToken == null || authToken.isEmpty()) {
            logger.info("FirebaseAuthTokenFilter :: doFilerInternal() :: authToken null or empty.");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            Authentication authentication = getAndValidateAuthentication(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            request.setAttribute(FIREBASE_ID, firebaseId);
            logger.info(("FirebaseAuthTokenFilter :: doFilterInternal() :: Successfully authenticated."));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            logger.info("FirebaseAuthTokenFilter :: doFilterInternal() :: Failed to authenticate.", e);
        }

        filterChain.doFilter(request, response);
        logger.info("FirebaseAuthTokenFilter :: doFilterInternal() :: Method complete.");
    }

    private Authentication getAndValidateAuthentication(String authToken) throws Exception {
        logger.info("FirebaseAuthTokenFilter :: getAndValidateAuthentication() :: Validating...");
        FirebaseToken firebaseToken = authenticateFirebaseToken(authToken);
        Authentication authentication = new UsernamePasswordAuthenticationToken(firebaseToken, authToken, new ArrayList<>());
        firebaseId = firebaseToken.getUid();
        logger.info("FirebaseAuthTokenFilter :: getAndValidateAuthentication() :: Method complete.");
        return authentication;
    }

    private FirebaseToken authenticateFirebaseToken(String authToken) throws Exception {
        logger.info("FirebaseAuthTokenFilter :: authenticateFirebaseToken() :: Verifying Id Token Async...");
        ApiFuture<FirebaseToken> app = FirebaseAuth.getInstance().verifyIdTokenAsync(authToken);
        logger.info("FirebaseAuthTokenFilter :: authenticateFirebaseToken() :: Method complete.");
        return app.get();
    }

}
