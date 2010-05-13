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

/**
 * Understands the number of pairwise contests won by a candidate against every
 * other candidate in an election.
 */
public class HeadToHeadResult implements Comparable<HeadToHeadResult> {

        private final Candidate candidate;

        private final int score;

        public HeadToHeadResult(final Candidate candidate, final int score) {
                this.candidate = candidate;
                this.score = score;
        }

        public Candidate getCandidate() {
                return this.candidate;
        }

        public int getScore() {
                return this.score;
        }

        public int compareTo(final HeadToHeadResult other) {
                if (this.score == other.score) {
                        return 0;
                }
                return this.score - other.score;
        }
}
