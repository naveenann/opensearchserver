/**   
 * License Agreement for OpenSearchServer
 *
 * Copyright (C) 2013-2014 Emmanuel Keller / Jaeksoft
 * 
 * http://www.open-search-server.com
 * 
 * This file is part of OpenSearchServer.
 *
 * OpenSearchServer is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 * OpenSearchServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with OpenSearchServer. 
 *  If not, see <http://www.gnu.org/licenses/>.
 **/

package com.jaeksoft.searchlib.index.osse;

import java.io.IOException;

import org.apache.lucene.analysis.TokenStream;

import com.jaeksoft.searchlib.analysis.filter.AbstractTermFilter;

public class OsseTokenTermUpdate extends AbstractTermFilter {

	private final OsseTermBuffer buffer;

	private int termCount;

	public OsseTokenTermUpdate(final OsseTermBuffer termBuffer,
			final TokenStream input) {
		super(input);
		this.buffer = termBuffer;
		this.termCount = 0;
	}

	@Override
	public final boolean incrementToken() throws IOException {
		for (;;) {
			if (!input.incrementToken())
				return false;
			if (termAtt.length() != 0) {
				buffer.addTerm(termAtt, offsetAtt, posIncrAtt);
				termCount++;
			}
		}
	}

	@Override
	final public void close() throws IOException {
		super.close();
	}

	final public int getTermCount() {
		return termCount;
	}

}
