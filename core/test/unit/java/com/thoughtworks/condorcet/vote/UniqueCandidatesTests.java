/*
 * Copyright (c) 2010, Kent R. Spillner <kspillner@acm.org>
 * Copyright (c) 2007 ThoughtWorks, Inc.
 *
 * Permission to use, copy, modify, and distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 * OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.thoughtworks.condorcet.vote;

import junit.framework.JUnit4TestAdapter;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class UniqueCandidatesTests {

        private static class IntegerCandidate implements Candidate {
        }

        @Test
        public void shouldNeverReturnNull() throws Exception {
                final Candidate[] candidates = get(new Candidate[] { });
                assertEquals(0, candidates.length);
        }

        @Test
        public void shouldGetOnlyCandidateOnOnlyBallot() throws Exception {
                final Candidate candidate = new IntegerCandidate();
                final Candidate[] candidates = get(candidate);
                assertEquals(candidate, candidates[0]);
                assertEquals(1, candidates.length);
        }

        @Test
        public void shouldGetAllCandidatesOnAllBallots() throws Exception {
                final Candidate firstCandidate = new IntegerCandidate();
                final Candidate secondCandidate = new IntegerCandidate();
                final Candidate[] firstBallot = new Candidate[] { firstCandidate };
                final Candidate[] secondBallot = new Candidate[] { secondCandidate };
                final Candidate[] candidates = get(new Candidate[][] { firstBallot, secondBallot });
                assertEquals(firstCandidate, candidates[0]);
                assertEquals(secondCandidate, candidates[1]);
                assertEquals(2, candidates.length);
        }

        @Test
        public void shouldAllowBallotsToOmitCandidates() throws Exception {
                final Candidate firstCandidate = new IntegerCandidate();
                final Candidate secondCandidate = new IntegerCandidate();
                final Candidate[] firstBallot = new Candidate[] { firstCandidate };
                final Candidate[] secondBallot = new Candidate[] { firstCandidate, secondCandidate };
                final Candidate[] candidates = get(new Candidate[][] { firstBallot, secondBallot });
                assertEquals(firstCandidate, candidates[0]);
                assertEquals(secondCandidate, candidates[1]);
                assertEquals(2, candidates.length);
        }

        private Candidate[] get(final Candidate... ballot) {
                return get(new Candidate[][] { ballot });
        }

        private Candidate[] get(final Candidate[][] ballots) {
                return new UniqueCandidates(ballots).get();
        }

        public static junit.framework.Test suite() {
                return new JUnit4TestAdapter(UniqueCandidatesTests.class);
        }
}
