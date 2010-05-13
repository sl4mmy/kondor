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

import com.thoughtworks.condorcet.vote.tabulation.ElectionResults;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Understands how to rank candidates in an election when compared to each other
 * one at a time.
 *
 * @see http://en.wikipedia.org/wiki/Condorcet_method
 */
public class CondorcetMethod {

        private Candidate[][] ballots;

        private Set<Voter> voters;

        public CondorcetMethod(final Candidate[][] ballots) {
                this.ballots = ballots;
        }

        public CondorcetMethod(final Set<Voter> voters) {
                this.voters = voters;
        }

        public CondorcetMethod() {
        }

        public List<Candidate> elect(final Set<Candidate> candidates) {
                final List<Candidate> rankedList = new ArrayList<Candidate>();
                if (this.voters == null) {
                        return rankedList;
                }

                this.ballots = new Candidate[this.voters.size()][];

                int i = 0;
                for (Voter voter : voters) {
                        final List<Candidate> ballot = voter.vote(candidates);
                        if (ballot != null) {
                                this.ballots[i++] = ballot.toArray(
                                    new Candidate[ballot.size()]);
                        } else {
                                this.ballots[i++] = new Candidate[0];
                        }
                }

                final Candidate[] rankedBallots = rank();

                for (final Candidate object : rankedBallots) {
                        rankedList.add(object);
                }

                return rankedList;
        }

        public Candidate[] rank() {
                if (this.ballots == null) {
                        return new Candidate[0];
                }
                final Candidate[] candidates = getCandidates();

                final ElectionResults results = new ElectionResults(candidates);

                for (final Candidate[] ballot : this.ballots) {
                        for (int i = 0; i < ballot.length; i++) {
                                final Candidate runner = ballot[i];
                                for (int j = i + 1; j < ballot.length; j++) {
                                        final Candidate opponent = ballot[j];
                                        results.addResult(runner, opponent);
                                }
                        }
                }

                return results.rank();
        }

        private Candidate[] getCandidates() {
                return new UniqueCandidates(this.ballots).get();
        }

        public void setVoters(final Set<Voter> voters) {
                this.voters = voters;
        }
}
