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
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CondorcetMethodTests {

        private static class IntegerCandidate implements Candidate {

        }

        @Test
        public void shouldNeverReturnNull() throws Exception {
                final Candidate[] winners = rank(new Candidate[][] { });
                assertEquals(0, winners.length);
        }

        @Test
        public void shouldRankOnlyCandidateAsWinner() throws Exception {
                final Candidate winner = new IntegerCandidate();
                final Candidate[] winners = rank(winner);
                assertEquals(winner, winners[0]);
                assertEquals(1, winners.length);
        }

        @Test
        public void shouldRankCandidatesInTheSameOrderIfThereIsOnlyASingleBallot()
            throws Exception {
                final Candidate firstPlace = new IntegerCandidate();
                final Candidate secondPlace = new IntegerCandidate();
                final Candidate[] winners = rank(firstPlace, secondPlace);
                assertEquals(firstPlace, winners[0]);
                assertEquals(secondPlace, winners[1]);
                assertEquals(2, winners.length);
        }

        @Test
        public void shouldRankOnlyCandidateAcrossMultipleIdenticalBallotsAsWinner()
            throws Exception {
                final Candidate winner = new IntegerCandidate();
                final Candidate[] ballot = new Candidate[] { winner };
                final Candidate[] winners = rank(
                    new Candidate[][] { ballot, ballot });
                assertEquals(winner, winners[0]);
                assertEquals(1, winners.length);
        }

        @Test
        public void shouldRankCandidatesInTheSameOrderIfEveryBallotIsIdentical()
            throws Exception {
                final Candidate firstPlace = new IntegerCandidate();
                final Candidate secondPlace = new IntegerCandidate();
                final Candidate[] ballot = new Candidate[] {
                    firstPlace, secondPlace
                };
                final Candidate[] winners = rank(
                    new Candidate[][] { ballot, ballot });
                assertEquals(firstPlace, winners[0]);
                assertEquals(secondPlace, winners[1]);
                assertEquals(2, winners.length);
        }

        @Test
        public void shouldAllowBallotsToOmitCandidates() throws Exception {
                final Candidate firstPlace = new IntegerCandidate();
                final Candidate secondPlace = new IntegerCandidate();
                final Candidate[] firstBallot = new Candidate[] { firstPlace };
                final Candidate[] secondBallot = new Candidate[] {
                    firstPlace, secondPlace
                };
                final Candidate[] winners = rank(
                    new Candidate[][] { firstBallot, secondBallot });
                assertEquals(firstPlace, winners[0]);
                assertEquals(secondPlace, winners[1]);
                assertEquals(2, winners.length);
        }

        @Test
        public void shouldRankCandidatesAccordingToPreferenceOfAllVoters()
            throws Exception {
                final Candidate firstPlace = new IntegerCandidate();
                final Candidate secondPlace = new IntegerCandidate();
                final Candidate[] firstBallot = new Candidate[] {
                    secondPlace, firstPlace
                };
                final Candidate[] secondBallot = new Candidate[] {
                    firstPlace, secondPlace
                };
                final Candidate[] thirdBallot = new Candidate[] {
                    firstPlace, secondPlace
                };
                final Candidate[] winners = rank(new Candidate[][] {
                    firstBallot, secondBallot, thirdBallot
                });
                assertEquals(firstPlace, winners[0]);
                assertEquals(secondPlace, winners[1]);
        }

        @Test
        public void shouldRankCandidatesDespiteBallotsWithMissingCandidates()
            throws Exception {
                final Candidate firstPlace = new IntegerCandidate();
                final Candidate secondPlace = new IntegerCandidate();
                final Candidate[] firstBallot = new Candidate[] { secondPlace };
                final Candidate[] secondBallot = new Candidate[] { firstPlace };
                final Candidate[] thirdBallot = new Candidate[] { firstPlace };
                final Candidate[] winners = rank(new Candidate[][] {
                    firstBallot, secondBallot, thirdBallot
                });
                assertEquals(firstPlace, winners[0]);
                assertEquals(secondPlace, winners[1]);
        }

        private Candidate[] rank(final Candidate... ballot) {
                return rank(new Candidate[][] { ballot });
        }

        private Candidate[] rank(final Candidate[][] ballots) {
                return new CondorcetMethod(ballots).rank();
        }

        public static junit.framework.Test suite() {
                return new JUnit4TestAdapter(CondorcetMethodTests.class);
        }
}
