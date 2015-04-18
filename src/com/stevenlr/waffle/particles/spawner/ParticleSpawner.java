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

	@Override
	public boolean canSpawnParticle() {
		if (_subSpawner != null) {
			return _subSpawner.canSpawnParticle();
		}

		return false;
	}

	@Override
	public boolean isDoneSpawning() {
		if (_subSpawner != null) {
			return _subSpawner.isDoneSpawning();
		}

		return true;
	}

	@Override
	public void update(float dt) {
		if (_subSpawner != null) {
			_subSpawner.update(dt);
		}
	}
}
