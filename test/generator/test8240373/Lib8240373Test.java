/*
 * Copyright (c) 2021, Oracle and/or its affiliates. All rights reserved.
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

import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;
import static test.jextract.test8240373.test8240373_h.*;

/*
 * @test id=classes
 * @bug 8240373
 * @summary Jextract assigns type "Void" to enum macros
 * @library ../../lib
 * @run main/othervm JtregJextract -t test.jextract.test8240373 test8240373.h
 * @run testng/othervm --enable-native-access=ALL-UNNAMED Lib8240373Test
 */

/*
 * @test id=sources
 * @bug 8240373
 * @summary Jextract assigns type "Void" to enum macros
 * @library ../../lib
 *
 * @run main/othervm JtregJextractSources -t test.jextract.test8240373 test8240373.h
 * @run testng/othervm --enable-native-access=ALL-UNNAMED Lib8240373Test
 */

public class Lib8240373Test {
    @Test
    public void test() {
        assertTrue(A() == 0);
        assertTrue(B() == 1);
        assertTrue(C() == 2);
        assertTrue(E_INVALID() == -1);
    }
}
