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

import java.util.LinkedList;
import java.util.List;

/**
 * Understands how to calculate the strength of each candidate when compared to every other.
 */
public class ElectionResultsScorer {

        private final List<Candidate> candidates;

        private final int[][] matrix;

        public ElectionResultsScorer(final List<Candidate> candidates, final int[][] matrix) {
                this.candidates = candidates;
                this.matrix = matrix;
        }

        public List<HeadToHeadResult> score() {
                if (this.candidates == null || this.matrix == null) {
                        return new LinkedList<HeadToHeadResult>();
                }

                final List<HeadToHeadResult> results = new LinkedList<HeadToHeadResult>();
                for (final Candidate candidate : this.candidates) {
                        results.add(new HeadToHeadResult(candidate, score(candidate)));
                }
                return results;
        }

        private int score(Object candidate) {
                final int indexOfCandidate = this.candidates.indexOf(candidate);
                int score = 0;
                for (final int votesFor : this.matrix[indexOfCandidate]) {
                        score += votesFor;
                }
                return score;
        }
}
