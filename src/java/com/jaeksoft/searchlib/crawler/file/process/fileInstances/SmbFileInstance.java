/**   
 * License Agreement for Jaeksoft OpenSearchServer
 *
 * Copyright (C) 2010 Emmanuel Keller / Jaeksoft
 * 
 * http://www.open-search-server.com
 * 
 * This file is part of Jaeksoft OpenSearchServer.
 *
 * Jaeksoft OpenSearchServer is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 * Jaeksoft OpenSearchServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Jaeksoft OpenSearchServer. 
 *  If not, see <http://www.gnu.org/licenses/>.
 **/

package com.jaeksoft.searchlib.crawler.file.process.fileInstances;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import com.jaeksoft.searchlib.crawler.file.database.FileTypeEnum;
import com.jaeksoft.searchlib.crawler.file.process.FileInstanceAbstract;

public class SmbFileInstance extends FileInstanceAbstract {

	@Override
	public URI init() {
		return null;
		// TODO Auto-generated method stub

	}

	@Override
	public FileTypeEnum getFileType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FileInstanceAbstract[] listFilesAndDirectories() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FileInstanceAbstract[] listFilesOnly() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getLastModified() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getFileSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}