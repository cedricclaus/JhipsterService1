package be.cite.jhipster.repository;

import be.cite.jhipster.domain.Entity1;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Entity1 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Entity1Repository extends MongoRepository<Entity1,String> {

}
