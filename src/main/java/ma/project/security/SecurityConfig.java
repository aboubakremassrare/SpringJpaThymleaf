package ma.project.security;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.*;

@Configurable
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	 @Autowired
	private DataSource datasource;
	@SuppressWarnings("deprecation")
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		/*Definir des utilisateurs par defaults
		 * auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles("USER","ADMIN");
		auth.inMemoryAuthentication().withUser("user").password("{noop}123456").roles("USER");*/
		auth.jdbcAuthentication().dataSource(datasource)
		.usersByUsernameQuery("select login as principal,pass as credentials,active from users where login =?")
		.authoritiesByUsernameQuery("select login as principal,role as role from users_roles where login=?")
		.passwordEncoder(new Md4PasswordEncoder())
		.rolePrefix("ROLE_");
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.formLogin();
		http.authorizeRequests().antMatchers("/user/*").hasRole("USER");
		http.authorizeRequests().antMatchers("/admin/*").hasRole("ADMIN");
		http.exceptionHandling().accessDeniedPage("/403");

	}
	

}
