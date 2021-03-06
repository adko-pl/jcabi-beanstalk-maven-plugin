/**
 * Copyright (c) 2012-2014, jcabi.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the following
 * disclaimer. 2) Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution. 3) Neither the name of the jcabi.com nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.jcabi.beanstalk.maven.plugin;

import com.amazonaws.auth.AWSCredentials;
import org.apache.maven.settings.Server;
import org.apache.maven.settings.Settings;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link ServerCredentials}.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 */
public final class ServerCredentialsTest {

    /**
     * ServerCredentials can fetch credentials from Maven settings.
     * @throws Exception If something is wrong
     */
    @Test
    public void fetchesCredentialsFromMavenSettings() throws Exception {
        final String key = "AAAABBBBCCCCDDDDZ9Y1";
        final String secret = "AbCdEfGhAbCdEfG/AbCdE7GhAbCdE9Gh+bCdEfGh";
        final Server server = new Server();
        server.setUsername(key);
        server.setPassword(secret);
        final String name = "srv1";
        server.setId(name);
        final Settings settings = new Settings();
        settings.addServer(server);
        final AWSCredentials creds = new ServerCredentials(settings, name);
        MatcherAssert.assertThat(
            creds.getAWSAccessKeyId(),
            Matchers.equalTo(key)
        );
        MatcherAssert.assertThat(
            creds.getAWSSecretKey(),
            Matchers.equalTo(secret)
        );
    }

    /**
     * ServerCredentials can throw when server is not defined.
     * @throws Exception If something is wrong
     */
    @Test(expected = org.apache.maven.plugin.MojoFailureException.class)
    public void throwsWhenServerIsNotDefined() throws Exception {
        new ServerCredentials(new Settings(), "foo");
    }

}
