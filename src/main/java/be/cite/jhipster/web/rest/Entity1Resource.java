package be.cite.jhipster.web.rest;

import com.codahale.metrics.annotation.Timed;
import be.cite.jhipster.domain.Entity1;
import be.cite.jhipster.service.Entity1Service;
import be.cite.jhipster.web.rest.util.HeaderUtil;
import be.cite.jhipster.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Entity1.
 */
@RestController
@RequestMapping("/api")
public class Entity1Resource {

    private final Logger log = LoggerFactory.getLogger(Entity1Resource.class);

    private static final String ENTITY_NAME = "entity1";

    private final Entity1Service entity1Service;

    public Entity1Resource(Entity1Service entity1Service) {
        this.entity1Service = entity1Service;
    }

    /**
     * POST  /entity-1-s : Create a new entity1.
     *
     * @param entity1 the entity1 to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entity1, or with status 400 (Bad Request) if the entity1 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/entity-1-s")
    @Timed
    public ResponseEntity<Entity1> createEntity1(@Valid @RequestBody Entity1 entity1) throws URISyntaxException {
        log.debug("REST request to save Entity1 : {}", entity1);
        if (entity1.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new entity1 cannot already have an ID")).body(null);
        }
        Entity1 result = entity1Service.save(entity1);
        return ResponseEntity.created(new URI("/api/entity-1-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /entity-1-s : Updates an existing entity1.
     *
     * @param entity1 the entity1 to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entity1,
     * or with status 400 (Bad Request) if the entity1 is not valid,
     * or with status 500 (Internal Server Error) if the entity1 couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/entity-1-s")
    @Timed
    public ResponseEntity<Entity1> updateEntity1(@Valid @RequestBody Entity1 entity1) throws URISyntaxException {
        log.debug("REST request to update Entity1 : {}", entity1);
        if (entity1.getId() == null) {
            return createEntity1(entity1);
        }
        Entity1 result = entity1Service.save(entity1);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, entity1.getId().toString()))
            .body(result);
    }

    /**
     * GET  /entity-1-s : get all the entity1S.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of entity1S in body
     */
    @GetMapping("/entity-1-s")
    @Timed
    public ResponseEntity<List<Entity1>> getAllEntity1S(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Entity1S");
        Page<Entity1> page = entity1Service.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/entity-1-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /entity-1-s/:id : get the "id" entity1.
     *
     * @param id the id of the entity1 to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entity1, or with status 404 (Not Found)
     */
    @GetMapping("/entity-1-s/{id}")
    @Timed
    public ResponseEntity<Entity1> getEntity1(@PathVariable String id) {
        log.debug("REST request to get Entity1 : {}", id);
        Entity1 entity1 = entity1Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(entity1));
    }

    /**
     * DELETE  /entity-1-s/:id : delete the "id" entity1.
     *
     * @param id the id of the entity1 to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/entity-1-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteEntity1(@PathVariable String id) {
        log.debug("REST request to delete Entity1 : {}", id);
        entity1Service.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
