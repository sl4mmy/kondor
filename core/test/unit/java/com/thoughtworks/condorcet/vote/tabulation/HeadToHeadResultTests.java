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

import static org.junit.Assert.assertEquals;

public class HeadToHeadResultTests {

        private static class IntegerCandidate implements Candidate {

        }

        @Test
        public void shouldGetCandidate() throws Exception {
                final Candidate candidate = new IntegerCandidate();
                final int score = 42;
                final HeadToHeadResult pair = new HeadToHeadResult(candidate, score);
                assertEquals(candidate, pair.getCandidate());
        }

        @Test
        public void shouldGetScore() throws Exception {
                final Candidate candidate = new IntegerCandidate();
                final int score = 42;
                final HeadToHeadResult pair = new HeadToHeadResult(candidate, score);
                assertEquals(score, pair.getScore());
        }

        @Test
        public void shouldBeComparable() throws Exception {
                final HeadToHeadResult pair = new HeadToHeadResult(null, 0);
                assertEquals(true, pair instanceof Comparable);
        }

        @Test
        public void shouldBeEqualWhenCompared() throws Exception {
                final Candidate firstCandidate = new IntegerCandidate();
                final Candidate secondCandidate = new IntegerCandidate();
                final int score = 42;
                final HeadToHeadResult firstPair = new HeadToHeadResult(firstCandidate, score);
                final HeadToHeadResult secondPair = new HeadToHeadResult(secondCandidate, score);
                assertEquals(0, firstPair.compareTo(secondPair));
        }

        @Test
        public void shouldComeBeforeOther() throws Exception {
                final Candidate firstCandidate = new IntegerCandidate();
                final Candidate secondCandidate = new IntegerCandidate();
                final int firstScore = 0;
                final int secondScore = 42;
                final HeadToHeadResult firstPair = new HeadToHeadResult(firstCandidate, firstScore);
                final HeadToHeadResult secondPair = new HeadToHeadResult(secondCandidate, secondScore);
                assertEquals(true, firstPair.compareTo(secondPair) < 0);
        }

        @Test
        public void shouldComeAfterOther() throws Exception {
                final Candidate firstCandidate = new IntegerCandidate();
                final Candidate secondCandidate = new IntegerCandidate();
                final int firstScore = 42;
                final int secondScore = 0;
                final HeadToHeadResult firstPair = new HeadToHeadResult(firstCandidate, firstScore);
                final HeadToHeadResult secondPair = new HeadToHeadResult(secondCandidate, secondScore);
                assertEquals(true, firstPair.compareTo(secondPair) > 0);
        }

        public static junit.framework.Test suite() {
                return new JUnit4TestAdapter(HeadToHeadResultTests.class);
        }
}
