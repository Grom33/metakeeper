package com.metakeeper;

import com.metakeeper.model.Prototype;

import java.util.HashMap;
import java.util.Map;

/**
 * Description
 *
 * @author Gromov Vitaly.
 * Created 01.11.2020
 */
public class TestDataGenerator {
    public static Prototype getPrototypeEntity() {
        Prototype prototype = new Prototype();
        prototype.setName("Test prototype");
        prototype.setCreated(System.currentTimeMillis());
        prototype.setModified(System.currentTimeMillis());
        prototype.setHidden(false);
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("Prop1", "Some properties");
        prototype.setAttributes(attributes);
        return prototype;
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
