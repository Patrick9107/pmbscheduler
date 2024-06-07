package at.spengergasse.pmbscheduler.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

//    private JwtAuthEntryPoint authEntryPoint;

    private CustomUserDetailsService userDetailsService;

    @Autowired
    public SecurityConfiguration(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
//        this.authEntryPoint = authEntryPoint;
    }

//    @Bean
//    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests(auth ->
//                auth.requestMatchers(new AntPathRequestMatcher("/auth/login", "POST"),
//                        new AntPathRequestMatcher("/auth/register", "POST")).anonymous())
//                .httpBasic(Customizer.withDefaults());
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz.anyRequest().permitAll())
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);
//                .authorizeHttpRequests(authz -> authz.requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
//                .httpBasic(Customizer.withDefaults())
////                .authorizeHttpRequests(request -> request.requestMatchers(new AntPathRequestMatcher("/create-event")) // create-event for everyone
////                        .permitAll())
//                .authorizeHttpRequests(request -> request.requestMatchers(new AntPathRequestMatcher("/login"), new AntPathRequestMatcher("/register"))
//                        .anonymous());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}