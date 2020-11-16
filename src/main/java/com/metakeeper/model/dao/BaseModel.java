package com.metakeeper.model.dao;

import com.arangodb.entity.BaseDocument;
import lombok.*;

import java.util.Objects;

/**
 * Description
 *
 * @author Gromov Vitaly.
 * Created 02.11.2020
 */
@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class BaseModel extends BaseDocument implements Identifiable {
    private long created;
    private long modified;
    private boolean hidden;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseModel)) return false;
        if (!super.equals(o)) return false;
        BaseModel baseModel = (BaseModel) o;
        return created == baseModel.created &&
                modified == baseModel.modified &&
                hidden == baseModel.hidden &&
                Objects.equals(name, baseModel.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), created, modified, hidden, name);
    }
}
