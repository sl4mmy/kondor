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

package com.thoughtworks.condorcet.vote;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Understands the set of candidates that appear on at least one ballot.
 */
class UniqueCandidates<T> {

        private final Candidate[][] ballots;

        UniqueCandidates(final Candidate[][] ballots) {
                this.ballots = ballots;
        }

        public Candidate[] get() {
                if (this.ballots == null) {
                        return new Candidate[0];
                }

                final Set<Candidate> candidates = new LinkedHashSet<Candidate>();
                for (final Candidate[] ballot : this.ballots) {
                        for (final Candidate candidate : ballot) {
                                candidates.add(candidate);
                        }
                }
                return candidates.toArray(new Candidate[candidates.size()]);
        }
}
