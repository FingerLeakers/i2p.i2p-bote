/**
 * Copyright (C) 2009  HungryHobo@mail.i2p
 * 
 * The GPG fingerprint for HungryHobo@mail.i2p is:
 * 6DD3 EAA2 9990 29BC 4AD2 7486 1E2C 7B61 76DC DC12
 * 
 * This file is part of I2P-Bote.
 * I2P-Bote is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * I2P-Bote is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with I2P-Bote.  If not, see <http://www.gnu.org/licenses/>.
 */

package i2p.bote;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import i2p.bote.email.Email;
import i2p.bote.fileencryption.PasswordCache;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.mail.MessagingException;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;

public class TestUtil {
    
    public static void assertEquals(String message, Email email1, Email email2) throws IOException, MessagingException {
        assertTrue(message, equals(email1, email2));
    }
    
    public static void assertUnequal(String message, Email email1, Email email2) throws IOException, MessagingException {
        assertFalse(message, equals(email1, email2));
    }
    
    private static boolean equals(Email email1, Email email2) throws IOException, MessagingException {
        if (email1==null || email2==null)
            return false;
        
        ByteArrayOutputStream byteStream1 = new ByteArrayOutputStream();
        email1.writeTo(byteStream1);
        
        ByteArrayOutputStream byteStream2 = new ByteArrayOutputStream();
        email2.writeTo(byteStream2);
        
        return Arrays.equals(byteStream1.toByteArray(), byteStream2.toByteArray());
    }
    
    /**
     * Creates a {@link PasswordCache} with a mock {@link Configuration}.
     * @param testDir
     * @return
     */
    public static PasswordCache createPasswordCache(File testDir) {
        final Configuration configuration = createConfiguration(testDir);
        
        return new PasswordCache(configuration);
    }
    
    /**
     * Creates a mock {@link Configuration} whose getKeyDerivationParametersFile() and
     * getPasswordFile() methods must be called at least once each.
     * The <code>getPasswordCacheDuration</code> method returns 1 (one minute).
     * @param testDir
     */
    public static Configuration createConfiguration(File testDir) {
        Mockery mockery = new Mockery() {{
            setImposteriser(ClassImposteriser.INSTANCE);
        }};
        final Configuration configuration = mockery.mock(Configuration.class);
        
        final File derivParametersFile = new File(testDir, "derivparams");
        mockery.checking(new Expectations() {{
            atLeast(1).of(configuration).getKeyDerivationParametersFile(); will(returnValue(derivParametersFile));
        }});
        
        final File passwordFile = new File(testDir, "password");
        mockery.checking(new Expectations() {{
            atLeast(1).of(configuration).getPasswordFile(); will(returnValue(passwordFile));
        }});
        
        mockery.checking(new Expectations() {{
            allowing(configuration).getPasswordCacheDuration(); will(returnValue(1));
        }});
        
        return configuration;
    }
}