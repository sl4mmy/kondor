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

import com.thoughtworks.condorcet.vote.Candidate;
import junit.framework.JUnit4TestAdapter;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ElectionResultsTests {

        private static class IntegerCandidate implements Candidate {
        }

        @Test
        public void shouldNeverReturnNull() {
                final Candidate[] winners = new ElectionResults((Candidate[]) null).rank();
                assertEquals(0, winners.length);
        }

        @Test
        public void shouldRankSingleSingleCandidateFirst() {
                final Candidate candidate = new IntegerCandidate();
                final Candidate[] candidates = new Candidate[] { candidate };
                final ElectionResults matrix = new ElectionResults(candidates);
                matrix.addResult(candidate);
                final Object[] winners = matrix.rank();
                assertEquals(candidate, winners[0]);
                assertEquals(1, winners.length);
        }

        @Test
        public void shouldRankCandidatesIdenticalToSingleResult() {
                final Candidate firstCandidate = new IntegerCandidate();
                final Candidate secondCandidate = new IntegerCandidate();
                final Candidate[] candidates = new Candidate[] { firstCandidate, secondCandidate };
                final ElectionResults matrix = new ElectionResults(candidates);
                matrix.addResult(firstCandidate, secondCandidate);
                final Object[] winners = matrix.rank();
                assertEquals(firstCandidate, winners[0]);
                assertEquals(secondCandidate, winners[1]);
                assertEquals(2, winners.length);
        }

        public static junit.framework.Test suite() {
                return new JUnit4TestAdapter(ElectionResultsTests.class);
        }
}
