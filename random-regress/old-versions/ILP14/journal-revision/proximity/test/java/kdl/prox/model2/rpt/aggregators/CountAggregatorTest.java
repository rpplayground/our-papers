/**
 * $Id: CountAggregatorTest.java 3658 2007-10-15 16:29:11Z schapira $
 *
 * Part of the open-source Proximity system
 *   (see LICENSE for copyright and license information).
 *
 */

/**
 * $Id: CountAggregatorTest.java 3658 2007-10-15 16:29:11Z schapira $
 */

package kdl.prox.model2.rpt.aggregators;

import junit.framework.TestCase;
import kdl.prox.TestUtil;
import kdl.prox.db.Container;
import kdl.prox.db.DB;
import kdl.prox.model2.common.sources.AttributeSource;
import kdl.prox.model2.common.sources.Source;
import kdl.prox.model2.util.NSTCache;
import org.apache.log4j.Logger;

/**
 * Directly tests the Connection class. Note that it is indirectly tested by
 * many other test classes. For now tests scope behavior.
 */
public class CountAggregatorTest extends TestCase {

    static Logger log = Logger.getLogger(CountAggregatorTest.class);

    Container container;
    private NSTCache cache;

    protected void setUp() throws Exception {
        super.setUp();

        Aggregator.setNumThresholds(2);
        cache = new NSTCache();

        TestUtil.initDBOncePerAllTests();
        TestUtil.openTestConnection();

        container = DB.createNewTempContainer();
        container.getObjectsNST().insertRow("1, 1, A");
        container.getObjectsNST().insertRow("2, 1, B");
        container.getObjectsNST().insertRow("3, 1, B");
        container.getObjectsNST().insertRow("4, 2, A");
        container.getObjectsNST().insertRow("5, 2, B");
        container.getObjectsNST().insertRow("6, 2, B");
        container.getObjectsNST().insertRow("7, 3, A");
        container.getObjectsNST().insertRow("8, 3, B");
        container.getObjectsNST().insertRow("9, 3, B");

        DB.getObjectAttrs().deleteAllAttributes();
        DB.getObjectAttrs().defineAttribute("gender", "str");
        DB.getObjectAttrs().getAttrDataNST("gender").insertRow("2, m");
        DB.getObjectAttrs().getAttrDataNST("gender").insertRow("3, m");
        DB.getObjectAttrs().getAttrDataNST("gender").insertRow("5, f");
        DB.getObjectAttrs().getAttrDataNST("gender").insertRow("6, m");
        DB.getObjectAttrs().getAttrDataNST("gender").insertRow("8, f");
        DB.getObjectAttrs().getAttrDataNST("gender").insertRow("9, f");

        DB.getObjectAttrs().defineAttribute("age", "int");
        DB.getObjectAttrs().getAttrDataNST("age").insertRow("1, 40");
        DB.getObjectAttrs().getAttrDataNST("age").insertRow("4, 45");
        DB.getObjectAttrs().getAttrDataNST("age").insertRow("7, 10");

        DB.getObjectAttrs().defineAttribute("height", "flt");
        DB.getObjectAttrs().getAttrDataNST("height").insertRow("1, 5.5");
        DB.getObjectAttrs().getAttrDataNST("height").insertRow("1, 6.5");
        DB.getObjectAttrs().getAttrDataNST("height").insertRow("4, 5.09");
        DB.getObjectAttrs().getAttrDataNST("height").insertRow("4, 6.09");
        DB.getObjectAttrs().getAttrDataNST("height").insertRow("7, 6.4");
        DB.getObjectAttrs().getAttrDataNST("height").insertRow("7, 7.4");
    }


    protected void tearDown() throws Exception {
        super.tearDown();
        TestUtil.closeTestConnection();
    }


    public void testString() {
        Source source = new AttributeSource("B", "gender").init(container, cache);
        AggregatorTestUtil.verifyValues(cache, new CountAggregator(source).computeTables(cache),
                new String[]{
                        "count([B.gender]=f)>=0",
                        "count([B.gender]=f)>=1",
                        "count([B.gender]=m)>=0",
                        "count([B.gender]=m)>=1"},
                new String[][]{
                        {"1@0.true", "2@0.true", "3@0.true"},
                        {"1@0.false", "2@0.true", "3@0.true"},
                        {"1@0.true", "2@0.true", "3@0.true"},
                        {"1@0.true", "2@0.true", "3@0.false"}});
    }


    public void testInt() {
        Source source = new AttributeSource("A", "age").setIsSingleValue(false).init(container, cache); //override the single-value setting
        AggregatorTestUtil.verifyValues(cache, new CountAggregator(source).computeTables(cache),
                new String[]{
                        "count([A.age]=40)>=0",
                        "count([A.age]=40)>=1",
                        "count([A.age]=45)>=0",
                        "count([A.age]=45)>=1",
                        "count([A.age]=10)>=0",
                        "count([A.age]=10)>=1"},
                new String[][]{
                        {"1@0.true", "2@0.true", "3@0.true"},
                        {"1@0.true", "2@0.false", "3@0.false"},
                        {"1@0.true", "2@0.true", "3@0.true"},
                        {"1@0.false", "2@0.true", "3@0.false"},
                        {"1@0.true", "2@0.true", "3@0.true"},
                        {"1@0.false", "2@0.false", "3@0.true"}});
    }

    public void testFlt() {
        Source source = new AttributeSource("A", "height").init(container, cache);
        AggregatorTestUtil.verifyValues(cache, new CountAggregator(source).computeTables(cache),
                new String[]{
                        "count([A.height]>6.4000001)>=0",
                        "count([A.height]>6.4000001)>=1",
                        "count([A.height]>5.5)>=1",
                        "count([A.height]>5.5)>=2"},
                new String[][]{
                        {"1@0.true", "2@0.true", "3@0.true"},
                        {"1@0.true", "2@0.false", "3@0.true"},
                        {"1@0.true", "2@0.true", "3@0.true"},
                        {"1@0.false", "2@0.false", "3@0.true"}});
    }

}