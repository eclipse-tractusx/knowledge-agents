package org.eclipse.tractusx.agents.remoting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class MergeObjectNodesTest {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Test
    public void testMerge() throws Exception {
        
        // Load input resources
        JsonNode input1 = loadJsonResource("MergeObjectNodesIn1.json");
        JsonNode input2 = loadJsonResource("MergeObjectNodesIn2.json");

        // Load expected result resource
        JsonNode expectedResult = loadJsonResource("MergeObjectNodesResult.json");

        // Invoke merge method with the input resources
        input1 = Invocation.mergeObjectNodes((ObjectNode)input1, (ObjectNode)input2);

        // Assert the result against the expected result
        assertEquals(expectedResult, input1);
    }
    
    private JsonNode loadJsonResource(String resourceName) throws IOException {
        // Use the class loader to load the resource
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(resourceName)) {

            // Parse JSON content into ObjectNode
            return objectMapper.readTree(inputStream);
        }
    }

}
