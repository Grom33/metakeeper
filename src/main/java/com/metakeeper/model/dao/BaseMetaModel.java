package com.metakeeper.model.dao;

import lombok.*;

import java.util.Objects;

/**
 * Description
 *
 * @author Gromov Vitaly.
 * Created 05.11.2020
 */
@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class BaseMetaModel extends BaseModel {
    private long code;
    private int version;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseMetaModel)) return false;
        if (!super.equals(o)) return false;
        BaseMetaModel that = (BaseMetaModel) o;
        return code == that.code &&
                version == that.version;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), code, version);
    }
}
