package linkedin.profileservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {

	
	//@Autowired
	//private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
               /* // komunikacija izmedju klijenta i servera je stateless
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                // za neautorizovane zahteve posalji 401 gresku
                // svim korisnicima dopusti da pristupe putanjama / i /api/foo
                http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST, "/auth/login").permitAll()/*.antMatchers("/api/").permitAll()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                //.cors().and()
                ;*/
      /*  http.cors()
        .and().authorizeRequests()
        .antMatchers(HttpMethod.POST, "/auth/login").permitAll()
        .and()*/.csrf().disable();
		// Add a filter to validate the tokens with every request
		//http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }
}