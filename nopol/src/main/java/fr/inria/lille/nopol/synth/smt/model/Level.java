/*
 * Copyright (C) 2013 INRIA
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package fr.inria.lille.nopol.synth.smt.model;

/**
 * @author Favio D. DeMarco
 * 
 */
public enum Level {
	CONSTANTS, COMPARISON, LOGIC, ARITHMETIC, COMPARISON_2, LOGIC_2, ARITHMETIC_2, ITE_ARRAY_ACCESS,  MULTIPLICATION, ITE_ARRAY_ACCESS_2, MULTIPLICATION_2;

	public Level next() {
		return values()[ordinal() + 1];
	}
}
