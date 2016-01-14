package api.chat.nihilent.repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import api.chat.nihilent.model.Registration;


/*
 *post request with  http://localhost:8080/registration
 * header content type : "application/json"
 * 
 * body content : {"firstname": "chana4","lastname": "aw4","username":"chana4.aw4"}
 * 
 */

@RepositoryRestResource(collectionResourceRel = "registration", path = "registration")
public interface RegistrationRepository extends MongoRepository<Registration, String> {

	List<Registration> findByFirstName(@Param("firstname") String firstname);
	
	List<Registration> findByLastName(@Param("lastname") String lastname);
	
	List<Registration> findByUserName(@Param("username") String username);
}

