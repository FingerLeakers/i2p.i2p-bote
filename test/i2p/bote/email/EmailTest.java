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

package i2p.bote.email;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import i2p.bote.packet.I2PBotePacket;
import i2p.bote.packet.dht.UnencryptedEmailPacket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.GeneralSecurityException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;

import org.junit.Before;
import org.junit.Test;

public class EmailTest {
    private Map<Email, EmailIdentity> identities;
    private Email[] emails;
    private String bccEmailDestination;
    private EmailIdentity bccIdentity;

    @Before
    public void setUp() throws Exception {
        identities = new HashMap<Email, EmailIdentity>();
        emails = new Email[4];
        EmailIdentity identity;
        
        identity = new EmailIdentity("uDl~luJ5RMhTJHIpYIiZUoxyE6xQHcOELDq9yaXELLYnODd88DBClohCfoIKDKOJL4fMBb34mLN42K8ptisCkLiVWliAZl4jiFtaXUGbXtNnZtkCYheelbL5mFyvcuGmmri-smOMZ-ROcio3V18VwQZfBeV-4-LHWpLaa8tqc1B0KVwCr0PVnwihcibut10VdfELhbhLYfI32fHQFTG6hCCZzhhe1jt8Ixl-aTAj2vXaPyJrfWn3M~Md1XsBAuFAQ5EHh0niJgF~CHn~gsRROpVvVZRDL9OAfAGAomnZMFEixnFW6B3Dce-uCTKFP5Jck3n1gP7cgRfVcXsRd4WCvWmzDFlmNMA~fIfscbTseSSke0AzA05sGskqQxNlnLyPFaXSX5OE4szfYz55onKRbgQFIJ-Pru9C8Ejvd7WGocmF6Lz6mtxhnzEl8-~wutAga94XQuRTlGr1kDSsve1hdGMyQ8UrUS~wP3Ke9JjLI7feg~uUI-bB6YvaVsOuVEHCAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACQ2~7HQlbJ7oT-fpSot9DUclsw0LGra2Fk86phRsyGriq5uCv1GLGawFTkFvtBkOP");
        identity.setPublicName("Max Mustermann");
        emails[0] = new Email(true);
        emails[0].setSender(new InternetAddress(identity.getPublicName() + " <" + identity.getKey() + ">"));
        emails[0].addRecipient(RecipientType.TO, new InternetAddress("Erika Mustermann <uDl~luJ5RMhTJHIpYIiZUoxyE6xQHcOELDq9yaXELLYnODd88DBClohCfoIKDKOJL4fMBb34mLN42K8ptisCkLiVWliAZl4jiFtaXUGbXtNnZtkCYheelbL5mFyvcuGmmri-smOMZ-ROcio3V18VwQZfBeV-4-LHWpLaa8tqc1B0KVwCr0PVnwihcibut10VdfELhbhLYfI32fHQFTG6hCCZzhhe1jt8Ixl-aTAj2vXaPyJrfWn3M~Md1XsBAuFAQ5EHh0niJgF~CHn~gsRROpVvVZRDL9OAfAGAomnZMFEixnFW6B3Dce-uCTKFP5Jck3n1gP7cgRfVcXsRd4WCvWmzDFlmNMA~fIfscbTseSSke0AzA05sGskqQxNlnLyPFaXSX5OE4szfYz55onKRbgQFIJ-Pru9C8Ejvd7WGocmF6Lz6mtxhnzEl8-~wutAga94XQuRTlGr1kDSsve1hdGMyQ8UrUS~wP3Ke9JjLI7feg~uUI-bB6YvaVsOuVEHC>"));
        emails[0].setSubject("Test", "UTF-8");
        identities.put(emails[0], identity);
        
        identity = new EmailIdentity("m-5~1dZ0MrGdyAWu-C2ecNAB5LCCsHQpeSfjn-r~mqMfNvroR98~BRmReUDmb0la-r-pBHLMtflrJE7aTrGwDTBm5~AJFEm-9SJPZnyGs-ed5pOj4Db65yJml1y1n77qr1~mM4GITl6KuIoxg8YwvPrCIlXe2hiiDCoC-uY9-np9UYYujtjOOwCqXPH9PIbcZeFRkegbOxw5G6I7M4-TZBFbxYDtaew6HX9hnQEGWHkaapq2kTTB3Hmv0Uyo64jvcfMmSRcPng3J1Ho5mHgnzsH0qxQemnBcw7Lfc9fU8xRz858uyiQ8J8XH3T8S7k2~8L7awSgaT7uHQgpV~Rs0p1ofJ70g");
        identity.setPublicName("Max Mustermann");
        emails[1] = new Email(true);
        emails[1].setSender(new InternetAddress(identity.getPublicName() + " <" + identity.getKey() + ">"));
        emails[1].addRecipient(RecipientType.TO, new InternetAddress("Erika Mustermann <m-5~1dZ0MrGdyAWu-C2ecNAB5LCCsHQpeSfjn-r~mqMfNvroR98~BRmReUDmb0la-r-pBHLMtflrJE7aTrGwDTBm5~AJFEm-9SJPZnyGs-ed5pOj4Db65yJml1y1n77qr1~mM4GITl6KuIoxg8YwvPrCIlXe2hiiDCoC-uY9-np9UY>"));
        emails[1].setSubject("Test", "UTF-8");
        identities.put(emails[1], identity);
        
        identity = new EmailIdentity("p2Njb7qvettUyfW3-GdMasoi9PIZbszjC-t2y55yFsP-ib4p2wXUlxL2KTofdcQkhOpR24nnZATfol-QQVKS9AOjggfWIkXQcf6dWraE~YIiNyQJ3sa~MixqKixRsSfpsLuFotDPBShvuSwKTIx7~9k0cfbJn98y1HV7VAEz8xhS");
        identity.setPublicName("Lieschen Müller");
        emails[2] = new Email(true);
        emails[2].setSender(new InternetAddress(identity.getPublicName() + " <" + identity.getKey() + ">"));
        emails[2].addRecipient(RecipientType.TO, new InternetAddress("E. Kishon <ix4fWGXnWUJRWtTsbFUiNks8BPAsBzEnVuzFErMNikjhtfdkBIiF-XdhmnnHxKRVGTCzGoo8dqoeI2hfLCZxds>"));
        emails[2].addRecipient(RecipientType.CC, new InternetAddress("R. Sheckley <uXh8-RSBNBVHbLd8ZznhcRcklM2KUHZxYwpEDvBQeyXne0tEiW8IdrT8IA27IjPRSV1k8c4BX9VB0sE7oA0sO3>"));
        emails[2].addRecipient(RecipientType.CC, new InternetAddress("M. Chrichton <wCJ3MVCHIusW8zMDgCPDMtz3ltLj9MhbkMzwghIJ9lgyIYOnpyJRUeF8q1elT9DAWy893EmKIwWSlyJu5LgG10>"));
        bccEmailDestination = "lnd40Y0WI2JXWs69EQXLLxpKGrVF-c0xyZiOnAalIhxuHrnvdFqjH-vKWUg6wVooT1etxPCZFzRkWDG8tYMU7O";
        emails[2].addRecipient(RecipientType.BCC, new InternetAddress("D. Adams <lnd40Y0WI2JXWs69EQXLLxpKGrVF-c0xyZiOnAalIhxuHrnvdFqjH-vKWUg6wVooT1etxPCZFzRkWDG8tYMU7O>"));
        emails[2].addRecipient(RecipientType.BCC, new InternetAddress("A. Kostner <1-oKfnRkUpgHDAEZww9P~Qmjj7eVkNZ8eOUaYeyiBi9mZrw4CxClXpl2h7iMxRC9kalm9fiiUA2fO6O06A6gXv>"));
        emails[2].addRecipient(RecipientType.BCC, new InternetAddress("P. Bellemare <n54FM0ASKMQZMB0UZq05EV0TOma6jWTuYA5pyhhvDCxyEBRwsWO5sZQGlcyrjHbB4JxXwjSKhvzRn2ctE79jPA>"));
        emails[2].setSubject("Test", "UTF-8");
        identities.put(emails[2], identity);
        bccIdentity = new EmailIdentity("lnd40Y0WI2JXWs69EQXLLxpKGrVF-c0xyZiOnAalIhxuHrnvdFqjH-vKWUg6wVooT1etxPCZFzRkWDG8tYMU7OM7vU0XG79~OcRzXlHsMgfpID4HN71-eOReQm8g0lNGgEJ-OPGDNCT1ti5Zs7bge6hu033Je-ihI2OLCc6WXgK8");
        
        // create an anonymous email, don't map it to an identity
        emails[3] = new Email(true);
        emails[3].setSender(new InternetAddress("anonymous"));
        emails[3].addRecipient(RecipientType.TO, new InternetAddress("Erika Mustermann <m-5~1dZ0MrGdyAWu-C2ecNAB5LCCsHQpeSfjn-r~mqMfNvroR98~BRmReUDmb0la-r-pBHLMtflrJE7aTrGwDTBm5~AJFEm-9SJPZnyGs-ed5pOj4Db65yJml1y1n77qr1~mM4GITl6KuIoxg8YwvPrCIlXe2hiiDCoC-uY9-np9UY>"));
        emails[3].setSubject("Test", "UTF-8");
        
        for (Email email: emails) {
            // Make a large email so it is a good test case for testCreateEmailPackets().
            // Use random data (more or less, because it has to be US ASCII chars)
            // so it doesn't get compressed into 1 packet.
            Random rng = new Random();
            rng.setSeed(0);
            byte[] message = new byte[500000];
            for (int i=0; i<message.length; i++)
                message[i] = (byte)(32 + rng.nextInt(127-32));
            email.setText(new String(message));
        }
    }

    @Test
    public void testSign() throws MessagingException, SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException, GeneralSecurityException {
        assertEquals(emails.length-1, identities.size());
        
        for (Email email: emails) {
            EmailIdentity identity = identities.get(email);
            if (identity == null)
                continue;
            
            // sign and verify signature
            sign(email, identity);
            assertTrue(email.isSignatureValid());
            
            // write the email to a byte array, make a new email from the byte array, and verify the signature
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            for (UnencryptedEmailPacket packet: email.createEmailPackets(identity, null, I2PBotePacket.MAX_DATAGRAM_SIZE))
                outputStream.write(packet.getContent());
            email = new Email(outputStream.toByteArray());
            assertTrue(email.isSignatureValid());
        }
    }

    /**
     * Calls the private method {link Email.sign(EmailIdentity)}.
     * @param email
     * @param identity
     * @throws NoSuchMethodException 
     * @throws SecurityException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    private void sign(Email email, EmailIdentity identity) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        Method signMethod = Email.class.getDeclaredMethod("sign", EmailIdentity.class);
        signMethod.setAccessible(true);
        signMethod.invoke(email, identity);
    }
    
    @Test
    public void testCreateEmailPackets() throws MessagingException, IOException, GeneralSecurityException, SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
        // convert an email to a byte array, convert back, and compare with the original email
        for (Email email: emails) {
            EmailIdentity identity = identities.get(email);
            Collection<UnencryptedEmailPacket> packets = email.createEmailPackets(identity, null, I2PBotePacket.MAX_DATAGRAM_SIZE);
            assertTrue("Expected more email packets. #packets = " + packets.size(), packets.size() > email.getText().length()/I2PBotePacket.MAX_DATAGRAM_SIZE/2);   // the emails are somewhat compressible, but definitely not by 50%
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            for (UnencryptedEmailPacket packet: packets)
                outputStream.write(packet.getContent());
            Email newEmail = new Email(outputStream.toByteArray());
            assertEquals(email.getContent(), newEmail.getContent());

            // check packet sizes against size limit
            for (UnencryptedEmailPacket packet: packets)
                assertTrue("Email packet exceeds max size!", packet.toByteArray().length <= I2PBotePacket.MAX_DATAGRAM_SIZE);
        }
    }
    
    @Test
    public void testHeaderRemoval() throws MessagingException, IOException, GeneralSecurityException {
        Email newEmail;
        Collection<UnencryptedEmailPacket> packets;
        ByteArrayOutputStream outputStream;
        
        // verify that all BCC addresses are removed when sending to a TO: address
        EmailIdentity identity2 = identities.get(emails[2]);
        packets = emails[2].createEmailPackets(identity2, null, I2PBotePacket.MAX_DATAGRAM_SIZE);
        outputStream = new ByteArrayOutputStream();
        for (UnencryptedEmailPacket packet: packets)
            outputStream.write(packet.getContent());
        newEmail = new Email(outputStream.toByteArray());
        assertNull("BCC headers were not removed!", newEmail.getHeader("BCC"));
        assertEquals(3, newEmail.getAllRecipients().length);
        
        // verify that the recipient is not removed if it is a BCC: addresses
        packets = emails[2].createEmailPackets(bccIdentity, bccEmailDestination, I2PBotePacket.MAX_DATAGRAM_SIZE);   // use the plain email dest because that is what the Email class compares against
        outputStream = new ByteArrayOutputStream();
        for (UnencryptedEmailPacket packet: packets)
            outputStream.write(packet.getContent());
        newEmail = new Email(outputStream.toByteArray());
        assertEquals("One BCC header expected!", 1, newEmail.getHeader("BCC").length);
        assertEquals(4, newEmail.getAllRecipients().length);
    }
    
    @Test
    public void testCompression() throws MessagingException, GeneralSecurityException {
        // create a 500,000-char string that should compress to one packet
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0; i<50000; i++)
            stringBuilder.append("0123456789");
        
        Email newEmail = new Email(true);
        newEmail.setText(stringBuilder.toString());
        Collection<UnencryptedEmailPacket> packets = newEmail.createEmailPackets(bccIdentity, null, I2PBotePacket.MAX_DATAGRAM_SIZE);
        assertEquals("The email was not compressed into one email packet.", 1, packets.size());
    }
    
    // TODO test an uncompressible email
}