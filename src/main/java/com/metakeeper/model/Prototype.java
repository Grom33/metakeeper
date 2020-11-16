package com.metakeeper.model;

import com.metakeeper.model.dao.BaseModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;
import java.util.Objects;

/**
 * Prototype entity describe
 *
 * @author Gromov Vitaly.
 * Created 31.10.2020
 */

@Getter
@Setter
@ToString(callSuper = true)
public class Prototype extends BaseModel {
    private String description;
    private Map<String, String> indexes;
    private Map<String, Object> attributes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Prototype)) return false;
        if (!super.equals(o)) return false;
        Prototype prototype = (Prototype) o;
        return Objects.equals(description, prototype.description) &&
                Objects.equals(indexes, prototype.indexes) &&
                Objects.equals(attributes, prototype.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), description, indexes, attributes);
    }
}
