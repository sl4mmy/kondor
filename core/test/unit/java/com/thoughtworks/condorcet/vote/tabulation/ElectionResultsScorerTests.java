/*
 * Copyright 2007 ThoughtWorks, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
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

import junit.framework.JUnit4TestAdapter;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.condorcet.vote.Candidate;

public class ElectionResultsScorerTests {

        private static class IntegerCandidate implements Candidate {
        }

        @Test
        public void shouldNeverReturnNull() throws Exception {
                final List<HeadToHeadResult> winners =
                    new ElectionResultsScorer((List<Candidate>) null, (int[][]) null).score();
                assertEquals(0, winners.size());
        }

        @Test
        public void shouldReturnSingleWinnerWithSingleVoteCount() throws Exception {
                final Candidate candidate = new IntegerCandidate();
                final List<Candidate> candidates = new ArrayList<Candidate>();
                candidates.add(candidate);
                final int[][] votes = new int[][] { { 42 } };
                final List<HeadToHeadResult> winners = new ElectionResultsScorer(candidates, votes).score();
                assertEquals(candidate, winners.get(0).getCandidate());
                assertEquals(42, winners.get(0).getScore());
        }

        @Test
        public void shouldAddAllVotesForSingleCandidate() throws Exception {
                final Candidate candidate = new IntegerCandidate();
                final List<Candidate> candidates = new ArrayList<Candidate>();
                candidates.add(candidate);
                final int[][] votes = new int[][] { { 42, 42 } };
                final List<HeadToHeadResult> winners = new ElectionResultsScorer(candidates, votes).score();
                assertEquals(candidate, winners.get(0).getCandidate());
                assertEquals(84, winners.get(0).getScore());
        }

        @Test
        public void shouldAddAllVotesForAllCandidates() throws Exception {
                final List<Candidate> candidates = new ArrayList<Candidate>();
                for (int i = 0; i < 3; i++) {
                        candidates.add(new IntegerCandidate());
                }
                final int[][] votes = new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
                final List<HeadToHeadResult> winners = new ElectionResultsScorer(candidates, votes).score();
                assertEquals(3, winners.size());
                assertEquals(6, winners.get(0).getScore());
                assertEquals(15, winners.get(1).getScore());
                assertEquals(24, winners.get(2).getScore());
        }

        public static junit.framework.Test suite() {
                return new JUnit4TestAdapter(ElectionResultsScorerTests.class);
        }
}
