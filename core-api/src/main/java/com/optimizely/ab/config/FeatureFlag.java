/**
 *
 *    Copyright 2017, 2019, Optimizely and contributors
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
package com.optimizely.ab.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * Represents a FeatureFlag definition at the project level
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeatureFlag implements IdKeyMapped {

    private final String id;
    private final String key;
    private final String rolloutId;
    private final List<String> experimentIds;
    private final List<FeatureVariable> variables;
    private final Map<String, FeatureVariable> variableKeyToFeatureVariableMap;

    @JsonCreator
    public FeatureFlag(@JsonProperty("id") String id,
                       @JsonProperty("key") String key,
                       @JsonProperty("rolloutId") String rolloutId,
                       @JsonProperty("experimentIds") List<String> experimentIds,
                       @JsonProperty("variables") List<FeatureVariable> variables) {
        this.id = id;
        this.key = key;
        this.rolloutId = rolloutId;
        this.experimentIds = experimentIds;
        this.variables = variables;
        this.variableKeyToFeatureVariableMap = ProjectConfigUtils.generateNameMapping(variables);
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getRolloutId() {
        return rolloutId;
    }

    public List<String> getExperimentIds() {
        return experimentIds;
    }

    public List<FeatureVariable> getVariables() {
        return variables;
    }

    public Map<String, FeatureVariable> getVariableKeyToFeatureVariableMap() {
        return variableKeyToFeatureVariableMap;
    }

    @Override
    public String toString() {
        return "FeatureFlag{" +
            "id='" + id + '\'' +
            ", key='" + key + '\'' +
            ", rolloutId='" + rolloutId + '\'' +
            ", experimentIds=" + experimentIds +
            ", variables=" + variables +
            ", variableKeyToFeatureVariableMap=" + variableKeyToFeatureVariableMap +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeatureFlag that = (FeatureFlag) o;

        if (!id.equals(that.id)) return false;
        if (!key.equals(that.key)) return false;
        if (!rolloutId.equals(that.rolloutId)) return false;
        if (!experimentIds.equals(that.experimentIds)) return false;
        if (!variables.equals(that.variables)) return false;
        return variableKeyToFeatureVariableMap.equals(that.variableKeyToFeatureVariableMap);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + key.hashCode();
        result = 31 * result + rolloutId.hashCode();
        result = 31 * result + experimentIds.hashCode();
        result = 31 * result + variables.hashCode();
        result = 31 * result + variableKeyToFeatureVariableMap.hashCode();
        return result;
    }
}
