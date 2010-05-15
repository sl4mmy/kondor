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

package com.thoughtworks.condorcet.vote.tabulation;

import com.thoughtworks.condorcet.vote.Candidate;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ElectionResultsScorerTests {

        private static class IntegerCandidate implements Candidate {

        }

        @Test
        public void shouldNotBeAnyWinnerWhenCandidatesIsNull()
            throws Exception {
                final List<HeadToHeadResult> winners =
                    new ElectionResultsScorer((List<Candidate>) null,
                        new int[][] { }).score();
                assertEquals(0, winners.size());
        }

        @Test
        public void shouldNotBeAnyWinnerWhenMatrixIsNull()
            throws Exception {
                final List<HeadToHeadResult> winners =
                    new ElectionResultsScorer(new ArrayList<Candidate>(),
                        (int[][]) null).score();
                assertEquals(0, winners.size());
        }

        @Test
        public void shouldReturnSingleWinnerWithSingleVoteCount()
            throws Exception {
                final Candidate candidate = new IntegerCandidate();
                final List<Candidate> candidates = new ArrayList<Candidate>();
                candidates.add(candidate);
                final int[][] votes = new int[][] { { 42 } };
                final List<HeadToHeadResult> winners
                    = new ElectionResultsScorer(candidates, votes).score();
                assertEquals(candidate, winners.get(0).getCandidate());
                assertEquals(42, winners.get(0).getScore());
        }

        @Test
        public void shouldAddAllVotesForSingleCandidate() throws Exception {
                final Candidate candidate = new IntegerCandidate();
                final List<Candidate> candidates = new ArrayList<Candidate>();
                candidates.add(candidate);
                final int[][] votes = new int[][] { { 42, 42 } };
                final List<HeadToHeadResult> winners
                    = new ElectionResultsScorer(candidates, votes).score();
                assertEquals(candidate, winners.get(0).getCandidate());
                assertEquals(84, winners.get(0).getScore());
        }

        @Test
        public void shouldAddAllVotesForAllCandidates() throws Exception {
                final List<Candidate> candidates = new ArrayList<Candidate>();
                for (int i = 0; i < 3; i++) {
                        candidates.add(new IntegerCandidate());
                }
                final int[][] votes = new int[][] {
                    { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }
                };
                final List<HeadToHeadResult> winners
                    = new ElectionResultsScorer(candidates, votes).score();
                assertEquals(3, winners.size());
                assertEquals(6, winners.get(0).getScore());
                assertEquals(15, winners.get(1).getScore());
                assertEquals(24, winners.get(2).getScore());
        }

        public static junit.framework.Test suite() {
                return new JUnit4TestAdapter(ElectionResultsScorerTests.class);
        }
}
