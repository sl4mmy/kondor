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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Understands how to track the results of comparing each candidate to every
 * other candidate.
 */
public class ElectionResults {

        private final List<Candidate> candidates;

        private final int[][] matrix;

        public ElectionResults(final Candidate[] candidates) {
                if (candidates != null) {
                        this.candidates = Arrays.asList(candidates);
                        final int length = candidates.length;
                        this.matrix = new int[length][length];
                } else {
                        this.candidates = new ArrayList<Candidate>(0);
                        this.matrix = new int[0][0];
                }
        }

        public void addResult(final Object runner) {
                addResult(runner, null);
        }

        public void addResult(final Object winner, final Object loser) {
                final int indexOfWinner = this.candidates.indexOf(winner);
                if (loser != null) {
                        increment(indexOfWinner, this.candidates.indexOf(
                            loser));
                } else {
                        increment(indexOfWinner, indexOfWinner);
                }
        }

        public Candidate[] rank() {
                final List<HeadToHeadResult> results
                    = new ElectionResultsScorer(this.candidates, this.matrix)
                    .score();
                Collections.sort(results);
                Collections.reverse(results);
                final Candidate[] winners = new Candidate[results.size()];
                int index = 0;
                for (final HeadToHeadResult headToHeadResult : results) {
                        winners[index++] = headToHeadResult.getCandidate();
                }
                return winners;
        }

        private void increment(final int indexOfWinner,
            final int indexOfLoser) {
                this.matrix[indexOfWinner][indexOfLoser] += 1;
        }
}
