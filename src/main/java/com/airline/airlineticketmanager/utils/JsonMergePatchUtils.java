package com.airline.airlineticketmanager.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;

public class JsonMergePatchUtils {

    public static <T> T mergePatch(JsonMergePatch mergePatch, T targetBean, Class<T> beanClass) throws JsonPatchException {
        ObjectMapper mapper = new ObjectMapper();
        // Convert the Java bean to a JSON document
        JsonNode target = mapper.convertValue(targetBean, JsonNode.class);
        // Apply the JSON Merge Patch to the JSON document
        JsonNode patched = mergePatch.apply(target);
        // Convert the JSON document to a Java bean and return it
        return mapper.convertValue(patched, beanClass);
    }
}
