/*
 * Copyright (c) 2020, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package org.openjdk.jextract.test.toolprovider;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Path;

import org.openjdk.jextract.test.TestUtils;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/*
 * @test
 * @library /test/lib
 * @build JextractToolRunner
 * @bug 8240752
 * @summary jextract generates non-compilable code for special floating point values
 * @run testng/othervm --enable-native-access=ALL-UNNAMED Test8240752
 */
public class Test8240752 extends JextractToolRunner {
    private float getFloatConstant(Class<?> cls, String name) {
        Method method = findMethod(cls, name);
        assertNotNull(method);
        assertEquals(method.getReturnType(), float.class);
        try {
            return (float)method.invoke(null);
        } catch (Exception exp) {
            System.err.println(exp);
            assertTrue(false, "should not reach here");
        }
        return 0.0f;
    }

    private double getDoubleConstant(Class<?> cls, String name) {
        Method method = findMethod(cls, name);
        assertNotNull(method);
        assertEquals(method.getReturnType(), double.class);
        try {
            return (double)method.invoke(null);
        } catch (Exception exp) {
            System.err.println(exp);
            assertTrue(false, "should not reach here");
        }
        return 0.0d;
    }

    @Test
    public void testConstants() {
        Path floatConstsOutput = getOutputFilePath("floatconstsgen");
        Path floatConstsH = getInputFilePath("float_constants.h");
        run("-d", floatConstsOutput.toString(), floatConstsH.toString()).checkSuccess();
        try(TestUtils.Loader loader = TestUtils.classLoader(floatConstsOutput)) {
            Class<?> cls = loader.loadClass("float_constants_h");
            assertNotNull(cls);

            double d = getDoubleConstant(cls, "NAN");
            assertTrue(Double.isNaN(d));
            d = getDoubleConstant(cls, "PINFINITY");
            assertTrue(Double.isInfinite(d) && d > 0);
            d = getDoubleConstant(cls, "NINFINITY");
            assertTrue(Double.isInfinite(d) && d < 0);

            float f = getFloatConstant(cls, "NANF");
            assertTrue(Float.isNaN(f));
            f = getFloatConstant(cls, "PINFINITYF");
            assertTrue(Float.isInfinite(f) && f > 0);
            f = getFloatConstant(cls, "NINFINITYF");
            assertTrue(Float.isInfinite(f) && f < 0);
        } finally {
            TestUtils.deleteDir(floatConstsOutput);
        }
    }
}
