package be.cite.jhipster.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import be.cite.jhipster.domain.enumeration.ReleaseType;

/**
 * A Entity1.
 */

@Document(collection = "entity1")
public class Entity1 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(min = 5, max = 20)
    @Field("name")
    private String name;

    @Field("size")
    private Long size;

    @Field("release_date")
    private ZonedDateTime releaseDate;

    @NotNull
    @Field("type")
    private ReleaseType type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Entity1 name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public Entity1 size(Long size) {
        this.size = size;
        return this;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public ZonedDateTime getReleaseDate() {
        return releaseDate;
    }

    public Entity1 releaseDate(ZonedDateTime releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public void setReleaseDate(ZonedDateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    public ReleaseType getType() {
        return type;
    }

    public Entity1 type(ReleaseType type) {
        this.type = type;
        return this;
    }

    public void setType(ReleaseType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Entity1 entity1 = (Entity1) o;
        if (entity1.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entity1.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Entity1{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", size='" + getSize() + "'" +
            ", releaseDate='" + getReleaseDate() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
