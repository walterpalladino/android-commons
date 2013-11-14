/*
 * Copyright 2013 Walter Hugo Palladino
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
package com.whp.android.serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.util.Base64;

/**
 * @author Walter Hugo Palladino
 * @since 12/10/2013
 * 
 */
public class SerializationUtils {

	/**
	 * Read the object from Base64 string.
	 * 
	 * @param s
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object fromString(String s) throws IOException, ClassNotFoundException {
		byte [] data = Base64.decode (s, Base64.DEFAULT);
		ObjectInputStream ois = new ObjectInputStream (new ByteArrayInputStream (data));
		Object o = ois.readObject ();
		ois.close ();
		return o;
	}

	/**
	 * Write the object to a Base64 string.
	 * 
	 * @param o
	 * @return
	 * @throws IOException
	 */
	public static String toString(Serializable o) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream ();
		ObjectOutputStream oos = new ObjectOutputStream (baos);
		oos.writeObject (o);
		oos.close ();
		return new String (Base64.encode (baos.toByteArray (), Base64.DEFAULT));
	}
}
