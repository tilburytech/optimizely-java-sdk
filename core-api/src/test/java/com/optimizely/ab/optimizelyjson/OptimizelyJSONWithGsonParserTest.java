/**
 *
 *    Copyright 2020, Optimizely and contributors
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.optimizely.ab.optimizelyjson;

import com.optimizely.ab.config.parser.ConfigParser;
import com.optimizely.ab.config.parser.GsonConfigParser;
import com.optimizely.ab.config.parser.JsonParseException;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests for GSON parser only
 */
public class OptimizelyJSONWithGsonParserTest extends OptimizelyJSONTest {
    @Override
    protected ConfigParser getParser() {
        return new GsonConfigParser();
    }

    @Test
    public void testGetValueWithNotMatchingType() throws JsonParseException {
        OptimizelyJSON oj1 = new OptimizelyJSON(orgJson, getParser());

        // GSON returns non-null object but variable is null (while Jackson returns null object)

        TestTypes.NotMatchingType md = oj1.getValue(null, TestTypes.NotMatchingType.class);
        assertNull(md.x99);
    }

    // Tests for integer/double processing

    @Test
    public void testIntegerProcessing() throws JsonParseException {

        // GSON parser toMap() adds ".0" to all integers

        String json = "{\"k1\":1,\"k2\":2.5,\"k3\":{\"kk1\":3,\"kk2\":4.0}}";

        Map<String,Object> m2 = new HashMap<String,Object>();
        m2.put("kk1", 3.0);
        m2.put("kk2", 4.0);

        Map<String,Object> m1 = new HashMap<String,Object>();
        m1.put("k1", 1.0);
        m1.put("k2", 2.5);
        m1.put("k3", m2);

        OptimizelyJSON oj1 = new OptimizelyJSON(json, getParser());
        assertEquals(oj1.toMap(), m1);
    }

    @Test
    public void testIntegerProcessing2() throws JsonParseException {

        // GSON parser toString() keeps ".0" in double

        String json = "{\"k1\":1,\"k2\":2.5,\"k3\":{\"kk1\":3,\"kk2\":4.0}}";

        Map<String,Object> m2 = new HashMap<String,Object>();
        m2.put("kk1", 3);
        m2.put("kk2", 4.0);

        Map<String,Object> m1 = new HashMap<String,Object>();
        m1.put("k1", 1);
        m1.put("k2", 2.5);
        m1.put("k3", m2);

        OptimizelyJSON oj1 = new OptimizelyJSON(m1, getParser());
        assertEquals(compact(oj1.toString()), compact(json));
    }

    @Test
    public void testIntegerProcessing3() throws JsonParseException {
        String json = "{\"k1\":1,\"k2\":2.5,\"k3\":{\"kk1\":3,\"kk2\":4.0}}";

        OptimizelyJSON oj1 = new OptimizelyJSON(json, getParser());
        TestTypes.MDN1 obj = oj1.getValue(null, TestTypes.MDN1.class);

        assertEquals(obj.k1, 1);
        assertEquals(obj.k2, 2.5, 0.01);
        assertEquals(obj.k3.kk1, 3);
        assertEquals(obj.k3.kk2, 4.0, 0.01);
    }

}
