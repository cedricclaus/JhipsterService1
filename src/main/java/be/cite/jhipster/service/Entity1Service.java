package be.cite.jhipster.service;

import be.cite.jhipster.domain.Entity1;
import be.cite.jhipster.repository.Entity1Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing Entity1.
 */
@Service
public class Entity1Service {

    private final Logger log = LoggerFactory.getLogger(Entity1Service.class);

    private final Entity1Repository entity1Repository;

    public Entity1Service(Entity1Repository entity1Repository) {
        this.entity1Repository = entity1Repository;
    }

    /**
     * Save a entity1.
     *
     * @param entity1 the entity to save
     * @return the persisted entity
     */
    public Entity1 save(Entity1 entity1) {
        log.debug("Request to save Entity1 : {}", entity1);
        return entity1Repository.save(entity1);
    }

    /**
     *  Get all the entity1S.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<Entity1> findAll(Pageable pageable) {
        log.debug("Request to get all Entity1S");
        return entity1Repository.findAll(pageable);
    }

    /**
     *  Get one entity1 by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public Entity1 findOne(String id) {
        log.debug("Request to get Entity1 : {}", id);
        return entity1Repository.findOne(id);
    }

    /**
     *  Delete the  entity1 by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Entity1 : {}", id);
        entity1Repository.delete(id);
    }
}
