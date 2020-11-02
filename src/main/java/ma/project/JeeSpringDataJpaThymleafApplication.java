package ma.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import ma.project.dao.ProduitRepository;
import ma.project.entities.Produit;

@SpringBootApplication
public class JeeSpringDataJpaThymleafApplication {

	public static void main(String[] args) {
		
		ApplicationContext context= SpringApplication.run(JeeSpringDataJpaThymleafApplication.class, args);
		
	}

}
