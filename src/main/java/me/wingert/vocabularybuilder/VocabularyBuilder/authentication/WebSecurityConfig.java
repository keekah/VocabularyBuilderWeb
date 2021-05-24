package me.wingert.vocabularybuilder.VocabularyBuilder.authentication;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
//
//    @Bean
//    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
//        logger.info("WebSecurityConfig securityEvaluationContextExtension()");
//        return new SecurityEvaluationContextExtension();
//    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        logger.info("WebSecurityConfig configure()");
        httpSecurity
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.addFilterBefore(firebaseAuthFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }

    public FirebaseAuthTokenFilter firebaseAuthFilterBean() {
        logger.info("firebaseAuthFilterBean() :: creating instance of FirebaseAuthFilter");
        return new FirebaseAuthTokenFilter();
    }
}
