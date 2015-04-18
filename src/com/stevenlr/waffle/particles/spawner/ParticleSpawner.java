/*
 * Copyright (c) 2015 Steven Le Rouzic
 * See LICENSE.txt for license details
 */

package com.stevenlr.waffle.particles.spawner;

import com.stevenlr.waffle.IUpdatable;

public abstract class ParticleSpawner implements IUpdatable, IParticleSpawnerDecorator {

	protected ParticleSpawner _subSpawner;

	protected ParticleSpawner(ParticleSpawner subSpawner) {
		_subSpawner = subSpawner;
	}
}
