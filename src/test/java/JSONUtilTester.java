import org.junit.Test;
import util.JSONUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JSONUtilTester {

    public static class Holder {
        public Object payload;
    }

    @Test public void roundTripsConcreteType()
    {
        MyObject in = new MyObject("Hello", "World");
        String json = JSONUtil.toJSON(in);
        assertFalse("no type ids should be written", json.contains("MyObject"));
        assertEquals(in, JSONUtil.fromJSON(json, MyObject.class));
    }

    @Test public void embeddedTypeIdDoesNotSelectClass()
    {
        // With default typing enabled this would instantiate java.net.URL from attacker-chosen type id.
        Holder h = (Holder) JSONUtil.fromJSON("{\"payload\":[\"java.net.URL\",\"http://example.invalid/\"]}", Holder.class);
        assertTrue(h.payload instanceof java.util.List);
    }
}
